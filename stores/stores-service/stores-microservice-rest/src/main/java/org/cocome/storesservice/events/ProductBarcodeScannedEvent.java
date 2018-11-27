package org.cocome.storesservice.events;

import java.io.Serializable;


/**
 * Event emitted when barcode was scanned
 * @author Niko Benkler
 * @author Robert Heinrich
 *
 */
public class ProductBarcodeScannedEvent implements Serializable {

	private static final long serialVersionUID = -1603344911255933167L;

	//

	private final long __barcode;
	private final long __storeId;

	//

	public ProductBarcodeScannedEvent(final long barcode, final long storeId) {
		__barcode = barcode;
		__storeId = storeId;
		
		
	}

	public long getBarcode() {
		return __barcode;
	}
	
	public long getStoreId() {
		return __storeId;
	}

}