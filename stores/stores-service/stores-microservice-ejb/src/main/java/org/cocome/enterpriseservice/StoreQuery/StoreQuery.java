package org.cocome.enterpriseservice.StoreQuery;

import java.io.Serializable;
import java.util.Collection;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.validation.constraints.NotNull;

import org.apache.log4j.Logger;
import org.cocome.enterpriseservice.enterpriseQuery.EnterpriseQuery;
import org.cocome.storesservice.domain.Store;
import org.cocome.storesservice.domain.TradingEnterprise;
import org.cocome.storesservice.repository.StoreRepository;
import org.cocome.storesservice.repository.TradingEnterpriseRepository;

@Local
@Stateless
public class StoreQuery implements IStoreQuery, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3010252231475129274L;
	private Logger LOG = Logger.getLogger(StoreQuery.class);
	private final long COULD_NOT_CREATE_ENTITY = -1;
	@EJB
	private TradingEnterpriseRepository enterpriseRepo;

	@EJB
	private StoreRepository storeRepo;

	public boolean createStore(@NotNull String storeName, @NotNull String storeLocation, @NotNull Long enterpriseId) {

		TradingEnterprise enterprise = enterpriseRepo.find(enterpriseId);
		if (enterprise == null) {
			LOG.error("QUERY: Could not create Store with name:  " + storeName + ", location: " + storeLocation
					+ " in enterprise with Id:  " + enterpriseId + ".  Enterprise not found!");
			return false;

		}
		LOG.debug("QUERY: Try to create store with " + storeName + ", location: " + storeLocation
				+ " in enterprise with Id:  " + enterpriseId);
		Store store = new Store();
		store.setLocation(storeLocation);
		store.setName(storeName);
		store.setEnterprise(enterprise);
		if (storeRepo.create(store) == COULD_NOT_CREATE_ENTITY) {
			LOG.error("QUERY: Error while creating store with " + storeName + ", location: " + storeLocation
					+ " in enterprise with Id:  " + enterpriseId);
			return false;
		} else {
			LOG.debug("QUERY: Successfully created Store with " + storeName + ", location: " + storeLocation
					+ " in enterprise with Id:  " + enterpriseId);
			return true;
		}

	}

	@Override
	public Collection<Store> getAllStores() {
		Collection<Store> stores = storeRepo.all();

		StringBuilder sb = new StringBuilder();
		sb.append("QUERY: Retrieving ALL Stores from database with following [name, Id]: ");

		for (Store store : stores) {
			sb.append("[ " + store.getName() + " ," + store.getId() + " ]");
		}
		LOG.debug(sb.toString());
		return stores;
	}

	@Override
	public Store getStoreById(long storeId) {
		LOG.debug("QUERY: Retrieving Store from Database with Id: " + storeId);
		Store store = storeRepo.find(storeId);
		if (store != null) {
			LOG.debug("QUERY: Successfully found enterprise with Id: " + storeId);
			return store;
		}
		LOG.debug("QUERY: Did not find enterprise with Id: " + storeId);
		return null;
	}

	@Override
	public Collection<Store> getStoresOfEnterprise(long enterpriseId) {
		LOG.debug("QUERY: Retrieving ALL Stores from Database with Id: " + enterpriseId);
		TradingEnterprise enterprise = enterpriseRepo.find(enterpriseId);

		if (enterprise == null) {
			LOG.error("QUERY: Did not find enterprise with Id: " + enterpriseId);
			return null;
		}

		Collection<Store> stores = enterprise.getStores();

		StringBuilder sb = new StringBuilder();
		sb.append("QUERY: Found following store in Enterprise with Id: " + enterpriseId + " [name, Id]: ");

		for (Store store : stores) {
			sb.append("[ " + store.getName() + " ," + store.getId() + " ]");
		}
		LOG.debug(sb.toString());
		return stores;

	}

}
