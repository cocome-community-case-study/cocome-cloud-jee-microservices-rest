package org.cocome.storesservice.frontend.cashdeskcomponents;



import org.cocome.storesservice.exceptions.QueryException;
import org.cocome.storesservice.frontend.viewdata.StockItemViewData;

public interface IScanner {
	
	public void submitBarcode(long barcode, long storeId);
}
