package org.cocome.storesservice.frontend.enterprise;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.cocome.storesservice.frontend.store.StoreInformation;
import org.cocome.storesservice.frontend.viewdata.EnterpriseViewData;
import org.cocome.storesservice.frontend.viewdata.StoreViewData;

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
	private long activeEnterpriseIdTest = Long.MIN_VALUE;

	/*
	 * This field indicates whether there is an active enterprise or not
	 */
	private EnterpriseViewData activeEnterprise;

	@Inject
	private EnterpriseManager enterpriseManager;

	@Inject
	private StoreInformation storeInformation;

	@Override
	public long getActiveEnterpriseId() {

		return activeEnterpriseIdTest;
	}

	@Override
	public void setActiveEnterpriseId(long enterpriseId) {

		switchEnterprise(enterpriseId);
		activeEnterpriseIdTest = enterpriseId;
		activeEnterprise = enterpriseManager.getEnterpriseById(activeEnterpriseIdTest);
		LOG.debug("Active Enterprise set to: " + enterpriseId);

	}

	@Override
	public EnterpriseViewData getActiveEnterprise() {

		return activeEnterprise;
	}

	@Override
	public boolean isEnterpriseSet() {
		return activeEnterpriseIdTest != Long.MIN_VALUE;
	}

	@Override
	public void setActiveEnterprise(EnterpriseViewData enterprise) {
		switchEnterprise(enterprise.getId());
		activeEnterpriseIdTest = enterprise.getId();
		activeEnterprise = enterprise;
		LOG.debug("Active Enterprise set to: " + activeEnterpriseIdTest);

	}

	@Override
	public Collection<StoreViewData> getStores() {
		if (activeEnterpriseIdTest == Long.MIN_VALUE) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Could not display Stores of enterprise with Id "
							+ activeEnterpriseIdTest + ". No Enterprise active!", null));
			return null;
		}
		return activeEnterprise.getStores();
	}

	private void switchEnterprise(long enterpriseId) {
		
		if(storeInformation.isStoreSet() && storeInformation.getActiveStore().getEnterpriseId() != enterpriseId) {
			storeInformation.resetStore();
		}
		
		
		
	}

}
