package org.cocome.reportsservice.service;

import org.cocome.productsclient.exception.ProductsRestException;
import org.cocome.reportsservice.domain.Report;
import org.cocome.storesclient.exception.StoreRestException;

/**
 * Provides methods for generating enterprise reports.
 * 
 * @author Sebastian Herold
 * @author Lubomir Bulej
 * @author Tobias Pöppke
 * @author Robert Heinrich
 * @author Nils Sommer
 */
public interface ReportGenerator {	
	/**
	 * Generates report of mean time to delivery for each supplier of the
	 * specified enterprise.
	 * 
	 * @param enterpriseId the enterprise entity identifier for which to generate report
	 * @return Report transfer object containing mean time to delivery information
	 * @throws StoreRestException 
	 * @throws ProductsRestException 
	 */
	Report getEnterpriseDeliveryReport(long enterpriseId) throws ProductsRestException;
	
	/**
	 * Generates report of available stock for all products in the specified
	 * store.
	 * 
	 * @param storeId the store entity identifier for which to generate report
	 * @return Report transfer object containing store stock information.
	 * @throws ProductsRestException 
	 * @throws StoreRestException 
	 */
	Report getStoreStockReport(long storeId) throws StoreRestException, ProductsRestException;
	
	/**
	 * Generates report of available stock for all products in all stores
	 * of the specified enterprise.
	 * 
	 * @param enterpriseId the enterprise entity identifier for which to generate report
	 * @return Report transfer object containing enterprise stock information
	 * @throws ProductsRestException 
	 * @throws StoreRestException 
	 */
	Report getEnterpriseStockReport(long enterpriseId) throws StoreRestException, ProductsRestException;
}
