package org.cocome.reportsservice.domain;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;

@XmlType(name = "Report")
@XmlAccessorType(XmlAccessType.FIELD)
public class Report implements Serializable {
	private static final long serialVersionUID = -5119702205787746189L;
	
	@XmlAttribute(name="reportText")
	private String reportText;
	
	public String getReportText() {
		return this.reportText;
	}
	
	public void setReportText(String reportText) {
		this.reportText = reportText;
	}
}
