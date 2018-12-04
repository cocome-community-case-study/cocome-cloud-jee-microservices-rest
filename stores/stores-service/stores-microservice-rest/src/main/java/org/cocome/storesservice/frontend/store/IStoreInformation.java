package org.cocome.storesservice.frontend.store;

import java.util.Collection;

import javax.validation.constraints.NotNull;

import org.cocome.storesservice.exceptions.QueryException;
import org.cocome.storesservice.frontend.viewdata.StockItemViewData;
import org.cocome.storesservice.frontend.viewdata.StoreViewData;

public interface IStoreInformation {
	long getActiveStoreId();

	void setActiveStoreId(long storeId) throws QueryException;

	StoreViewData getActiveStore();

	void setActiveStore(@NotNull StoreViewData store);

	boolean isStoreSet();

	void resetStore();

	String switchToStore(@NotNull long storeId);

	String switchToStock(@NotNull long storeId);

	void loadItems();

	Collection<StockItemViewData> getStockItems();
	
	void updateStockItem(StockItemViewData item);

	String getActiveStoreName();

}
