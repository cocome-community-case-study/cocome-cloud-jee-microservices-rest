package org.cocome.storesservice.frontend.enterprise;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.cocome.storesservice.frontend.viewdata.EnterpriseViewData;

@Named
@SessionScoped
public class EnterpriseInformation implements IEnterpriseInformation, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -708496773551832351L;

	private static final Logger LOG = Logger.getLogger(EnterpriseInformation.class);

	/*
	 * This field indicates the id of the active enterprise. As soon as this field
	 * changes its value, the class automatically updates the corresponding
	 * EnterpriseViewData
	 */
	private long activeEnterpriseId = Long.MIN_VALUE;

	/*
	 * This field indicates whether there is an active enterprise or not
	 */
	private EnterpriseViewData activeEnterprise;

	@Inject
	private EnterpriseManager enterpriseManager;

	@Override
	public long getActiveEnterpriseId() {

		return activeEnterpriseId;
	}

	@Override
	public void setActiveEnterpriseId(long enterpriseId) {
		LOG.debug("Active Enterprise set to: " + enterpriseId);
		activeEnterpriseId = enterpriseId;
		activeEnterprise = enterpriseManager.getEnterpriseById(activeEnterpriseId);
		

	}

	@Override
	public EnterpriseViewData getActiveEnterprise() {
		

		return activeEnterprise;
	}
    
	

	@Override
	public void setActiveEnterprise(EnterpriseViewData enterprise) {
		activeEnterpriseId = enterprise.getId();
		activeEnterprise = enterprise;
		LOG.debug("Active Enterprise set to: " + activeEnterpriseId);

	}

}
