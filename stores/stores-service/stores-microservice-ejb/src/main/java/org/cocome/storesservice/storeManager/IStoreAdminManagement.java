package org.cocome.storesservice.storeManager;

import java.util.Collection;

import org.cocome.storesservice.domain.StockItem;

public interface IStoreAdminManagement extends ICashDeskWorker{
	void changePrice(long productId, double Price);
	
	Collection<StockItem> getStoreProducts(long storeId);
}
