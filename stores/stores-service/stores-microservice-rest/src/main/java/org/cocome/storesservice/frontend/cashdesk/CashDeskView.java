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
import org.cocome.storesservice.events.StartCardPaymentEvent;
import org.cocome.storesservice.events.StartCashPaymentEvent;
import org.cocome.storesservice.exceptions.UpdateException;
import org.cocome.storesservice.frontend.cashdeskcomponents.ICashBox;
import org.cocome.storesservice.frontend.cashdeskcomponents.ICashDesk;
import org.cocome.storesservice.frontend.cashdeskcomponents.IDisplay;
import org.cocome.storesservice.frontend.cashdeskcomponents.IExpressLight;
import org.cocome.storesservice.frontend.cashdeskcomponents.IPrinter;
import org.cocome.storesservice.frontend.cashdeskcomponents.IScanner;
import org.cocome.storesservice.frontend.store.IStoreInformation;
import org.cocome.storesservice.navigation.NavigationElements;
import org.cocome.storesservice.util.Messages;

/**
 * This class is the interface for the JSF-Sites concerning the whole
 * sales-Process. It reroutes the queries to the specific classes
 * 
 * @author Niko Benkler
 * @author Robert Heinrich
 *
 */
@Named
@SessionScoped
public class CashDeskView implements Serializable {
	private static final long serialVersionUID = -2512543291563857980L;



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
	
	@Inject
	Event<StartCardPaymentEvent> startCardPaymentEvent;
	
	

	/**
	 * Submitting a new CashDeskName start a new sale Process
	 * @return
	 */
	public String submitCashDeskName() {
		return resetSale();
	}
	
	/**
	 * Used for rerendering View
	 * @return
	 */
	private String getSalePageRedirectOutcome() {
		return NavigationElements.START_SALE.getNavigationOutcome();
	}

	/**
	 * Set name of the currently active CashDesk
	 * 
	 * @param cashDeskName
	 */
	public void setCashDeskName(String cashDeskName) {
		cashDesk.setCashDeskName(cashDeskName);
	}

	/**
	 * Get Name of the currently active CashDesk
	 * 
	 * @return
	 */
	public String getCashDeskName() {
		return cashDesk.getCashDeskName();
	}
	
	public String resetExpressMode() {
		cashDesk.resetExpressMode();
		return getSalePageRedirectOutcome();
	}

	/**
	 * Determines whether user has to submit a CashDeskName
	 * 
	 * @return
	 */
	public boolean isCashDeskNameNeeded() {
		boolean bool = cashDesk.isCashDeskNameNeeded();
		return bool;
	}

	/**
	 * Determines whether sale already started
	 * 
	 * @return
	 */
	public boolean isSaleStarted() {
		return cashDesk.isSaleStarted();
	}

	/**
	 * Determines wheter CashDesk is in ExpressMode
	 * 
	 * @return
	 */
	public boolean isInExpressMode() {
		return expressLight.isInExpressMode();

	}

	/**
	 * Return Message that should be Displayed
	 * 
	 * @return
	 */
	public String getDisplayMessage() {
		return display.getDisplayLine();
	}

	/**
	 * Return the lines for the printer
	 * 
	 * @return
	 */
	public List<String> getPrinterOutput() {
		return printer.getPrinterOutput();
	}

	

	private void addFacesError(String errorString) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
				errorString,
				null));
	}

	public String enterCashAmount(double cashAmount) {
		if(cashDesk.isSaleFinished()) {
			return getSalePageRedirectOutcome();
		}
		try {
			cashbox.enterCashAmount(cashAmount);
		} catch (UpdateException e) {
			addFacesError(e.getMessage());
		}

		return getSalePageRedirectOutcome();
	}

	public String enterCardInfo(String cardInfo, int pin) {
		if(cashDesk.isSaleFinished()) {
			return getSalePageRedirectOutcome();
		}
		
		try {
			cashbox.enterCardInfo(cardInfo, pin);
		} catch (UpdateException e) {
			addFacesError(e.getMessage());
		}
		
		
		return getSalePageRedirectOutcome();
	}

	public String startCashPayment() {

		// Payment running
		if (cashDesk.isCardPayment() || cashDesk.isCashPayment()) {

			return getSalePageRedirectOutcome();
		}

		startCashPaymentEvent.fire(new StartCashPaymentEvent());

		return getSalePageRedirectOutcome();
	}

	public String startCardPayment() {


		// Payment running
		if (cashDesk.isCardPayment() || cashDesk.isCashPayment()) {

			return getSalePageRedirectOutcome();
		}

		startCardPaymentEvent.fire(new StartCardPaymentEvent());

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
		if(cashbox.getBarcode() == "") {
			throw new NumberFormatException("Pleaser insert a Barcode!");
		}
		
		long barcode = Long.parseLong(cashbox.getBarcode());
		if (barcode < 0) {
			throw new NumberFormatException("Barcode must be positive!");
		}
		return barcode;
	}

	public String scanBarcode() {
		if(cashDesk.paymentInProcess()) {
			return getSalePageRedirectOutcome();
		}
		
		long barcode;
		try {
			barcode = convertBarcode();
		} catch (NumberFormatException e) {

			addFacesError("Please insert a valid positive barcode!");

			return getSalePageRedirectOutcome();
		}
		long activeStoreId = storeInformation.getActiveStoreId();
		scanner.submitBarcode(barcode, activeStoreId);

		return getSalePageRedirectOutcome();

	}

	/**
	 * Add Digit to Barcode
	 * 
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
	
	

}
