package org.cocome.storesservice.frontend.store;

import javax.validation.constraints.NotNull;

import org.cocome.storesservice.frontend.viewdata.EnterpriseViewData;
import org.cocome.storesservice.frontend.viewdata.StoreViewData;

public interface IStoreInformation {
	public long getActiveStoreId();
	public void setActiveStoreId(long storeId);
	public StoreViewData getActiveStore();
	public void setActiveStore(@NotNull StoreViewData store);
	public boolean isStoreSet();
	public void resetStore();
	public String switchToStore(@NotNull StoreViewData store);
	public String switchToStock(@NotNull StoreViewData store);
}
