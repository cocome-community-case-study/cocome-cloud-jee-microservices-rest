package org.cocome.storesservice.frontend.enterprise;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.cocome.storesservice.exceptions.QueryException;
import org.cocome.storesservice.frontend.store.StoreInformation;
import org.cocome.storesservice.frontend.viewdata.EnterpriseViewData;
import org.cocome.storesservice.frontend.viewdata.StoreViewData;

/**
 * This class holds Information about the currently active Enterprise
 * 
 * @author Niko Benkler
 * @author Robert Heinrich
 *
 */
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

	@Inject
	private StoreInformation storeInformation;

	@Override
	public long getActiveEnterpriseId() {

		return activeEnterpriseId;
	}

	/**
	 * Set new active enterpriseId <br>
	 * This will cause to update EnterpriseViewData --> Retrieve enterprise with
	 * given Id from Database
	 */
	@Override
	public void setActiveEnterpriseId(long enterpriseId) {
		// reset StoreInformation
		resetStoreInfo(enterpriseId);

		try {
			activeEnterprise = enterpriseManager.getEnterpriseById(enterpriseId);
			activeEnterpriseId = enterpriseId;
		} catch (QueryException e) {
			activeEnterpriseId = Long.MIN_VALUE;
			LOG.error("Could not set Enterprise");
		}

		LOG.debug("Active Enterprise set to: " + enterpriseId);

	}

	@Override
	public EnterpriseViewData getActiveEnterprise() {

		return activeEnterprise;
	}

	/**
	 * Returns true, if there is currently an active enterprise set
	 */
	@Override
	public boolean isEnterpriseSet() {
		return activeEnterpriseId != Long.MIN_VALUE;
	}

	/**
	 * Set Active Enterprise by directly setting EnterpriseViewData <br>
	 * This also updates the current activeEnterpriseId. <br>
	 * Also, it resets the current active Store if there was one set
	 */
	@Override
	public void setActiveEnterprise(EnterpriseViewData enterprise) {
		resetStoreInfo(enterprise.getId());
		activeEnterpriseId = enterprise.getId();
		activeEnterprise = enterprise;
		LOG.debug("Active Enterprise set to: " + activeEnterpriseId);

	}

	/**
	 * Returns all stores that belong to the currently active Enterprise
	 */
	@Override
	public Collection<StoreViewData> getStores() {
		if (activeEnterpriseId == Long.MIN_VALUE) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Could not display Stores of enterprise with Id " + activeEnterpriseId + ". No Enterprise active!",
					null));
			return null;
		}
		return activeEnterprise.getStores();
	}

	/*
	 * This is needed to reset current active store to null as a change of
	 * Enterprise need to reset the active store
	 */
	private void resetStoreInfo(long enterpriseId) {

		if (storeInformation.isStoreSet()) {
			storeInformation.resetStore();
		}

	}

	/**
	 * We need to reset Active StoreInformation when the current enterprise changes,
	 * as the active store no longer belongs to ne (new) active Enterprise
	 */
	@Override
	public void refreshEnterpriseInformation() {
		try {
			activeEnterprise = enterpriseManager.getEnterpriseById(activeEnterpriseId);
		} catch (QueryException e) {
			resetStoreInfo(activeEnterpriseId);
			activeEnterpriseId = Long.MIN_VALUE;
		}

	}

}
