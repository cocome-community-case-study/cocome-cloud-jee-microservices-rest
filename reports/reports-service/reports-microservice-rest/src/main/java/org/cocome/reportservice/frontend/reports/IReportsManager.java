package org.cocome.reportservice.frontend.reports;

import java.util.Collection;

import org.cocome.reportservice.frontend.viewdata.EnterpriseViewData;

public interface IReportsManager {


	
	String getEnterpriseDeliveryReport();
	
	String getStoreStockReport();
	
	
	
	Collection<EnterpriseViewData> getEnterprises();





	String getEnterpriseStockReport();

}
