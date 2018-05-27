package org.cocome.reportsservice.reporter;

import restCommunicator.OrderCommunicator;
import restCommunicator.StoreCommunicator;

public class Reporter implements IReportPresentator{
	// enable different filters
	
	
	@Override
	public String[] getEnterpriseReport(long enterpriseId) {
		String[] report = OrderCommunicator.getOrderReport();
		return report;
	}

	@Override
	public String[] getStoreReport(long enterpriseId, long storeId) {
		String[] report = StoreCommunicator.getStoreReport(storeId);
		return report;
	}

}
