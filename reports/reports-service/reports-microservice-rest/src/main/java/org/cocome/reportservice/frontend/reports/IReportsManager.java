package org.cocome.reportservice.frontend.reports;

import java.util.Collection;

import org.cocome.reportservice.frontend.viewdata.EnterpriseViewData;
import org.cocome.reportsservice.exceptions.QueryException;

public interface IReportsManager {

	void setActiveStoreId(long storeId);

	long getActiveStoreId();
	
	String getEnterpriseDeliveryReport(long enterpriseId);
	
	String getStoreStockReport();
	
	String getEnterpriseStockReport(long enterpriseId);
	
	Collection<EnterpriseViewData> getEnterprises();

}
