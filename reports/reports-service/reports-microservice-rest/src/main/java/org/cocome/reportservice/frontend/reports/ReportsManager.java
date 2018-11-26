package org.cocome.reportservice.frontend.reports;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.cocome.reportservice.frontend.viewdata.EnterpriseViewData;
import org.cocome.reportsservice.domain.Report;
import org.cocome.reportsservice.events.UserInformationProcessedEvent;
import org.cocome.reportsservice.exceptions.QueryException;
import org.cocome.reportsservice.navigation.NavigationElements;
import org.cocome.reportsservice.navigation.NavigationView;
import org.cocome.reportsservice.reportsquery.IReportsQuery;
import org.cocome.storesclient.domain.TradingEnterpriseTO;


@Named
@SessionScoped
public class ReportsManager implements IReportsManager, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7926065448824775012L;
	private static final Logger LOG = Logger.getLogger(ReportsManager.class);
	private long activeStoreId = Long.MIN_VALUE;
	private long activeEnterpriseId = Long.MIN_VALUE;
	private boolean enterpriseSet = false;

	@EJB
	IReportsQuery reportsquery;

	/**
	 * Set active store ID: No check if store really exists. This is done during the
	 * REST-request
	 */
	@Override
	public void setActiveStoreId(long storeId) {
		LOG.debug("Active Store set to: " + storeId);
		activeStoreId = storeId;

	}

	@Override
	public long getActiveStoreId() {
		return activeStoreId;
	}
	
	
	
		

	
	@Override
	public long getActiveEnterpriseId() {
		return activeEnterpriseId;
	}
    
	@Override
	public void setActiveEnterpriseId(long activeEnterpriseId) {
		LOG.debug("Active enterprise set to: " + activeEnterpriseId);
		enterpriseSet = true;
		this.activeEnterpriseId = activeEnterpriseId;
	}

	public void selectEnterprise(long enterpriseId) {
		LOG.debug("Active enterprise set to: " + enterpriseId);
		enterpriseSet = true;
		this.activeEnterpriseId =enterpriseId;
		
	}
	
	

	

	@Override
	public String getEnterpriseDeliveryReport(long enterpriseId) {
		Report report;
		
		try {
			report = reportsquery.getEnterpriseDeliveryReport(enterpriseId);
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
			report = reportsquery.getStoreStockReport(activeStoreId);
		} catch (QueryException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
			/*
			 * Workaround... FacesMessage is thrown during rendering process and therefore not displayed
			 */
			
			return e.getMessage();
		}
		return report.getReportText();
	}

	@Override
	public String getEnterpriseStockReport() {
		Report report;
		try {
			report = reportsquery.getEnterpriseStockReport(activeEnterpriseId);
		} catch (QueryException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
			return null;
		}
		return report.getReportText();
	}

	/**
	 * Process Login Information to set active store
	 * 
	 * @param event
	 */
	public void observe(@Observes UserInformationProcessedEvent event) {
		setActiveStoreId(event.getStoreID());

	}

	@Override
	public Collection<EnterpriseViewData> getEnterprises() {
		Collection<TradingEnterpriseTO> enterprises = new LinkedList<>();
		try {
			enterprises = reportsquery.getAllEnterprises();
		} catch (QueryException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
			
		}

		return EnterpriseViewData.fromEnterpriseTOCollectio(enterprises);
	}

	@Override
	public boolean isEnterpriseSet() {
		return enterpriseSet;
	}

	@Override
	public void setEnterpriseSet(boolean enterpriseSet) {
		this.enterpriseSet = enterpriseSet;
	}

}
