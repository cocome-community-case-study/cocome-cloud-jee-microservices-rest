package org.cocome.storesservice.frontend.store;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.cocome.storesservice.frontend.viewdata.StoreViewData;

/**
 * This class holds information about the currently active store <br>
 * The store is bound to an active enterprise. Without an active enterprise,
 * there cannot be an active Store
 * 
 * @author Niko Benkler
 * @author Robert Heinrich
 *
 */
@Named
@SessionScoped
public class StoreInformation implements IStoreInformation, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1957571338580056760L;
	private static final Logger LOG = Logger.getLogger(StoreInformation.class);
	@Inject
	StoreManager storeManager;

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

	/*
	 * Used for changeStoreName/Location
	 */
	private boolean editingEnabled = false;

	/**
	 * Used to display fields for editing store
	 * 
	 * @return
	 */
	public boolean isEditingEnabled() {
		return editingEnabled;
	}

	/**
	 * If set to true, editing field become visible
	 * 
	 * @param editingEnabled
	 */
	public void setEditingEnabled(boolean editingEnabled) {
		this.editingEnabled = editingEnabled;
	}

	@Override
	public long getActiveStoreId() {
		return activeStoreId;
	}

	/**
	 * Setting an activeStore by Id causes an store query to get the corresponding
	 * store from the backend
	 */
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

	/**
	 * Setting activeStore directly by passing its ViewData does not cause a backend Query
	 */
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

	/**
	 * Reset active store fields
	 */
	@Override
	public void resetStore() {
		activeStoreId = Long.MIN_VALUE;
		activeStore = null;
		editingEnabled = false;
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
