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
import org.cocome.storesservice.events.CheckExpressModeEvent;
import org.cocome.storesservice.events.CreditCardPaymentSuccessfulEvent;
import org.cocome.storesservice.events.EnoughItemsForExpressModeEvent;
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

	@Inject
	ICashDesk cashDesk;

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

	@Inject
	Event<CheckExpressModeEvent> checkExpressModeEvent;

	@Inject
	Event<EnoughItemsForExpressModeEvent> enoughItemsForExpressModeEvent;

	private static final long serialVersionUID = 3992593730770915195L;
	private String barcode;
	private double runningTotal;
	private List<StockItemViewData> saleProducts;

	/*
	 * We do not add an item to saleProducts if it already exists. But we need the
	 * absolute amount of items in current sale for express mode. This is stored in this variable
	 */
	private int saleProductsCounter;

	private static final Logger LOG = Logger.getLogger(CashBox.class);

	@PostConstruct
	private void init() {
		this.barcode = "";
		this.setRunningTotal(0.0);
		saleProducts = new ArrayList<>();
		saleProductsCounter = 0;
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
		this.saleProducts.clear();
		this.saleProductsCounter = 0;

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
		LOG.debug("FRONTEND: Trying to add product with barcode " + barcode + " to sale!");
		if (checkIfAlreadyEnoughItems()) {
			LOG.debug("FRONTEND: Could not add item with barcode " + barcode + " to sale. Express Mode enabled");
			return;
		}

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
			saleProductsCounter++; //this is needed for expressmode counting
			final double price = item.getSalesPrice();
			this.runningTotal = this.computeNewRunningTotal(price);
			this.sendRunningTotalChangedEvent(item.getName(), price);
			LOG.debug("FRONTEND: Successfully added stock item with barcode " + item.getBarcode() + " to sale");
		} // else: error is covered by productoutofStockEvent

	}

	private boolean checkIfAlreadyEnoughItems() {
		LOG.debug("FRONTEND: Check if item can be added to sale or sale already exceeds Express mode amount. ");
		LOG.debug("FRONTEND: Current size of sale list is: " + saleProducts.size());
		if (!cashDesk.isInExpressMode()) {
			LOG.debug("FRONTEND: Not in Express Mode. Can add as much items as available.");
			return false;
		}

		if (saleProductsCounter >= cashDesk.getMaxItemsExpressMode()) {
			enoughItemsForExpressModeEvent.fire(new EnoughItemsForExpressModeEvent());
			return true;
		}
		return false;

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
				LOG.debug("FRONEND: Product already in sale. Reduce amoun of available items: " + itemInSale.getName());
			} else {
				productOutOfStockEvent.fire(new ProductOutOfStockEvent());
				LOG.debug(" FRONTEND: Product not available anymore in stock");
				return false;
			}

			// Item does not exist --> Add and reduce amount
		} else {
			long currentAmount = stockItem.getAmount();
			if (currentAmount < 1) {
				LOG.debug(" FRONTEND: Product not available anymore in stock");
				productOutOfStockEvent.fire(new ProductOutOfStockEvent());
				return false;
			} else {
				// reduce amount
				stockItem.setAmount(currentAmount - 1);
				saleProducts.add(stockItem);
				LOG.debug("FRONTEND: Product not yet in sale. Added to sale now: " + stockItem.getName());
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
			checkExpressModeEvent.fire(new CheckExpressModeEvent(saleProducts.size()));

		} else {

			insufficientCashAmountEvents.fire(new InsufficientCashAmountEvent(cashAmount, runningTotal));

		}

	}

	@Override
	public void enterCardInfo(String cardInfo, int pin) throws UpdateException {

		// check credit Card details
		TransactionID transaction = bank.validateCard(cardInfo, pin);
		DebitResult result = bank.debitCard(transaction);

		// take action according to checking result
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

		// finish Sale
		try {
			// This is the actually sales-process
			makeSale();
		} catch (QueryException e) {

			saleUnsuccessEvent.fire(new SaleUnsuccessfulEvent());
			throw new UpdateException("An error occured while finishing the sale!");

		}

		saleSuccessEvent.fire(new SaleSuccessEvent());
		checkExpressModeEvent.fire(new CheckExpressModeEvent(saleProducts.size()));

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
			if (item.getAmount() < item.getMinStock()) {
				stockManager.doStockExchange(item.getId());
			}
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
