package org.cocome.reportsservice.reportsquery;

import java.util.Collection;
import org.cocome.reportsservice.domain.Report;
import org.cocome.reportsservice.exceptions.QueryException;
import org.cocome.storesclient.domain.TradingEnterpriseTO;


public interface IReportsQuery {
	
	Report getEnterpriseDeliveryReport(long enterpriseId) throws  QueryException;
	Report getStoreStockReport(long storeId) throws QueryException;
	Report getEnterpriseStockReport(long enterpriseId) throws QueryException;
	Collection<TradingEnterpriseTO> getAllEnterprises() throws QueryException;
	

}
