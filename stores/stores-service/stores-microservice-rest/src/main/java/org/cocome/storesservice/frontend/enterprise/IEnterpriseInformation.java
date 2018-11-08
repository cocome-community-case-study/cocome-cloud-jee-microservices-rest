package org.cocome.storesservice.frontend.enterprise;

import javax.validation.constraints.NotNull;

import org.cocome.storesservice.frontend.viewdata.EnterpriseViewData;

public interface IEnterpriseInformation {

	
	public long getActiveEnterpriseId();
	public void setActiveEnterpriseId(long enterpriseId);
	public EnterpriseViewData getActiveEnterprise();
	public void setActiveEnterprise(@NotNull EnterpriseViewData enterprise);
}
