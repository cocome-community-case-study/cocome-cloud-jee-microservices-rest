package org.cocome.storesservice.frontend.cashdesk;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Event;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.cocome.storesservice.events.SaleStartedEvent;
import org.cocome.storesservice.events.StartCashPaymentEvent;
import org.cocome.storesservice.exceptions.NoSuchProductException;

import org.cocome.storesservice.exceptions.QueryException;
import org.cocome.storesservice.frontend.cashdeskcomponents.ICashBox;
import org.cocome.storesservice.frontend.cashdeskcomponents.ICashDesk;
import org.cocome.storesservice.frontend.cashdeskcomponents.IDisplay;
import org.cocome.storesservice.frontend.cashdeskcomponents.IExpressLight;
import org.cocome.storesservice.frontend.cashdeskcomponents.IPrinter;
import org.cocome.storesservice.frontend.cashdeskcomponents.IScanner;
import org.cocome.storesservice.frontend.store.IStoreInformation;
import org.cocome.storesservice.frontend.viewdata.StockItemViewData;
import org.cocome.storesservice.navigation.NavigationElements;
import org.cocome.storesservice.util.Messages;

@Named
@SessionScoped
public class CashDeskView implements Serializable {
	private static final long serialVersionUID = -2512543291563857980L;

	private static final String[] EMPTY_OUTPUT = {};
	
	private static final Logger LOG = Logger.getLogger(CashDeskView.class);

	@Inject
	ICashDesk cashDesk;
	
	@Inject
	IExpressLight expressLight;
	
	@Inject 
	IPrinter printer;
	
	@Inject 
	IDisplay display;
	
	@Inject
	ICashBox cashbox;
	
	@Inject 
	IScanner scanner;

	@Inject
	IStoreInformation storeInformation;
	
	@Inject 
	Event<SaleStartedEvent> saleStartedEvents;
	
	@Inject
	Event<StartCashPaymentEvent> startCashPaymentEvent;

	public String submitCashDeskName() {
		updateExpressMode(); //TODO 
		return resetSale();
	}

	private void updateDisplayAndPrinter() {
		updateDisplayMessage();
		updatePrinterOutput();
	}

	private String getSalePageRedirectOutcome() {
		return NavigationElements.START_SALE.getNavigationOutcome();
	}
    
	/**
	 * Set name of the currently active CashDesk
	 * @param cashDeskName
	 */
	public void setCashDeskName(String cashDeskName) {
		cashDesk.setCashDeskName(cashDeskName);
	}
    
	/**
	 * Get Name of the currently active CashDesk
	 * @return
	 */
	public String getCashDeskName() {
		return cashDesk.getCashDeskName();
	}
    
	/**
	 * Determines whether user has to submit a CashDeskName
	 * @return
	 */
	public boolean isCashDeskNameNeeded() {
		boolean bool = cashDesk.isCashDeskNameNeeded();
		return bool;
	}
    
	/**
	 * Determines whether sale already started
	 * @return
	 */
	public boolean isSaleStarted() {
		return cashDesk.isSaleStarted();
	}

	
	/**
	 * Determines wheter CashDesk is in ExpressMode
	 * @return
	 */
	public boolean isInExpressMode() {
		return expressLight.isInExpressMode();
		
	}
    
	/**
	 * Return Message that should be Displayed
	 * @return
	 */
	public String getDisplayMessage() {
		return display.getDisplayLine();
	}
    
	/**
	 * Return the lines for the printer
	 * @return
	 */
	public List<String> getPrinterOutput() {
		return printer.getPrinterOutput();
	}
    
	
	public void updateExpressMode() {
		String cashDeskName = cashDesk.getCashDeskName();

		long storeID = storeInformation.getActiveStoreId();

		boolean expressMode = false;

		// TODO implement ExpressMode Policy
		// expressMode = cashDeskDAO.isInExpressMode(cashDeskName, storeID);

		cashDesk.setInExpressMode(expressMode);
	}

	private void addFacesError(String errorString) {
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", errorString));
	}

	public void updateDisplayMessage() {
//		String cashDeskName = cashDesk.getCashDeskName();
//		long storeID = storeInformation.getActiveStoreID();
//
//		String displayMessage = "";
//
//		try {
//			displayMessage = cashDeskDAO.getDisplayMessage(cashDeskName, storeID);
//		} catch (UnhandledException_Exception | NotInDatabaseException_Exception e) {
//			addFacesError(Messages.getLocalizedMessage("cashdesk.error.display.retrieve"));
//		}
//		
//		cashDesk.setDisplayMessage(displayMessage);
	}

	public String enterCashAmount(double cashAmount) {
//		String cashDeskName = cashDesk.getCashDeskName();
//		long storeID = storeInformation.getActiveStoreID();
//
//		try {
//			cashDeskDAO.enterCashAmount(cashDeskName, storeID, cashAmount);
//		} catch (UnhandledException_Exception | NotInDatabaseException_Exception | IllegalCashDeskStateException_Exception e) {
//			addFacesError(String.format(Messages.getLocalizedMessage("cashdesk.error.cash_pay.failed"), e.getMessage()));
//		}
//		
//		updateDisplayAndPrinter();
//		updateExpressMode();
		return getSalePageRedirectOutcome();
	}

	public String enterCardInfo(String cardInfo, int pin) {
//		String cashDeskName = cashDesk.getCashDeskName();
//		long storeID = storeInformation.getActiveStoreID();
//
//		try {
//			cashDeskDAO.enterCardInfo(cashDeskName, storeID, cardInfo, pin);
//		} catch (UnhandledException_Exception | IllegalCashDeskStateException_Exception
//				| NotInDatabaseException_Exception e) {
//			addFacesError(
//					String.format(Messages.getLocalizedMessage("cashdesk.error.card_pay.failed"), e.getMessage()));
//		}
//		updateDisplayAndPrinter();
//		updateExpressMode();
		return getSalePageRedirectOutcome();
	}

	public String startCashPayment() {
		
		startCashPaymentEvent.fire(new StartCashPaymentEvent());
		
		return getSalePageRedirectOutcome();
	}

	public String startCardPayment() {
		
		//TODO check if expressmode
//		String cashDeskName = cashDesk.getCashDeskName();
//		long storeID = storeInformation.getActiveStoreID();
//
//		try {
//			cashDeskDAO.startCreditCardPayment(cashDeskName, storeID);
//			cashDesk.setAllItemsRegistered(true);
//			cashDesk.setCardPayment(true);
//			cashDesk.setCashPayment(false);
//		} catch (NotInDatabaseException_Exception | ProductOutOfStockException_Exception | UnhandledException_Exception
//				| IllegalCashDeskStateException_Exception | IllegalInputException_Exception e) {
//			addFacesError(String.format(Messages.getLocalizedMessage("cashdesk.error.start_card_pay.failed"),
//					e.getMessage()));
//		}
//
//		updateDisplayAndPrinter();

		return getSalePageRedirectOutcome();
	}

	public String resetSale() {
		saleStartedEvents.fire(new SaleStartedEvent());
		return getSalePageRedirectOutcome();
	}

	public boolean isCashPayment() {
		return cashDesk.isCashPayment();
	}

	public boolean isCardPayment() {
		return cashDesk.isCardPayment();
	}

	public String getBarcode() {
		return cashbox.getBarcode();
	}
	
	public void setBarcode(String barcode) {
		cashbox.setBarcode(barcode);
	}

	

	private long convertBarcode() throws NumberFormatException {
		long barcode = Long.parseLong(cashbox.getBarcode());
		if (barcode < 0) {
			throw new NumberFormatException("Barcode must be positive!");
		}
		return barcode;
	}

	public String scanBarcode() {
		long barcode;
		try {
			barcode = convertBarcode();
		} catch (NumberFormatException e) {
			
			addFacesError(Messages.getLocalizedMessage("cashdesk.validation.barcode.failed"));

			return getSalePageRedirectOutcome();
		}
		long activeStoreId = storeInformation.getActiveStoreId();
	
		
			scanner.submitBarcode(barcode, activeStoreId);
		
		
		return getSalePageRedirectOutcome();
		

	}
    
	/**
	 * Add Digit to Barcode
	 * @param digit
	 */
	public void addDigitToBarcode(char digit) {
		cashbox.addToDigit(digit);
	}
    
	/**
	 * Reset current barcode
	 */
	public void clearBarcode() {
		cashbox.resetBarcode();
	}

	/**
	 * Remove the last digit
	 */
	public void removeLastBarcodeDigit() {
		cashbox.removeLastDigit();
	}

	public void updatePrinterOutput() {
//		String cashDeskName = cashDesk.getCashDeskName();
//		long storeID = storeInformation.getActiveStoreID();
//
//		String[] printerOutput;
//
//		try {
//			printerOutput = cashDeskDAO.getPrinterOutput(cashDeskName, storeID);
//		} catch (UnhandledException_Exception | NotInDatabaseException_Exception e) {
//			addFacesError(
//					String.format(Messages.getLocalizedMessage("cashdesk.error.printer.retrieve"), e.getMessage()));
//			printerOutput = EMPTY_OUTPUT;
//		}
//		cashDesk.setPrinterOutput(printerOutput);
	}
}
