package org.cocome.reportsservice.service;

import javax.ejb.Remote;
import javax.ejb.Stateless;

import org.cocome.reportsservice.domain.Report;

@Remote
@Stateless
public class HTMLReportGenerator implements ReportGenerator {
	@Override
	public Report generate() {
		return new Report();
	}
}
