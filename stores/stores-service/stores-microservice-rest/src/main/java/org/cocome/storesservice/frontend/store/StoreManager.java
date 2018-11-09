package org.cocome.storesservice.frontend.store;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.cocome.enterpriseservice.StoreQuery.IStoreQuery;
import org.cocome.storesservice.domain.Store;
import org.cocome.storesservice.domain.TradingEnterprise;
import org.cocome.storesservice.frontend.enterprise.EnterpriseInformation;
import org.cocome.storesservice.frontend.viewdata.EnterpriseViewData;
import org.cocome.storesservice.frontend.viewdata.StoreViewData;
import org.cocome.storesservice.navigation.NavigationElements;

@Named
@ApplicationScoped
public class StoreManager implements IStoreManager {

	/*
	 * We might want to do caching here!
	 */
	private Map<Long, StoreViewData> stores;

	@EJB
	IStoreQuery storeQuery;
	
	@Inject
	StoreInformation storeInfo;

	@Override
	public String createStore(String storeName, String location, long enterpriseId) {

		if (storeQuery.createStore(storeName, location, enterpriseId)) {
			// TODO update activeStoreData
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Successfully created the Store!", null));

			return NavigationElements.SHOW_ENTERPRISES.getNavigationOutcome();
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error creating the new Store!", null));

			return NavigationElements.ENTERPRISE_MAIN.getNavigationOutcome();
		}
	}

	@Override
	public Collection<StoreViewData> getStores() {
		this.stores = new HashMap<Long, StoreViewData>();

		for (Store store : storeQuery.getAllStores()) {
			stores.put(store.getId(), StoreViewData.fromStore(store));
		}
		return stores.values();
	}

	@Override
	public StoreViewData getStoreById(long storeId) {
		Store store = storeQuery.getStoreById(storeId);

		if (store == null) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Could not find Store with Id " + storeId, null));
			return null;
		} else {
			return StoreViewData.fromStore(store);
		}
	}

	@Override
	public Collection<StoreViewData> getStoresByEnterpriseId(long enterpriseId) {
		Collection<StoreViewData> storesAsViewData = new LinkedList<StoreViewData>();
		Collection<Store> stores = storeQuery.getStoresOfEnterprise(enterpriseId);
		if (stores == null) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Could not find Stores of enterprise with Id " + enterpriseId, null));
		}
		for (Store store : stores) {
			storesAsViewData.add(StoreViewData.fromStore(store));
		}
		return storesAsViewData;
	}

	@Override
	public String updateStore(long storeId, String newName, String newLocation) {
		//TODO update storelist in active Enterprise!!!
		
		if(storeQuery.updateStore(storeId, newName, newLocation)) {
			storeInfo.setEditingEnabled(false);
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Successfully updated the Store!", null));
		}else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Could not update Stores with Id " + storeId, null));
			
		}
		return null;
	}

}
