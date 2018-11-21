package org.cocome.storesserviceservice.StoreQuery;

import java.util.Collection;

import javax.validation.constraints.NotNull;

import org.cocome.storesservice.domain.Store;
import org.cocome.storesservice.exceptions.CreateException;
import org.cocome.storesservice.exceptions.QueryException;

public interface IStoreQuery {

	long createStore(@NotNull String storeName, @NotNull String storeLocation, @NotNull Long enterpriseId) throws CreateException;

	Collection<Store> getAllStores();

	Store getStoreById(@NotNull long storeId) throws QueryException;

	Collection<Store> getStoresOfEnterprise(@NotNull long enterpriseId) throws QueryException;

	void updateStore(@NotNull long storeId, @NotNull String newName, @NotNull String newLocation) throws QueryException;

	

	void deleteStore(long storeId) throws QueryException;

}
