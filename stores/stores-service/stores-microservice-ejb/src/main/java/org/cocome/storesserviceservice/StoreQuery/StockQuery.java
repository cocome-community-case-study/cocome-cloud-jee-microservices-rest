package org.cocome.storesserviceservice.StoreQuery;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;
import org.cocome.storesservice.domain.StockItem;
import org.cocome.storesservice.domain.Store;
import org.cocome.storesservice.exceptions.CreateException;
import org.cocome.storesservice.exceptions.QueryException;
import org.cocome.storesservice.repository.StockItemRepository;
import org.cocome.storesservice.repository.StoreRepository;

@Local
@Stateless
public class StockQuery implements IStockQuery, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1263704023914164538L;
	private Logger LOG = Logger.getLogger(StockQuery.class);

	@EJB
	private StoreRepository storeRepo;

	@EJB
	private StockItemRepository stockRepo;

	@Override
	public long createStockItem(double salesPrice, long amount, long minStock, long maxStock, long barcode,
			long incomingAmount, long productId, long storeId, String name) throws CreateException {
		LOG.debug("QUERY: Trying to create stock item with product id:" + productId + " in store with id: " + storeId);

		// find corresponding Store
		Store store = storeRepo.find(storeId);
		if (store == null) {
			LOG.error("QUERY: Could not create stock item with product id:" + productId + " in store with id: "
					+ storeId + ". Store not found!");
			throw new CreateException("QUERY: Could not create stock item with product id:" + productId
					+ " in store with id: " + storeId + ". Store not found!");
		}

		// Create Item
		StockItem item = new StockItem();
		item.setAmount(amount);
		item.setBarcode(barcode);
		item.setIncomingAmount(incomingAmount);
		item.setMaxStock(maxStock);
		item.setMinStock(minStock);
		item.setProductId(productId);
		item.setSalesPrice(salesPrice);
		item.setStore(store);
		item.setName(name);

		Long stockItemId = stockRepo.create(item);
		if (stockItemId == null) {
			LOG.error("QUERY: Could not create stock item with product id:" + item.getProductId() + " in store with id:"
					+ item.getStore().getId());
			throw new CreateException("QUERY: Could not create stock item with product id:" + item.getProductId()
					+ " in store with id:" + item.getStore().getId());
		}

		LOG.debug("QUERY: Successfully created StockItem with product id:" + item.getProductId() + " in store with id "
				+ item.getStore().getId());

		/*
		 * Updating stock automatically done by database
		 * 
		 * @see AutomatiChangeTracking
		 */
		store.addStockItems(item);
		// storeRepo.update(store);

		return stockItemId;
	}

	@Override
	public Collection<StockItem> getAllStockItems() {

		Collection<StockItem> items = stockRepo.all();

		StringBuilder sb = new StringBuilder();
		sb.append("QUERY: Retrieving ALL StockItem from database with following [ProductId, Id]: ");

		for (StockItem item : items) {
			sb.append("[ " + item.getProductId() + " ," + item.getId() + " ]");
		}
		LOG.debug(sb.toString());
		return items;
	}

	@Override
	public StockItem getStockItemByIdAndStore(long stockItemId, long storeId) throws QueryException {
		LOG.debug("QUERY: Get StockItem with id: " + stockItemId + " from store with id: " + storeId);
		StockItem item = stockRepo.find(stockItemId);
		if (item == null) {
			LOG.debug("QUERY: Did not find StockItem with ID " + stockItemId);
			throw new QueryException("Did not find StockItem with id: " + stockItemId);

		}

		if (item.getStore().getId() != storeId) {
			LOG.debug("QUERY: StockItem found, but does not belong to store with id:  " + stockItemId);
			throw new QueryException("Did not find StockItem with id " + stockItemId);
		}
		return item;

	}

	@Override
	public StockItem getStockItemByBarcodeAndStore(long barcode, long storeId) throws QueryException {
		LOG.debug("QUERY: Get StockItem with barcode: " + barcode + " from store with id: " + storeId);

		StockItem item = null;

		for (StockItem it : this.getStockItemsByStore(storeId)) {
			if (it.getBarcode() == barcode) {
				item = it; // Barcode is unique!
				break;

			}
		}

		if (item == null) {
			LOG.debug("QUERY: Did not find StockItem with barcode " + barcode);
			throw new QueryException("Did not find StockItem with Barcode: " + barcode);

		}

		return item;

	}

	@Override
	public StockItem getStockItemById(long stockItemId) throws QueryException {
		LOG.debug("QUERY: Get StockItem with id: " + stockItemId);

		StockItem item = stockRepo.find(stockItemId);
		if (item == null) {
			LOG.debug("QUERY: Did not find StockItem with ID " + stockItemId);
			throw new QueryException("Did not find StockItem with Id: " + stockItemId);

		}
		LOG.debug("QUERY: Successfully found StockItem with Id: " + stockItemId);
		return item;

	}

	@Override
	public Collection<StockItem> getStockItemsByStore(long storeId) throws QueryException {
		LOG.debug("Trying to find all Stock items of store with id: " + storeId);

		// find corresponding Store
		Store store = storeRepo.find(storeId);
		if (store == null) {
			LOG.debug("QUERY: Cannot retrieve Stock items of store with id: " + storeId + ". Store not Found!");
			throw new QueryException("Cannot retrieve Stock items of store with id: " + storeId + ". Store not Found!");
		}

		// get Stock Items
		Collection<StockItem> stockItems = store.getStockItems();

		/*
		 * Make sure that we do not get a null pointer is item list was not initialized
		 */
		if (stockItems == null) {
			stockItems = new LinkedList<StockItem>();
		}

		// Logging
		StringBuilder sb = new StringBuilder();
		sb.append("QUERY: Found following StockItem in Store with Id: " + storeId + " [product, Id]: ");

		for (StockItem item : store.getStockItems()) {
			sb.append("[ " + item.getProductId() + " ," + store.getId() + " ]");
		}
		LOG.debug(sb.toString());

		return stockItems;
	}

	@Override
	public void updateStockeItem(long id, double salesPrice, long amount, long minStock, long maxStock, long barcode,
			long incomingAmount, String name) throws QueryException {
		LOG.debug("Trying to update StockItem wit id: " + id);

		// Find Item
		StockItem item = stockRepo.find(id);
		if (item == null) {
			LOG.debug("QUERY: Could not update StockItem with id:" + id + ". Item not found! ");
			throw new QueryException("Could not update StockItem with id:" + id + ". Item not found! ");
		}
		// Set new values
		item.setSalesPrice(salesPrice);
		item.setAmount(amount);
		item.setMinStock(minStock);
		item.setMaxStock(maxStock);
		item.setBarcode(barcode);
		item.setIncomingAmount(incomingAmount);
		item.setName(name);

		if (stockRepo.update(item) == null) {
			LOG.debug("QUERY: Could not update StockItem with id: " + item.getId());
			throw new QueryException("Could not update StockItem with id: " + item.getId());

		}

		LOG.debug("QUERY: Successfully updated StockItem with id: " + item.getId());

	}

	private void updateStockItem(StockItem item) throws QueryException {
		this.updateStockeItem(item.getId(), item.getSalesPrice(), item.getAmount(), item.getMinStock(),
				item.getMaxStock(), item.getBarcode(), item.getIncomingAmount(), item.getName());
	}

	@Override
	public void deleteStockItem(long stockItemId) throws QueryException {
		LOG.debug("QUERY: Deleting StockItem from Database with id: " + stockItemId);

		if (stockRepo.delete(stockItemId)) {
			LOG.debug("QUERY: Successfully deleted StockItem with id: " + stockItemId);
			return;
		}
		LOG.debug("QUERY: Could not delete StockItem with id: " + stockItemId);
		throw new QueryException("Could not delete StockItem with id: " + stockItemId);
	}

	/**
	 * Do Stock Exchange of certain item. <br>
	 * The procedure calls other methods to get any available stock within the same
	 * enterprise. It uses pseudo heuristics to determine how much items can be
	 * exchanged <br>
	 * 
	 */
	@Override
	public void stockExchange(long stockItemId) throws QueryException {
		LOG.debug("QUERY: Trying to exchange stock for stockItem with id: " + stockItemId);
		/**
		 * Notice: This is only a pseudo implementation. It was not necessary to do any
		 * useful and correct product exchange
		 */
		StockItem item = stockRepo.find(stockItemId);

		// do exchange only if necessary
		if (item.getAmount() < item.getMinStock()) {
			// Get all items with same productId and from same enterprise
			Collection<StockItem> items = getStockItemsForExchange(item);

			// Remove item that that's running out of stock to prevent self-donnation
			items.remove(item);
			applyExchange(items, item);
		}

	}

	/*
	 * This method does not really use any heuristics or checks any useful
	 * conditions. We only transfer some items to the requesting store by increasing
	 * the amount of the given stock item and decreasing the amount of the same
	 * product (different stock item) of antoher store
	 */
	private void applyExchange(Collection<StockItem> items, StockItem item) throws QueryException {
		LOG.debug("QUERY: Apply Heuristics!");
		long desirableAmount = item.getMinStock() * 3; // randon number
		long increasingAmount = 0;

		List<StockItem> shuffleList = new ArrayList<>(items);
		/*
		 * Pseudo heuristic: We want not always the same items, and therefore the same
		 * stores to provide items
		 */
		Collections.shuffle(shuffleList);

		// List that holds items with changed (reduced/increased) amount
		List<StockItem> newAmountList = new ArrayList<>();

		/*
		 * Iterate over List and decrease amount of other stock items as long as enough
		 * items found
		 */
		for (StockItem shuffleItem : shuffleList) {

			// Decrease Amount according to this policy
			long decreasingAmount = applyHeusistics(shuffleItem);
			long newAmount = shuffleItem.getAmount() - decreasingAmount;
			// Only set if enough stock
			if (newAmount > shuffleItem.getMinStock()) {
				shuffleItem.setAmount(newAmount);
				increasingAmount += decreasingAmount;
				newAmountList.add(shuffleItem);

			}
			if (increasingAmount > desirableAmount) {
				LOG.debug("QUERY: Enough Items found. Now, items are updated!");
				// enough items already found
				break;
			}
		}

		/*
		 * Only Update stock items if useful! Do not update, if all stores together
		 * cannot provide enough items
		 */
		if (increasingAmount < desirableAmount) {
			LOG.debug("QUERY: Not enough stock items available in all stores");
			return;

		}

		// Set new amount and update the changed items
		item.setAmount(item.getAmount() + increasingAmount);
		newAmountList.add(item);
		updateStockItemCollection(newAmountList);

	}

	/*
	 * This method can be changed
	 */
	private long applyHeusistics(StockItem item) {
		return Math.round(item.getAmount() / 2);
	}

	private void updateStockItemCollection(Collection<StockItem> items) throws QueryException {
		for (StockItem item : items) {
			this.updateStockItem(item);
		}
	}

	/*
	 * This Method provides all StockItems of each store in the
	 */
	private Collection<StockItem> getStockItemsForExchange(StockItem item) {

		Collection<StockItem> items = new ArrayList<>();
		// this is really ugly but works
		for (Store store : item.getStore().getEnterprise().getStores()) {
			// add all items of each store inb enterprise
			items.addAll(store.getStockItems());
		}

		// filter all items that are not the same
		items.stream().filter(it -> it.getProductId() == item.getProductId()).collect(Collectors.toList());

		return items;

	}
}
