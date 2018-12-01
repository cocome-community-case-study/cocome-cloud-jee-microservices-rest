package org.cocome.storesservice.frontend.cashdeskcomponents;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.cocome.external.DebitResult;
import org.cocome.external.IBankLocal;
import org.cocome.external.TransactionID;
import org.cocome.storesservice.events.CashAmountEnteredEvent;
import org.cocome.storesservice.events.ChangeAmountCalculatedEvent;
import org.cocome.storesservice.events.CreditCardPaymentSuccessfulEvent;
import org.cocome.storesservice.events.InsufficientCashAmountEvent;
import org.cocome.storesservice.events.InsufficientCreditCardBalanceEvent;
import org.cocome.storesservice.events.InvalidCreditCardDetailsEvent;
import org.cocome.storesservice.events.InvalidProductBarcodeEvent;
import org.cocome.storesservice.events.ProductOutOfStockEvent;
import org.cocome.storesservice.events.RunningTotalChangedEvent;
import org.cocome.storesservice.events.SaleSuccessEvent;
import org.cocome.storesservice.events.SaleUnsuccessfulEvent;
import org.cocome.storesservice.exceptions.QueryException;
import org.cocome.storesservice.exceptions.UpdateException;
import org.cocome.storesservice.frontend.stock.IStockManager;
import org.cocome.storesservice.frontend.viewdata.StockItemViewData;

/**
 * Class that represents functionality of the CashBox. It also stores the
 * current running total amount and the barcode the user is submitting. <br>
 * Most of the Methods are executed by the CashBoxEventHandler
 * 
 * @author Niko Benkler
 * @author Robert Heinrich
 *
 */
@Named
@SessionScoped
public class CashBox implements ICashBox, Serializable {

	@Inject
	IStockManager stockManager;

	@EJB
	IBankLocal bank;

	@Inject
	Event<InvalidProductBarcodeEvent> invalidProductBarcodeEvent;

	@Inject
	Event<RunningTotalChangedEvent> runningTotalChangedEvents;

	@Inject
	Event<ProductOutOfStockEvent> productOutOfStockEvent;

	@Inject
	Event<InsufficientCashAmountEvent> insufficientCashAmountEvents;

	@Inject
	Event<CashAmountEnteredEvent> cashAmountEnteredEvents;

	@Inject
	Event<ChangeAmountCalculatedEvent> changeAmountCalculatedEvents;

	@Inject
	Event<SaleSuccessEvent> saleSuccessEvent;

	@Inject
	Event<SaleUnsuccessfulEvent> saleUnsuccessEvent;
	
	@Inject
	Event<InsufficientCreditCardBalanceEvent> insuffiecientCreditVardBalanceEvent;
	
	@Inject
	Event<InvalidCreditCardDetailsEvent> invalidCreditCardDetailsEvent;
	
	@Inject
	Event<CreditCardPaymentSuccessfulEvent> creditCardPaymentSuccessfullEvent;

	private static final long serialVersionUID = 3992593730770915195L;
	private String barcode;
	private double runningTotal;
	private List<StockItemViewData> saleProducts = new ArrayList<>();

	private static final Logger LOG = Logger.getLogger(CashBox.class);

	@PostConstruct
	private void init() {
		this.barcode = "";
		this.setRunningTotal(0.0);
	}

	/**
	 * Add next Digit to barcode
	 */
	@Override
	public void addToDigit(char nextDigit) {
		if (Character.isDigit(nextDigit)) {
			barcode += nextDigit;
		}
	}
	
	

	@Override
	public String getBarcode() {
		return this.barcode;
	}

	@Override
	public void setBarcode(String barcode) {
		this.barcode = barcode;

	}

	@Override
	public void resetBarcode() {
		barcode = "";
	}

	@Override
	public void removeLastDigit() {
		barcode = barcode.substring(0, barcode.length() - 1);

	}

	/**
	 * Reset CashBox
	 */
	@Override
	public void startSale() {
		resetBarcode();
		this.runningTotal = 0.0;

	}

	@Override
	public double getRunningTotal() {
		return runningTotal;
	}

	@Override
	public void setRunningTotal(double runningTotal) {
		this.runningTotal = runningTotal;
	}

	/**
	 * Executed by EventHandler, as soon as Scanner fires a barcodeScannedEvent <br>
	 * adds Item tu current Sale
	 */
	@Override
	public void addItemToSale(long barcode, long storeId) {
		// TODO already enough Items => ExpressCheckout policy

		StockItemViewData item;

		// Fetch Item
		try {
			item = stockManager.getStockItemByBarcodeAndStoreId(barcode, storeId);
		} catch (QueryException e) {
			LOG.debug("FRONTEND: Could not add item with barcode " + barcode + " to sale");
			invalidProductBarcodeEvent.fire(new InvalidProductBarcodeEvent(barcode));
			return;
		}

		// add it to sale
		if (addItemToSale(item)) {
			final double price = item.getSalesPrice();
			this.runningTotal = this.computeNewRunningTotal(price);

			this.sendRunningTotalChangedEvent(item.getName(), price);
		} // else: error is covered by productoutofStockEvent

	}

	/**
	 * Adds Item to Sale. The StockItem itself contains Information about the
	 * current amount in Stock
	 * 
	 * @param stockItem
	 * @return
	 */
	private boolean addItemToSale(StockItemViewData stockItem) {

		// Check if StockItem Exists already in sale and reduce amount
		int index = saleProducts.indexOf(stockItem);

		// Item already exists
		if (index != -1) {
			StockItemViewData itemInSale = saleProducts.get(index);
			long currentAmount = itemInSale.getAmount();
			if (currentAmount > 0) {
				itemInSale.setAmount(currentAmount - 1);
			} else {
				productOutOfStockEvent.fire(new ProductOutOfStockEvent());
				return false;
			}

			// Item does not exist --> Add and reduce amount
		} else {
			long currentAmount = stockItem.getAmount();
			if (currentAmount < 1) {
				productOutOfStockEvent.fire(new ProductOutOfStockEvent());
				return false;
			} else {
				// reduce amount
				stockItem.setAmount(currentAmount - 1);
				saleProducts.add(stockItem);
			}

		}

		return true;
	}

	/**
	 * Enter Cash Amount to pay by Cash
	 */
	@Override
	public void enterCashAmount(double cashAmount) throws UpdateException {
		// Check if customer paid enough
		final double change = this.computeChangeAmount(cashAmount);
		if (Math.signum(change) >= 0) {

			try {
				// This is the actually sales-process
				makeSale();
			} catch (QueryException e) {

				saleUnsuccessEvent.fire(new SaleUnsuccessfulEvent());
				throw new UpdateException("An error occured while finishing the sale!");

			}
			cashAmountEnteredEvents.fire(new CashAmountEnteredEvent(cashAmount));
			changeAmountCalculatedEvents.fire(new ChangeAmountCalculatedEvent(change));
			saleSuccessEvent.fire(new SaleSuccessEvent());

		} else {

			insufficientCashAmountEvents.fire(new InsufficientCashAmountEvent(cashAmount, runningTotal));

		}

	}

	@Override
	public void enterCardInfo(String cardInfo, int pin) throws UpdateException {

		//check credit Card details
		TransactionID transaction = bank.validateCard(cardInfo, pin);
		DebitResult result = bank.debitCard(transaction);

		//take action according to checking result
		switch (result) {
		case INSUFFICIENT_BALANCE:
			insuffiecientCreditVardBalanceEvent.fire(new InsufficientCreditCardBalanceEvent());
			return;
		case INVALID_TRANSACTION_ID:
			invalidCreditCardDetailsEvent.fire(new InvalidCreditCardDetailsEvent());
			return;
		case OK:
             creditCardPaymentSuccessfullEvent.fire(new CreditCardPaymentSuccessfulEvent());
			break;
		default:
			invalidCreditCardDetailsEvent.fire(new InvalidCreditCardDetailsEvent());
			return;

		}

		
		//finish Sale
		try {
			// This is the actually sales-process
			makeSale();
		} catch (QueryException e) {

			saleUnsuccessEvent.fire(new SaleUnsuccessfulEvent());
			throw new UpdateException("An error occured while finishing the sale!");

		}

		saleSuccessEvent.fire(new SaleSuccessEvent());

	}

	/**
	 * Finally update the stock item (reduce amount that was sold)
	 * 
	 * @throws QueryException
	 */
	private void makeSale() throws QueryException {
		/*
		 * It might happen, that some Stock Items are already updated and some not. But
		 * we are not interested in fancy rollback mechanisms
		 */
		for (StockItemViewData item : saleProducts) {
			stockManager.updateStockItem(item);
		}
	}

	/**
	 * Used to compute new running total when a item is added to the Sale
	 * 
	 * @param price
	 * @return
	 */
	private double computeNewRunningTotal(final double price) {
		final double result = this.runningTotal + price;
		return Math.rint(100 * result) / 100;
	}

	private void sendRunningTotalChangedEvent(String productName, double salePrice) {
		runningTotalChangedEvents.fire(new RunningTotalChangedEvent(productName, salePrice, this.runningTotal));
	}

	/**
	 * Used to compute change amount when a user paid a certain amount
	 * 
	 * @param amount
	 * @return
	 */
	private double computeChangeAmount(final double amount) {
		final double changeAmount = amount - this.runningTotal;
		return Math.rint(100 * changeAmount) / 100;
	}

}
