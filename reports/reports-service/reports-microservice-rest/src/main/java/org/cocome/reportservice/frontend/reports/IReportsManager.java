package org.cocome.reportservice.frontend.reports;

import java.util.Collection;

import org.cocome.reportservice.frontend.viewdata.EnterpriseViewData;

public interface IReportsManager {

	void setActiveStoreId(long storeId);

	long getActiveStoreId();
	
	String getEnterpriseDeliveryReport(long enterpriseId);
	
	String getStoreStockReport();
	
	
	
	Collection<EnterpriseViewData> getEnterprises();



	boolean isEnterpriseSet();

	void setEnterpriseSet(boolean enterpriseSet);

	long getActiveEnterpriseId();

	void setActiveEnterpriseId(long activeEnterpriseId);

	String getEnterpriseStockReport();

}
