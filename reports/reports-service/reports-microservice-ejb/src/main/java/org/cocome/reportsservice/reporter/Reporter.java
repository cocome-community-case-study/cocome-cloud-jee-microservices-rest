package org.cocome.reportsservice.reporter;

import java.util.Collection;

import org.cocome.ordersclient.domain.ProductOrder;
import org.cocome.storesclient.domain.StockItem;

import restCommunicator.OrderCommunicator;
import restCommunicator.StoreCommunicator;

public class Reporter implements IReporter{
	// enable different filters
	
	
	@Override
	public Collection<ProductOrder> getEnterpriseReport(long enterpriseId, long storeId) {
		Collection<ProductOrder> report = OrderCommunicator.getOrderReport(storeId);
		return report;
	}

	@Override
	public Collection<StockItem> getStoreReport(long enterpriseId, long storeId) {
		Collection<StockItem> report = StoreCommunicator.getStoreReport(storeId);
		return report;
	}

}
