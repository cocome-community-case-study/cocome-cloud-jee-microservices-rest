package org.cocome.storesservice.events;

import java.io.Serializable;

public class InvalidProductBarcodeEvent implements Serializable {

	private static final long serialVersionUID = -354692220702852330L;

	//

	private final long __invalidBarcode;

	//

	public InvalidProductBarcodeEvent(final long invalidBarcode) {
		__invalidBarcode = invalidBarcode;
	}

	public long getBarcode() {
		return __invalidBarcode;
	}

}
