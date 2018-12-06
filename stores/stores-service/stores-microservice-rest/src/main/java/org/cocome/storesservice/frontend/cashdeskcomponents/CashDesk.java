package org.cocome.storesservice.frontend.cashdeskcomponents;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.cocome.storesservice.events.EnableExpressModeEvent;
import org.cocome.storesservice.frontend.store.IStoreInformation;

/**
 * Class that holds basic Information/the state of the CashDesk
 * 
 * @author Niko Benkler
 * @author Robert Heinrich
 *
 */
@Named
@SessionScoped
public class CashDesk implements Serializable, ICashDesk {

	@Inject
	IStoreInformation storeInfo;
	
	@Inject
	Event<EnableExpressModeEvent> enableExpressModeEvent;

	private static final Logger LOG = Logger.getLogger(CashDesk.class);

	private static final long serialVersionUID = 2531025289417846417L;

	private String cashDeskName = "defaultCashDesk";

	//lessequal 8 items are express mode sales
	private final int MAX_ITEMS_EXPRESS_MODE = 8;

	// Max 4 sales with less than 8 items, till express mode gets enabled
	private final int MAX_EXPRESS_MODE_SALES = 4;

	//counter for expressmodesale in a row
	private int numberOfExpressModeSales = 0;

	/*
	 * The following parameters represent the state of the CashDesk. We did not want
	 * to implement an oversized StateMachine as a few simple parameters do the job.
	 */
	private boolean cashDeskNameNeeded = true;
	private boolean saleStarted = false;
	private boolean cashPayment = false;
	private boolean cardPayment = false;
	private boolean inExpressMode = false;
	private boolean saleFinished = false;

	@Override
	public boolean isSaleStarted() {
		return saleStarted;
	}

	@Override
	public void setSaleStarted(boolean saleStarted) {
		this.saleStarted = saleStarted;
	}

	@Override
	public boolean isCashPayment() {
		return cashPayment;
	}

	@Override
	public void setCashPayment(boolean cashPayment) {
		this.cashPayment = cashPayment;
	}
	
	@Override
	public int getMaxItemsExpressMode() {
		return this.MAX_ITEMS_EXPRESS_MODE;
	}

	@Override
	public boolean isCardPayment() {
		return cardPayment;
	}

	@Override
	public void setCardPayment(boolean cardPayment) {
		this.cardPayment = cardPayment;
	}

	@Override
	public boolean isInExpressMode() {
		return inExpressMode;
	}

	@Override
	public void setInExpressMode(boolean inExpressMode) {
		this.inExpressMode = inExpressMode;
	}

	@Override
	public String getCashDeskName() {
		return cashDeskName;
	}

	@Override
	public void setCashDeskName(String cashDeskName) {
		this.cashDeskName = cashDeskName;
	}

	@Override
	public boolean isCashDeskNameNeeded() {
		return cashDeskNameNeeded;
	}

	@Override
	public void setCashDeskNameNeeded(boolean cashDeskNameNeeded) {
		this.cashDeskNameNeeded = cashDeskNameNeeded;
	}

	@Override
	public void requestNewCashDesk() {
		cashDeskNameNeeded = true;
	}

	/**
	 * Start or reset Sale
	 */
	@Override
	public void startSale() {
		setCashDeskNameNeeded(false);
		setCashPayment(false);
		setCardPayment(false);
		setSaleStarted(true);
		setSaleFinished(false);
		

	}

	@Override
	public boolean isSaleFinished() {
		return saleFinished;
	}

	@Override
	public void setSaleFinished(boolean saleFinished) {
		this.saleFinished = saleFinished;
	}

	@Override
	public boolean isSaleSuccessful() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean paymentInProcess() {
		return (this.cardPayment || this.cashPayment);
	}

	/**
	 * Express Mode Policy: If the last 4 Sales had less than 8 Items, change to
	 * express mode
	 */
	@Override
	public void checkExpressMode(final int numberOfSaleItems) {
		if(isInExpressMode()) return;
		
		//increase counter
		if (numberOfSaleItems > MAX_ITEMS_EXPRESS_MODE) {
			LOG.debug("FRONTEND: Reset expressmode Counter");
			// reset number of sale Items
			numberOfExpressModeSales = 0;
		} else {
			numberOfExpressModeSales++;
			LOG.debug("FRONTEND: increase ExpressMode counter. Counter is at: " + numberOfExpressModeSales );
		}
		
		if (numberOfExpressModeSales >= MAX_EXPRESS_MODE_SALES) {
			LOG.debug("FRONEND: Fire EnableExpressMode event");
			setInExpressMode(true);
			enableExpressModeEvent.fire(new EnableExpressModeEvent());
		}
	}
	
	
}
