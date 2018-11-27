package org.cocome.storesservice.frontend.cashdeskcomponents;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;

import org.cocome.storesservice.events.ProductBarcodeScannedEvent;
import org.cocome.storesservice.exceptions.NoSuchProductException;
import org.cocome.storesservice.exceptions.QueryException;
import org.cocome.storesservice.frontend.stock.IStockManager;
import org.cocome.storesservice.frontend.store.IStoreManager;
import org.cocome.storesservice.frontend.viewdata.StockItemViewData;

@Named
@SessionScoped
public class Scanner implements IScanner, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1161537604011899065L;

	@Inject
	IStockManager stockManager;

	@Inject
	IStoreManager storeManager;

	@Inject
	private Event<ProductBarcodeScannedEvent> productBarcodeScannedEvent;

	@Override
	public void submitBarcode(long barcode, long storeId) {

		productBarcodeScannedEvent.fire(new ProductBarcodeScannedEvent(barcode, storeId));
	}

}
