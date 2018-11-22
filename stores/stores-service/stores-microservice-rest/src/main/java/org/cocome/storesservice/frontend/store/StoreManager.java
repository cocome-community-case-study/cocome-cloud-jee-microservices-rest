package org.cocome.storesservice.frontend.store;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.cocome.storesservice.domain.Store;
import org.cocome.storesservice.exceptions.CreateException;
import org.cocome.storesservice.exceptions.QueryException;
import org.cocome.storesservice.frontend.enterprise.EnterpriseInformation;
import org.cocome.storesservice.frontend.viewdata.StoreViewData;
import org.cocome.storesservice.navigation.NavigationElements;
import org.cocome.storesserviceservice.StoreQuery.IStoreQuery;

/**
 * This class is an Interface between frontend and backend. It accesses the
 * backend on a frontend query and redirects to new Views if needed
 * 
 * @author Niko Benkler
 * @author Robert Heinrich
 *
 */
@Named
@SessionScoped
public class StoreManager implements IStoreManager, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7574478612005212704L;



	/*
	 * We might want to do caching here!
	 */
	private Map<Long, StoreViewData> stores;

	

	@EJB
	IStoreQuery storeQuery;

	@Inject
	StoreInformation storeInfo;

	@Inject
	EnterpriseInformation enterpriseInfo;

	/**
	 * Create store by given name, location and enterpriseId <br>
	 * Cannot create store without corresponding enterpriseID <br>
	 * Redirects to ShowStore View
	 */
	@Override
	public String createStore(String storeName, String location, long enterpriseId) {

		try {
			storeQuery.createStore(storeName, location, enterpriseId);
		} catch (CreateException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error creating the new Store!", null));

			return NavigationElements.ENTERPRISE_MAIN.getNavigationOutcome();
		}

		enterpriseInfo.setActiveEnterpriseId(enterpriseId);
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Successfully created the Store!", null));

		return NavigationElements.SHOW_STORES.getNavigationOutcome();

	}

	/**
	 * Get all Stores
	 */
	@Override
	public Collection<StoreViewData> getStores() {

		return StoreViewData.fromStoreCollection(storeQuery.getAllStores());

	}

	/**
	 * Get store by given Id
	 * 
	 * @throws QueryException
	 */
	@Override
	public StoreViewData getStoreById(long storeId) throws QueryException {

		Store store = storeQuery.getStoreById(storeId);

		return StoreViewData.fromStore(store);

	}

	@Override
	public Collection<StoreViewData> getStoresByEnterpriseId(long enterpriseId) {

		Collection<Store> stores;
		try {
			stores = storeQuery.getStoresOfEnterprise(enterpriseId);
		} catch (QueryException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Could not find Stores of enterprise with Id " + enterpriseId, null));
			// return empty (but initialized!) List
			return new LinkedList<StoreViewData>();
		}

		return StoreViewData.fromStoreCollection(stores);

	}

	/**
	 * Update Store by setting new name and location
	 */
	@Override
	public String updateStore(long storeId, String newName, String newLocation) {
		

		try {
			storeQuery.updateStore(storeId, newName, newLocation);
		} catch (QueryException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Could not update Stores with Id " + storeId, null));
			return NavigationElements.SHOW_STORES.getNavigationOutcome();
		}

		//Reload Enterprise
		enterpriseInfo.refreshEnterpriseInformation();
		
		//Logging
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Successfully updated the Store!", null));
		return NavigationElements.SHOW_STORES.getNavigationOutcome();

	}

}
