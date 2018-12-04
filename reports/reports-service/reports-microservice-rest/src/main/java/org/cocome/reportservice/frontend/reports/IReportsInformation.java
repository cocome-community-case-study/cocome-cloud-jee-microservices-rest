package org.cocome.reportservice.frontend.reports;

public interface IReportsInformation {

	
	

	long getActiveStoreId();
	
	boolean isEnterpriseSet();

	void setEnterpriseSet(boolean enterpriseSet);

	long getActiveEnterpriseId();

	void setActiveEnterpriseId(long activeEnterpriseId);
}
