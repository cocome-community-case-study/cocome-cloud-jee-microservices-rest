package org.cocome.productsservice.controller;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import org.cocome.reportsservice.domain.Report;
import org.cocome.reportsservice.service.ReportGenerator;

@RequestScoped
@Path("/reports")
public class ReportResource {
	@EJB
	private ReportGenerator reportGenerator;
	
	//
	// POST /reports?type={report type}&id={enterprise or store id}
	//
	@POST
	public Report generateReport(@DefaultValue("enterprise-stock") @QueryParam("type") String type, @QueryParam("id") Long id) {
		Report report;
		
		switch (type) {
		case "enterprise-delivery":
			report = reportGenerator.getEnterpriseDeliveryReport(id);
			break;
		case "store-stock":
			report = reportGenerator.getStoreStockReport(id);
			break;
		case "enterprise-stock":
			report = reportGenerator.getEnterpriseStockReport(id);
			break;
		default:
			report = reportGenerator.getEnterpriseStockReport(id);
			break;
		}
		
		return report;
	}
}
