package org.cocome.storesservice.frontend.cashdeskeventhandlers;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.cocome.storesservice.events.ProductBarcodeScannedEvent;
import org.cocome.storesservice.events.SaleStartedEvent;
import org.cocome.storesservice.frontend.cashdeskcomponents.ICashBox;

/**
 * Component that controls CashBox in case an event was thrown which is determined for CashBox
 * @author Niko Benkler
 * @author Robert Heinrich
 *
 */
@Named
@SessionScoped
class CashBoxEventHandler implements  Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -113764312663218734L;

	private static final Logger LOG = Logger.getLogger(CashBoxEventHandler.class);
	
	@Inject
	private ICashBox cashBox;
	
	public void onEvent(@Observes SaleStartedEvent event) {
		LOG.debug("FRONTEND: Cashbox start new sale");
		cashBox.startSale();
		
	}
	
	public void onEvent(@Observes ProductBarcodeScannedEvent event){
		LOG.debug("FRONTEND: Cashbox Barcode Scanned");
		
		cashBox.addItemToSale(event.getBarcode(), event.getStoreId());
		
	}

}

