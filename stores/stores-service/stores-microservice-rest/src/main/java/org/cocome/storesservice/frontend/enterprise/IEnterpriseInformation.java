package org.cocome.storesservice.frontend.enterprise;

import java.util.Collection;

import javax.validation.constraints.NotNull;

import org.cocome.storesservice.frontend.viewdata.EnterpriseViewData;
import org.cocome.storesservice.frontend.viewdata.StoreViewData;

public interface IEnterpriseInformation {

	
	public long getActiveEnterpriseId();
	public void setActiveEnterpriseId(long enterpriseId);
	public EnterpriseViewData getActiveEnterprise();
	public void setActiveEnterprise(@NotNull EnterpriseViewData enterprise);
	public Collection<StoreViewData> getStores();
	boolean isEnterpriseSet();
	
}
