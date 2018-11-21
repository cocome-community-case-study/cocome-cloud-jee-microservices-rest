package org.cocome.storesservice.frontend.store;

import javax.validation.constraints.NotNull;

import org.cocome.storesservice.exceptions.QueryException;
import org.cocome.storesservice.frontend.viewdata.StoreViewData;

public interface IStoreInformation {
	public long getActiveStoreId();
	public void setActiveStoreId(long storeId) throws QueryException;
	public StoreViewData getActiveStore();
	public void setActiveStore(@NotNull StoreViewData store);
	public boolean isStoreSet();
	public void resetStore();
	public String switchToStore(@NotNull long storeId);
	public String switchToStock(@NotNull long storeId);
}
