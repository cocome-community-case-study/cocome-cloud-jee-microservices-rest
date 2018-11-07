package org.cocome.tobiAlt;

import java.util.Collection;

import org.cocome.storesservice.domain.StockItem;

public interface IStoreManagement extends ICashDeskWorker{
	
	
	void changePrice(long productId, double Price);
	
	Collection<StockItem> getStoreProducts(long storeId);
}
