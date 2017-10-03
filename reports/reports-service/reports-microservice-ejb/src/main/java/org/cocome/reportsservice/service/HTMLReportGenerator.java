package org.cocome.reportsservice.service;

import javax.ejb.Remote;
import javax.ejb.Stateless;

import org.cocome.reportsservice.domain.Report;

@Remote
@Stateless
public class HTMLReportGenerator implements ReportGenerator {
	@Override
	public Report getEnterpriseDeliveryReport(long enterpriseId) {
		throw new RuntimeException("Not implemented yet!");
	}

	@Override
	public Report getStoreStockReport(long storeId) {
		throw new RuntimeException("Not implemented yet!");
	}

	@Override
	public Report getEnterpriseStockReport(long enterpriseId) {
		throw new RuntimeException("Not implemented yet!");
	}
}
