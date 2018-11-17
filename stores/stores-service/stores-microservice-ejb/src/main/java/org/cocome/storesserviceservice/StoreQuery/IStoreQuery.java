package org.cocome.storesserviceservice.StoreQuery;

import java.util.Collection;

import javax.validation.constraints.NotNull;

import org.cocome.storesservice.domain.Store;

public interface IStoreQuery {

	long createStore(@NotNull String storeName, @NotNull String storeLocation, @NotNull Long enterpriseId);

	Collection<Store> getAllStores();

	Store getStoreById(@NotNull long storeId);

	Collection<Store> getStoresOfEnterprise(@NotNull long enterpriseId);

	boolean updateStore(@NotNull long storeId, @NotNull String newName, @NotNull String newLocation);

	

	boolean deleteStore(long storeId);

}
