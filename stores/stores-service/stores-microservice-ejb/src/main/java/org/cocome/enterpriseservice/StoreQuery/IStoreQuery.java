package org.cocome.enterpriseservice.StoreQuery;

import java.util.Collection;

import javax.validation.constraints.NotNull;

import org.cocome.storesservice.domain.Store;


public interface IStoreQuery {
	

	public boolean createStore(@NotNull String storeName, @NotNull String storeLocation, @NotNull Long enterpriseId);
	
	public Collection<Store> getAllStores();
	
	public Store getStoreById(@NotNull long storeId);
	
	public Collection<Store> getStoresOfEnterprise(@NotNull long enterpriseId);

}
