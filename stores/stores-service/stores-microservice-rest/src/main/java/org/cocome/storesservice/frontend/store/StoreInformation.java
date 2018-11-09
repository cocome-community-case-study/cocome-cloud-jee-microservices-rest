package org.cocome.storesservice.frontend.store;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.cocome.storesservice.frontend.enterprise.EnterpriseInformation;
import org.cocome.storesservice.frontend.viewdata.StoreViewData;

@Named
@SessionScoped
public class StoreInformation implements IStoreInformation, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1957571338580056760L;
	private static final Logger LOG = Logger.getLogger(StoreInformation.class);
	
	/*
	 * This field indicates the id of the active store. As soon as this field
	 * changes its value, the class automatically updates the corresponding
	 * StoreViewData
	 */
	private long activeStoreId = Long.MIN_VALUE;
	
	/*
	 * This field indicates whether there is an active enterprise or not
	 */
	private StoreViewData activeStore;
	
	private boolean editingEnabled = false;
	
	public boolean isEditingEnabled() {
		return editingEnabled;
	}

	public void setEditingEnabled(boolean editingEnabled) {
		this.editingEnabled = editingEnabled;
	}

	@Inject
	StoreManager storeManager;
	
	

	@Override
	public long getActiveStoreId() {
		return activeStoreId;
	}

	@Override
	public void setActiveStoreId(long storeId) {
		LOG.debug("Active Store set to: " + storeId);
		activeStoreId = storeId;
		activeStore = storeManager.getStoreById(storeId);
		
		
	}

	@Override
	public StoreViewData getActiveStore() {
		return activeStore;
	}

	@Override
	public void setActiveStore(StoreViewData store) {
		activeStoreId = store.getId();
		activeStore = store;
		LOG.debug("Active store set to: " + activeStoreId);
		
	}
	
	@Override
	public boolean isStoreSet() {
		return activeStoreId != Long.MIN_VALUE;
	}

	@Override
	public void resetStore() {
		activeStoreId = Long.MIN_VALUE;
		LOG.debug("Active store resetted");
		
	}

	@Override
	public String switchToStore(StoreViewData store) {
		// TODO implement
		return null;
	}

	@Override
	public String switchToStock(StoreViewData store) {
		// TODO implement
		return null;
	}

}
