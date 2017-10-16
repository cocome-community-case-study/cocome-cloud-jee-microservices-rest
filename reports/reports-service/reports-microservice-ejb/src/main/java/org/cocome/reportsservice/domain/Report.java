package org.cocome.reportsservice.domain;

import java.io.Serializable;

public class Report implements Serializable {
	private static final long serialVersionUID = -5119702205787746189L;
	
	private String reportText;
	
	public String getReportText() {
		return this.reportText;
	}
	
	public void setReportText(String reportText) {
		this.reportText = reportText;
	}
}
