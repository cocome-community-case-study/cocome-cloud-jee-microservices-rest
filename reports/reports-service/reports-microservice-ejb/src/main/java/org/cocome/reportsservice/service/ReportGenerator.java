package org.cocome.reportsservice.service;

import org.cocome.reportsservice.domain.Report;

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
	 */
	Report getEnterpriseDeliveryReport(long enterpriseId);
	
	/**
	 * Generates report of available stock for all products in the specified
	 * store.
	 * 
	 * @param storeId the store entity identifier for which to generate report
	 * @return Report transfer object containing store stock information.
	 */
	Report getStoreStockReport(long storeId);
	
	/**
	 * Generates report of available stock for all products in all stores
	 * of the specified enterprise.
	 * 
	 * @param enterpriseId the enterprise entity identifier for which to generate report
	 * @return Report transfer object containing enterprise stock information
	 */
	Report getEnterpriseStockReport(long enterpriseId);
}
