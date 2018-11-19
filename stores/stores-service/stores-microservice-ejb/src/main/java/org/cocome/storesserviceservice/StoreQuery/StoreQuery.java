package org.cocome.storesserviceservice.StoreQuery;

import java.io.Serializable;
import java.util.Collection;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.validation.constraints.NotNull;

import org.apache.log4j.Logger;
import org.cocome.storesservice.domain.Store;
import org.cocome.storesservice.domain.TradingEnterprise;
import org.cocome.storesservice.repository.StoreRepository;
import org.cocome.storesservice.repository.TradingEnterpriseRepository;

/**
 * This class abstracts the Database Access and provides more specific
 * CRUD-Operations. <br>
 * It uses the Entities specified in {@link org.cocome.storesservice.domain}<br>
 * 
 * It handles Queries from the enterprise-frontend.
 * 
 * @author Niko Benkler
 * @author Robert Heinrich
 *
 */

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

	/**
	 * Create Store with given Id, name, storeLocation and Enterprise <br>
	 * As the Database Entities TradinEnterprise and Store have an OneToMany
	 * Relationship, there is no need of updating the corresponding Enterprise
	 * (setting this store is done automatically)
	 */
	public long createStore(@NotNull String storeName, @NotNull String storeLocation, @NotNull Long enterpriseId) {

		/*
		 * find corresponding enterprise. If not enterprise found, store cannot be
		 * created as it belongs to exactly one enterprise
		 */
		TradingEnterprise enterprise = enterpriseRepo.find(enterpriseId);
		if (enterprise == null) {
			LOG.error("QUERY: Could not create Store with name:  " + storeName + ", location: " + storeLocation
					+ " in enterprise with Id:  " + enterpriseId + ".  Enterprise not found!");
			return COULD_NOT_CREATE_ENTITY;

		}
		LOG.debug("QUERY: Try to create store with " + storeName + ", location: " + storeLocation
				+ " in enterprise with Id:  " + enterpriseId);

		// create new Store with enterprise
		Store store = new Store();
		store.setLocation(storeLocation);
		store.setName(storeName);
		store.setEnterprise(enterprise);

		// persist store
		long storeId = storeRepo.create(store);
		if (storeId == COULD_NOT_CREATE_ENTITY) {
			LOG.error("QUERY: Error while creating store with " + storeName + ", location: " + storeLocation
					+ " in enterprise with Id:  " + enterpriseId);
			return COULD_NOT_CREATE_ENTITY;
		}
		LOG.debug("QUERY: Successfully created Store with name: " + storeName + ", storeId: " + storeId + ", location: " + storeLocation
				+ " in enterprise with Id:  " + enterpriseId);

		/*
		 * Updating enterprise automatically done by database
		 * @see AutomatiChangeTracking
		 */
		enterprise.addStore(store);
		// enterpriseRepo.update(enterprise);

		return storeId;

	}

	/**
	 * Retrieve all store in DB
	 */
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

	/**
	 * Find Store by id
	 * 
	 * @return null if not found
	 */
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

	/**
	 * Returns all Store that belong to the enterprise with the given Id
	 */
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

	@Override
	public boolean deleteStore(long storeId) {
		LOG.debug("QUERY: Deleting Store from Database with id: " + storeId);

		if (storeRepo.delete(storeId)) {
			LOG.debug("QUERY: Successfully deleted store with id: " + storeId);
			return true;

		}
		LOG.debug("QUERY: Could not delete Store with id: " + storeId);
		return false;
	}

	/**
	 * Update Store by giving new StoreName and StoreLocation
	 * 
	 * @return true/false if sucessful or not
	 */
	@Override
	public boolean updateStore(@NotNull long storeId, @NotNull String newName, @NotNull String newLocation) {
		LOG.debug("QUERY: Trying to update store with id " + storeId);
		Store store = storeRepo.find(storeId);

		if (store == null) {
			LOG.debug("QUERY: Could not update Store with id: " + storeId + ". Store not found!");
			return false;
		}
		
		store.setLocation(newLocation);
		store.setName(newName);

		;
		if (storeRepo.update(store) != null) {
			LOG.debug("QUERY: Sucessfully updated store with id: " + store.getId());
			return true;
		} else
			LOG.debug("QUERY: Could not update Store entity with id: " + store.getId());
		return false;

	}
}
