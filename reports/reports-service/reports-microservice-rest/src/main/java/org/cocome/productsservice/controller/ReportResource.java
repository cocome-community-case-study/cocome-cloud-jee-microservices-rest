package org.cocome.productsservice.controller;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.cocome.reportsservice.domain.Report;
import org.cocome.reportsservice.service.ReportGenerator;

@RequestScoped
@Path("/reports")
public class ReportResource {
	@EJB
	private ReportGenerator reportGenerator;
	
	@POST
	public Report generateReport() {
		return reportGenerator.generate();
	}
}
