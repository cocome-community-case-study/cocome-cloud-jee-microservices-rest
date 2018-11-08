package org.cocome.storesservice.frontend.enterprise;

import java.util.Collection;

public interface IEnterpriseManager {

	public String createEnterprise(String enterpriseName);
	
	public Collection<EnterpriseViewData> getEnterprises();
}
