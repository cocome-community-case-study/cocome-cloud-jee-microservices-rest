package org.cocome.reportsservice.reportsquery;

import java.util.Collection;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;
import org.cocome.productsclient.exception.ProductsRestException;
import org.cocome.reportsservice.domain.Report;
import org.cocome.reportsservice.exceptions.QueryException;
import org.cocome.reportsservice.service.ReportGenerator;
import org.cocome.storesclient.client.TradingEnterpriseClient;
import org.cocome.storesclient.domain.TradingEnterpriseTO;
import org.cocome.storesclient.exception.StoreRestException;

/**
 * Backend functionality to create and provide the different kinds of reports
 * 
 * @author Niko Benkler
 * @author Robert Heinrich
 *
 */
@Local
@Stateless
public class ReportQuery implements IReportsQuery {

	@EJB
	ReportGenerator generator;
	private static final Logger LOG = Logger.getLogger(ReportQuery.class);

	/**
	 * return Enterprise Delivery Report that contains a dynamically created report
	 * as HTML-Site
	 */
	@Override
	public Report getEnterpriseDeliveryReport(long enterpriseId) throws QueryException {
		LOG.debug("QUERY: Try to get enterprise Delivery Report for Enterprise with Id: " + enterpriseId);
		try {
			return generator.getEnterpriseDeliveryReport(enterpriseId);
		} catch (StoreRestException e) {
			LOG.error("QUERY: Could not create enterprise Delivery Report for Enterprise with Id: " + enterpriseId);
			throw new QueryException(e.getMessage());
		}
	}

	/**
	 * return Store Stock Report that contains a dynamically created report as
	 * HTML-Site
	 */
	@Override
	public Report getStoreStockReport(long storeId) throws QueryException {
		LOG.debug("QUERY: Try to get Store Report for Store with id: " + storeId);

		try {
			return generator.getStoreStockReport(storeId);
		} catch (ProductsRestException | StoreRestException e) {
			LOG.error("QUERY: Could could not create store report for store with id: " + storeId);
			throw new QueryException(e.getMessage());
		}
	}

	/**
	 * return Enterprise Stock Report that contains a dynamically created report as
	 * HTML-Site
	 */
	@Override
	public Report getEnterpriseStockReport(long enterpriseId) throws QueryException {
		LOG.debug("QUERY: Try to create enterprise report for enterprise with id: " + enterpriseId);
		try {
			return generator.getEnterpriseStockReport(enterpriseId);
		} catch (ProductsRestException | StoreRestException e) {
			LOG.error("QUERY: Could not create enterprise report for enterprise with id: " + enterpriseId);
			throw new QueryException(e.getMessage());
		}
	}

	/**
	 * Returns all enterprises. It is used to display possible enterprises to the user
	 * so that one can select which report to get displayed
	 */
	@Override
	public Collection<TradingEnterpriseTO> getAllEnterprises() throws QueryException {
		LOG.debug("QUERY: Try to get alle enterprises!");
		TradingEnterpriseClient enterpriseClient = new TradingEnterpriseClient();
		
		//find
		Collection<TradingEnterpriseTO> collection;
		try {
			collection = enterpriseClient.findAll();

		} catch (StoreRestException e) {
			LOG.error("QUERY: Could not get all enterprises");
			throw new QueryException(e.getMessage());
		}

		// Logging
		StringBuilder sb = new StringBuilder();
		sb.append("QUERY: Successfully found all enterprises: [id,name] ");
		for (TradingEnterpriseTO enterpriseTO : collection) {
			sb.append("[" + enterpriseTO.getId() + "," + enterpriseTO.getName() + "]");
		}
		LOG.debug(sb.toString());

		return collection;
	}

}
