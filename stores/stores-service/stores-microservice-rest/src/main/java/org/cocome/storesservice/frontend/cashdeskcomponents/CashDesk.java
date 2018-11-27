package org.cocome.storesservice.frontend.cashdeskcomponents;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.cocome.storesservice.events.InvalidProductBarcodeEvent;
import org.cocome.storesservice.exceptions.QueryException;
import org.cocome.storesservice.frontend.stock.IStockManager;
import org.cocome.storesservice.frontend.store.IStoreInformation;
import org.cocome.storesservice.frontend.viewdata.StockItemViewData;

/**
 * Class that holds basic Information/the state of the CashDesk
 * @author Niko Benkler
 * @author Robert Heinrich
 *
 */
@Named
@SessionScoped
public class CashDesk implements Serializable, ICashDesk {

	

	@Inject
	IStoreInformation storeInfo;
	

	
	
	
	
	
	private static final Logger LOG = Logger.getLogger(CashDesk.class);

	private static final long serialVersionUID = 2531025289417846417L;

	private String cashDeskName = "defaultCashDesk";
	private boolean cashDeskNameNeeded = true;

	private boolean saleStarted = false;
	private boolean cashPayment = false;
	private boolean cardPayment = false;
	private boolean inExpressMode = false;
	

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
		
	}

	@Override
	public boolean isSaleSuccessful() {
		// TODO Auto-generated method stub
		return false;
	}
}
