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
import org.cocome.storesservice.exceptions.CreateException;
import org.cocome.storesservice.exceptions.QueryException;
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

	@EJB
	private TradingEnterpriseRepository enterpriseRepo;

	@EJB
	private StoreRepository storeRepo;

	/**
	 * Create Store with given Id, name, storeLocation and Enterprise <br>
	 * As the Database Entities TradinEnterprise and Store have an OneToMany
	 * Relationship, there is no need of updating the corresponding Enterprise
	 * (setting this store is done automatically)
	 * 
	 * @throws CreateException
	 */
	public long createStore(@NotNull String storeName, @NotNull String storeLocation, @NotNull Long enterpriseId)
			throws CreateException {

		/*
		 * find corresponding enterprise. If not enterprise found, store cannot be
		 * created as it belongs to exactly one enterprise
		 */
		TradingEnterprise enterprise = enterpriseRepo.find(enterpriseId);
		if (enterprise == null) {
			LOG.error("QUERY: Could not create Store with name:  " + storeName + ", location: " + storeLocation
					+ " in enterprise with Id:  " + enterpriseId + ".  Enterprise not found!");
			throw new CreateException("Could not create Store with name:  " + storeName + ", location: " + storeLocation
					+ " in enterprise with Id:  " + enterpriseId + ".  Enterprise not found!");

		}
		LOG.debug("QUERY: Try to create store with " + storeName + ", location: " + storeLocation
				+ " in enterprise with Id:  " + enterpriseId);

		// create new Store with enterprise
		Store store = new Store();
		store.setLocation(storeLocation);
		store.setName(storeName);
		store.setEnterprise(enterprise);

		// persist store
		Long storeId = storeRepo.create(store);
		if (storeId == null) {
			LOG.error("QUERY: Error while creating store with " + storeName + ", location: " + storeLocation
					+ " in enterprise with Id:  " + enterpriseId);
			throw new CreateException("Error while creating store with " + storeName + ", location: " + storeLocation
					+ " in enterprise with Id:  " + enterpriseId);
			

		}
		LOG.debug("QUERY: Successfully created Store with name: " + storeName + ", storeId: " + storeId + ", location: "
				+ storeLocation + " in enterprise with Id:  " + enterpriseId);

		/*
		 * Updating enterprise automatically done by database
		 * 
		 * @see AutomatiChangeTracking
		 */
		enterprise.addStore(store);
		//enterpriseRepo.update(enterprise);

		return storeId.longValue();

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
	 * @throws QueryException
	 */
	@Override
	public Store getStoreById(long storeId) throws QueryException {
		LOG.debug("QUERY: Retrieving Store from Database with Id: " + storeId);

		Store store = storeRepo.find(storeId);
		if (store == null) {
			LOG.debug("QUERY: Did not find store with Id: " + storeId);
			throw new QueryException("Did not find store with Id: " + storeId);
		}

		LOG.debug("QUERY: Successfully found store with Id: " + storeId);
		return store;
	}

	/**
	 * Returns all Store that belong to the enterprise with the given Id
	 * 
	 * @throws QueryException
	 */
	@Override
	public Collection<Store> getStoresOfEnterprise(long enterpriseId) throws QueryException {
		LOG.debug("QUERY: Retrieving ALL Stores from Database with Id: " + enterpriseId);

		// find corresponding enterprise
		TradingEnterprise enterprise = enterpriseRepo.find(enterpriseId);

		if (enterprise == null) {
			LOG.error("QUERY: Did not find enterprise with Id: " + enterpriseId);
			throw new QueryException(
					"Could not retrieve Stores from Enterprise with id: " + enterpriseId + ". Enterprise not found!");

		}

		// get Stores
		Collection<Store> stores = enterprise.getStores();

		// Logging
		StringBuilder sb = new StringBuilder();
		sb.append("QUERY: Found following store in Enterprise with Id: " + enterpriseId + " [name, Id]: ");

		for (Store store : stores) {
			sb.append("[ " + store.getName() + " ," + store.getId() + " ]");
		}
		LOG.debug(sb.toString());
		return stores;

	}

	@Override
	public void deleteStore(long storeId) throws QueryException {
		LOG.debug("QUERY: Deleting Store from Database with id: " + storeId);

		if (storeRepo.delete(storeId)) {
			LOG.debug("QUERY: Successfully deleted store with id: " + storeId);
			return;
		}

		LOG.debug("QUERY: Could not delete Store with id: " + storeId);
		throw new QueryException("Could not delete Store with id: " + storeId);

	}

	/**
	 * Update Store by giving new StoreName and StoreLocation
	 * 
	 * @return true/false if sucessful or not
	 * @throws QueryException
	 */
	@Override
	public void updateStore(@NotNull long storeId, @NotNull String newName, @NotNull String newLocation)
			throws QueryException {

		LOG.debug("QUERY: Trying to update store with id " + storeId);
		Store store = storeRepo.find(storeId);

		if (store == null) {
			LOG.debug("QUERY: Could not update Store with id: " + storeId + ". Store not found!");
			throw new QueryException(" Could not update Store with id: " + storeId + ". Store not found!");
		}

		store.setLocation(newLocation);
		store.setName(newName);

		if (storeRepo.update(store) == null) {
			LOG.debug("QUERY: Could not update Store entity with id: " + store.getId());
			throw new QueryException("QUERY: Could not update Store entity with id: " + store.getId());
		}
		LOG.debug("QUERY: Sucessfully updated store with id: " + store.getId());

	}
}
