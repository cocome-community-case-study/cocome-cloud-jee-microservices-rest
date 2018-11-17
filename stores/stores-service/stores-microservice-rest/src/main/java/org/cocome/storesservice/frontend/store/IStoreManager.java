package org.cocome.storesservice.frontend.store;

import java.util.Collection;

import org.cocome.storesservice.frontend.viewdata.StoreViewData;

public interface IStoreManager {
public String createStore(String storeName, String location, long enterpriseId);
	
	public Collection<StoreViewData> getStores();
	
	public StoreViewData getStoreById(long storeId);
	
	public Collection<StoreViewData> getStoresByEnterpriseId(long enterpriseId);
	
	public String updateStore(long storeId, String newName, String newLocation);
}
