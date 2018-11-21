package org.cocome.storesservice.frontend.store;

import java.io.Serializable;
import java.util.Collection;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.cocome.storesservice.exceptions.QueryException;
import org.cocome.storesservice.frontend.viewdata.StockItemViewData;
import org.cocome.storesservice.frontend.viewdata.StoreViewData;
import org.cocome.storesservice.navigation.NavigationElements;

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

	@Override
	public long getActiveStoreId() {
		return activeStoreId;
	}

	/**
	 * Setting an activeStore by Id causes an store query to get the corresponding
	 * store from the backend
	 * 
	 * @throws QueryException
	 */
	@Override
	public void setActiveStoreId(long storeId) throws QueryException {
		LOG.debug("Active Store set to: " + storeId);
		activeStore = storeManager.getStoreById(storeId);
		activeStoreId = storeId;

	}

	@Override
	public StoreViewData getActiveStore() {
		return activeStore;
	}

	public Collection<StockItemViewData> getStockItems() {

		return null;
	}

	/**
	 * Setting activeStore directly by passing its ViewData does not cause a backend
	 * Query
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
		LOG.debug("Active store resetted");

	}

	@Override
	public String switchToStore(long storeId) {
		try {
			setActiveStoreId(storeId);
			return NavigationElements.STORE_MAIN.getNavigationOutcome(); // TODO straight to store?
		} catch (QueryException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Could not find Store with Id " + storeId, null));
			return NavigationElements.STORE_MAIN.getNavigationOutcome();
		}

	}

	@Override
	public String switchToStock(long storeId) {
		try {
			setActiveStoreId(storeId);
			return NavigationElements.SHOW_STOCK.getNavigationOutcome();
		} catch (QueryException e) {

			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Could not find Store with Id " + storeId, null));
			return NavigationElements.STORE_MAIN.getNavigationOutcome();

		}

	}

}
