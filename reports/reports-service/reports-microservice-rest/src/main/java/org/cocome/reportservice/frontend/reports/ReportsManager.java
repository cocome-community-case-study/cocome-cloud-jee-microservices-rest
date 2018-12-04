package org.cocome.reportservice.frontend.reports;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.cocome.reportservice.frontend.viewdata.EnterpriseViewData;
import org.cocome.reportsservice.domain.Report;
import org.cocome.reportsservice.exceptions.QueryException;
import org.cocome.reportsservice.reportsquery.IReportsQuery;
import org.cocome.storesclient.domain.TradingEnterpriseTO;

/**
 *  Interface between backend functionaility and frontend for creating
 * Reports. <br>
 * 
 * 
 * @author Niko Benkler
 * @author Robert Heinrich
 *
 */

@Named
@SessionScoped
public class ReportsManager implements IReportsManager, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7926065448824775012L;
	private static final Logger LOG = Logger.getLogger(ReportsManager.class);
	

	@EJB
	IReportsQuery reportsquery;
	
	@Inject 
	IReportsInformation reportsInfo;
	
	Collection<TradingEnterpriseTO> enterprises;
	private boolean init = false;

	/**
	 * Set active store ID: No check if store really exists. This is done during the
	 * REST-request
	 */
	

	@Override
	public String getEnterpriseDeliveryReport() {
		Report report;

		try {
			report = reportsquery.getEnterpriseDeliveryReport(reportsInfo.getActiveEnterpriseId());
		} catch (QueryException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
			return null;
		}
		return report.getReportText();
	}

	@Override
	public String getStoreStockReport() {
		Report report;
		try {
			report = reportsquery.getStoreStockReport(reportsInfo.getActiveStoreId());
		} catch (QueryException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
			/*
			 * Workaround... FacesMessage is thrown during rendering process and therefore
			 * not displayed
			 */

			return e.getMessage();
		}
		return report.getReportText();
	}

	@Override
	public String getEnterpriseStockReport() {
		Report report;
		try {
			report = reportsquery.getEnterpriseStockReport(reportsInfo.getActiveEnterpriseId());
		} catch (QueryException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
			return e.getMessage();
		}
		return report.getReportText();
	}

	

	@Override
	public Collection<EnterpriseViewData> getEnterprises() {
		loadEnterprises();
		
		

		return EnterpriseViewData.fromEnterpriseTOCollectio(enterprises);
	}
	
	/**
	 * Init Enterprises. Executed in metadata of enterpriseStockReport
	 */
	public void loadEnterprises() {
		
		if(init) {
			return;
		}
		enterprises = new LinkedList<>();
		init =true;
		try {
			enterprises = reportsquery.getAllEnterprises();
			if(enterprises.isEmpty()) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "No Enterprises Available", null));
			}
		} catch (QueryException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));

		}
		
		
	}


}
