package org.cocome.storesservice.frontend.cashdeskeventhandlers;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.cocome.storesservice.events.InvalidProductBarcodeEvent;
import org.cocome.storesservice.events.ProductOutOfStockEvent;
import org.cocome.storesservice.events.RunningTotalChangedEvent;
import org.cocome.storesservice.events.SaleStartedEvent;
import org.cocome.storesservice.events.StartCashPaymentEvent;
import org.cocome.storesservice.frontend.cashdeskcomponents.IDisplay;

@Named
@SessionScoped
public class DisplayEventHandler implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2449834177820890437L;
	private static final Logger LOG = Logger.getLogger(DisplayEventHandler.class);

	@Inject
	IDisplay display;

	public void onEvent(@Observes SaleStartedEvent event) {
		LOG.debug("FRONTEND: Display start new sale");
		this.display.addDisplayLine("New Sale");
	}

	public void onEvent(@Observes InvalidProductBarcodeEvent event) {
		final long barcode = event.getBarcode();
		LOG.debug("FRONTEND: Display No product with barcode: " + event.getBarcode());
		this.display.addDisplayLine("No product for barcode " + barcode + "!");
	}
	
	public void onEvent(@Observes RunningTotalChangedEvent event) {
		LOG.debug("FRONTEND: Display new Item: " + event.getProductName());
		this.display.addDisplayLine("Add Item: " + event.getProductName() + " to Sale");;

	}
	
	public void onEvent(@Observes ProductOutOfStockEvent event ) {
		LOG.debug("FRONTEND: Display  Item out of stock: " );
		this.display.addDisplayLine(event.getEvetText());
	}
	
	public void onEvent(@Observes StartCashPaymentEvent event) {
		LOG.debug("FRONTEND: Display start Cash Payment");
		this.display.addDisplayLine("Start Cash Payment");
		
	}


	
}
