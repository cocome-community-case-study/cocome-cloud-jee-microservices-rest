package org.cocome.reportservice.frontend.reports;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.cocome.reportsservice.navigation.INavigationMenu;

@Named
@ViewScoped
public class ReportsInformation implements IReportsInformation, Serializable{

	
	private static final long serialVersionUID = 3407158264646962362L;
	private static final Logger LOG = Logger.getLogger(ReportsInformation.class);

	private long activeEnterpriseId = Long.MIN_VALUE;
	private boolean enterpriseSet = false;
	
	@Inject
	INavigationMenu menu;
	

	@Override
	public long getActiveStoreId() {
		return menu.getActiveStoreId();
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
	
	
	

	@Override
	public boolean isEnterpriseSet() {
		return enterpriseSet;
	}

	@Override
	public void setEnterpriseSet(boolean enterpriseSet) {
		this.enterpriseSet = enterpriseSet;
	}

}
