package org.cocome.storesservice.frontend.cashdeskeventhandlers;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.cocome.storesservice.events.ProductBarcodeScannedEvent;
import org.cocome.storesservice.events.SaleStartedEvent;
import org.cocome.storesservice.frontend.cashdeskcomponents.ICashDesk;

@SessionScoped
@Named
class CashDeskEventHandler implements  Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -113764312663218734L;

	private static final Logger LOG = Logger.getLogger(CashDeskEventHandler.class);
	
	@Inject
	private ICashDesk cashDesk;
	
	public void onEvent(@Observes SaleStartedEvent event) {
		LOG.debug("FRONTEND: CashDesk start new sale event");
		cashDesk.startSale();
		
	}

}

