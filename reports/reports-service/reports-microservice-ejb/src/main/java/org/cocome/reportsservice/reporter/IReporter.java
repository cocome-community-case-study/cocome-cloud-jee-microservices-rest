package org.cocome.reportsservice.reporter;

import java.util.Collection;

import org.cocome.ordersclient.domain.ProductOrder;
import org.cocome.storesclient.domain.StockItem;

public interface IReporter {
	public Collection<ProductOrder> getEnterpriseReport(long enterpriseId, long storeId);
	public Collection<StockItem> getStoreReport(long enterpriseId, long storeId);
}
