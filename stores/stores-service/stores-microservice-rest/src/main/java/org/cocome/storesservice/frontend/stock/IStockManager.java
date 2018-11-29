package org.cocome.storesservice.frontend.stock;

import java.util.Collection;

import org.cocome.storesservice.exceptions.QueryException;
import org.cocome.storesservice.frontend.viewdata.StockItemViewData;

public interface IStockManager {
	
	Collection<StockItemViewData> getStockItemsByStore(long storeId) throws QueryException;
	
	void updateStockItem(StockItemViewData item) throws QueryException;

	StockItemViewData getStockItemByBarcodeAndStoreId(long barcode, long storeId) throws QueryException;

}
