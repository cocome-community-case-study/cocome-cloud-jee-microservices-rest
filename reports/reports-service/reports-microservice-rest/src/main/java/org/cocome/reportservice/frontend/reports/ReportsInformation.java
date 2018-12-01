package org.cocome.reportservice.frontend.reports;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.cocome.reportsservice.events.UserInformationProcessedEvent;

@Named
@SessionScoped
public class ReportsInformation implements IReportsInformation, Serializable{

	
	private static final long serialVersionUID = 3407158264646962362L;
	private static final Logger LOG = Logger.getLogger(ReportsInformation.class);
	private long activeStoreId = Long.MIN_VALUE;
	private long activeEnterpriseId = Long.MIN_VALUE;
	private boolean enterpriseSet = false;
	
	
	@Override
	public void setActiveStoreId(long storeId) {
		// executed when LoginEvent is processed
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
		this.activeEnterpriseId = enterpriseId;

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
	public boolean isEnterpriseSet() {
		return enterpriseSet;
	}

	@Override
	public void setEnterpriseSet(boolean enterpriseSet) {
		this.enterpriseSet = enterpriseSet;
	}

}
