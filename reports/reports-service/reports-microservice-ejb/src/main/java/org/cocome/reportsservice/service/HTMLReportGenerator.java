package org.cocome.reportsservice.service;

import java.util.Collection;
import java.util.Formatter;

import javax.ejb.Local;
import javax.ejb.Stateless;

import org.cocome.productsclient.client.ProductClient;
import org.cocome.productsclient.client.ProductSupplierClient;
import org.cocome.productsclient.domain.ProductSupplierTO;
import org.cocome.productsclient.domain.ProductTO;
import org.cocome.productsclient.exception.ProductsRestException;
import org.cocome.reportsservice.domain.Report;
import org.cocome.storesclient.client.StockItemClient;
import org.cocome.storesclient.client.StoreClient;
import org.cocome.storesclient.client.TradingEnterpriseClient;
import org.cocome.storesclient.domain.StockItemTO;
import org.cocome.storesclient.domain.StoreTO;
import org.cocome.storesclient.domain.TradingEnterpriseTO;
import org.cocome.storesclient.exception.StoreRestException;

/**
 * HTML Generator for the reports. Information is loaded dynamically via rest
 * calls from the specific services
 * 
 * @author Niko Benkler
 * @author Robert Heinrich
 *
 */
@Local
@Stateless
public class HTMLReportGenerator implements ReportGenerator {

	private TradingEnterpriseClient enterpriseClient = new TradingEnterpriseClient();
	private StoreClient storeClient = new StoreClient();
	private StockItemClient stockItemClient = new StockItemClient();
	private ProductSupplierClient supplierClient = new ProductSupplierClient();
	private ProductClient productClient = new ProductClient();

	/**
	 * Returns full enterprise delivery report as HTML Page
	 * 
	 * @throws ProductsRestException
	 */
	@Override
	public Report getEnterpriseDeliveryReport(long enterpriseId) throws ProductsRestException {

		final Collection<ProductSupplierTO> suppliers = this.supplierClient.findAll();
		final Formatter report = new Formatter();
		appendReportHeader(report);
		appendDeliveryReport(suppliers, report);
		appendReportFooter(report);
		return createReportTO(report);
	}

	/**
	 * Returns full store report as HTML Page
	 */
	@Override
	public Report getStoreStockReport(long storeId) throws StoreRestException, ProductsRestException {
		final StoreTO store = this.storeClient.find(storeId);
		final Formatter report = new Formatter();
		appendReportHeader(report);
		appendStoreReport(store, report);
		appendReportFooter(report);
		return createReportTO(report);
	}

	/**
	 * Returns full enterprise stock report as HTML Page
	 */
	@Override
	public Report getEnterpriseStockReport(long enterpriseId) throws StoreRestException, ProductsRestException {
		TradingEnterpriseTO enterprise = this.enterpriseClient.find(enterpriseId);
		Formatter report = new Formatter();
		appendReportHeader(report);
		appendEnterpriseReport(enterprise, report);
		appendReportFooter(report);
		return createReportTO(report);
	}

	// -------------------------------Private helper Methods------------

	private void appendDeliveryReport(Collection<ProductSupplierTO> suppliers, Formatter output) {
		output.format(
				"<h3> UC6 - \"Show Delivery Reports\" is not implemented as we do not have an actual delivery time. Therefore, we only provide a basic overview of the available suppliers  </h3>\n");

		this.appendTableHeader(output, "Supplier ID", "Supplier Name", "Mean Time To Delivery");

		for (ProductSupplierTO supplier : suppliers) {
			this.appendTableRow(output, supplier.getId(), supplier.getName(), "N/A");
		}

		this.appendTableFooter(output);
	}

	private void appendStoreReport(final StoreTO store, final Formatter output)
			throws StoreRestException, ProductsRestException {
		output.format("<h3>Report for %s at %s, id %d</h3>\n", store.getName(), store.getLocation(), store.getId());

		this.appendTableHeader(output, "StockItem ID", "Product Name", "Amount", "Min Stock", "Max Stock");

		final Collection<StockItemTO> stockItems = this.stockItemClient.findByStore(store.getId());

		for (final StockItemTO si : stockItems) {
			ProductTO product = this.productClient.find(si.getProductId());
			this.appendTableRow(output, si.getId(), product.getName(), si.getAmount(), si.getMinStock(),
					si.getMaxStock());
		}

		this.appendTableFooter(output);
	}

	private void appendEnterpriseReport(final TradingEnterpriseTO enterprise, final Formatter output)
			throws StoreRestException, ProductsRestException {
		output.format("<h2>Stock report for %s</h2>\n", enterprise.getName());

		for (final StoreTO store : this.storeClient.findByEnterprise(enterprise.getId())) {
			this.appendStoreReport(store, output);
		}
	}

	private Report createReportTO(final Formatter report) {
		final Report result = new Report();
		result.setReportText(report.toString());
		return result;
	}

	private Formatter appendReportFooter(final Formatter output) {
		return output.format("</body></html>\n");
	}

	private Formatter appendReportHeader(final Formatter output) {
		return output.format("<html><body>\n");
	}

	private void appendTableHeader(final Formatter output, final String... names) {
		output.format("<table>\n<tr>");
		for (final String name : names) {
			output.format("<th>%s</th>", name);
		}
		output.format("</tr>\n");
	}

	private void appendTableRow(final Formatter output, final Object... values) {
		output.format("<tr>");
		for (final Object value : values) {
			output.format("<td>%s</td>", value);
		}
		output.format("</tr>\n");
	}

	private void appendTableFooter(final Formatter output) {
		output.format("</table><br/>\n");
	}
}
