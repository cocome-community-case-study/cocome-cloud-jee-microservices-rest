package org.cocome.storesservice.frontend.enterprise;

import java.util.Collection;

import org.cocome.storesservice.frontend.viewdata.EnterpriseViewData;

public interface IEnterpriseManager {

	public String createEnterprise(String enterpriseName);
	
	public Collection<EnterpriseViewData> getEnterprises();
	
	public EnterpriseViewData getEnterpriseById(long enterpriseId);
}
