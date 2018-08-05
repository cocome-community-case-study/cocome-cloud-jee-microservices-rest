package org.cocome.reportsservice.reporter;

public interface IReports {
	public String[] getEnterpriseReport(long enterpriseId);
	public String[] getStoreReport(long enterpriseId, long storeId);
}
