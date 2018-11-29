package org.cocome.storesservice.frontend.store;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.cocome.storesservice.events.ChangeViewEvent;
import org.cocome.storesservice.events.UserInformationProcessedEvent;
import org.cocome.storesservice.exceptions.QueryException;
import org.cocome.storesservice.frontend.stock.StockManager;
import org.cocome.storesservice.frontend.viewdata.StockItemViewData;
import org.cocome.storesservice.frontend.viewdata.StoreViewData;
import org.cocome.storesservice.navigation.NavigationElements;
import org.cocome.storesservice.navigation.NavigationView;

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

	@Inject
	StockManager stockManager;

	@Inject
	Event<ChangeViewEvent> changeViewevent;

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

	private List<StockItemViewData> items;

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

	/**
	 * Fetch items from backend
	 */
	@Override
	public void loadItems() {

		try {

			items = new ArrayList<StockItemViewData>(stockManager.getStockItemsByStore(activeStoreId));
		} catch (QueryException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
		}

	}

	@Override
	public Collection<StockItemViewData> getStockItems() {
		if (items == null) {
			loadItems();
		}
		return items;
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
		items = null;
		LOG.debug("Active store resetted");

	}

	/**
	 * Switch current View  to store view and activeStore
	 */
	@Override
	public String switchToStore(long storeId) {
		try {
			setActiveStoreId(storeId);
			changeViewevent.fire(new ChangeViewEvent(NavigationView.STORE_VIEW));
			return NavigationElements.WELCOME_STORE.getNavigationOutcome(); 
		} catch (QueryException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Could not find Store with Id " + storeId, null));
			return NavigationElements.STORE_MAIN.getNavigationOutcome();
		}

	}

	/**
	 * Switch current View to store view (stock overview) and active store
	 */
	@Override
	public String switchToStock(long storeId) {
		try {
			setActiveStoreId(storeId);
			changeViewevent.fire(new ChangeViewEvent(NavigationView.STORE_VIEW));

			return NavigationElements.SHOW_STOCK.getNavigationOutcome();
		} catch (QueryException e) {

			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Could not find Store with Id " + storeId, null));
			return NavigationElements.STORE_MAIN.getNavigationOutcome();

		}

	}

	/**
	 * Update given Stock Item
	 */
	@Override
	public void updateStockItem(StockItemViewData updatedItem) {

		if (!validateStockItem(updatedItem)) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Max. Stock Amount has to be grater than Min. Stock Amount", null));
			return;

		}

		// update the fields like minStock with the new values
		updatedItem.submitEdit();
		// TODO if update fails, this has to be undone!

		try {
			stockManager.updateStockItem(updatedItem);
		} catch (QueryException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
		}
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Successfully updated StockItem", null));
		updateStockItemList(updatedItem);

	}

	/**
	 * Process Login Information to set active store
	 * 
	 * @param event
	 */
	public void observe(@Observes UserInformationProcessedEvent event) {
		//Only set store if not enterprise view required
		if(event.getRequestedNavViewState() == NavigationView.ENTERPRISE_VIEW) {
			return;
		}
		
		try {
			setActiveStoreId(event.getStoreID());
		} catch (QueryException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Login for Store with id: " + event.getStoreID() + " not possible. Store does not exist", null));
		}
	}

	/*
	 * Update Item List after a item - update. <br> This is cheaper than requesting
	 * all items one more time from backend. <br> We made it a little bit more
	 * complicated, than just removing the old and adding the new item, as we want
	 * to preserve its index within the list
	 */
	private void updateStockItemList(StockItemViewData item) {
		int index = -1;

		for (StockItemViewData itemInCollection : items) {
			if (itemInCollection.getId() == item.getId()) {
				index = items.indexOf(itemInCollection);
				break;
			}
		}
		if (index != -1) {
			items.set(index, item);
		}

	}

	private boolean validateStockItem(StockItemViewData item) {
		return item.getNewMinAmount() < item.getNewMaxAmount();
	}

}
