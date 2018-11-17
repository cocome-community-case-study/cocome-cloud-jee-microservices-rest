package org.cocome.storesserviceservice.StoreQuery;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;
import org.cocome.storesservice.domain.StockItem;
import org.cocome.storesservice.domain.Store;
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
	private final long COULD_NOT_CREATE_ENTITY = -1;

	@EJB
	private StoreRepository storeRepo;

	@EJB
	private StockItemRepository stockRepo;



	@Override
	public long createStockItem(double salesPrice, long amount, long minStock, long maxStock, long barcode,
			long incomingAmount, long productId, long storeId) {
		LOG.debug("QUERY: Trying to create stock item with product id:" + productId + " in store with id: "
				+ storeId);
		
		Store store = storeRepo.find(storeId);
		if (store == null) {
			LOG.error("QUERY: Could not create stock item with product id:" + productId + " in store with id: "
					+ storeId + ". Store not found!");
			return COULD_NOT_CREATE_ENTITY;
		}

		// Create Items
		StockItem item = new StockItem();
		item.setAmount(amount);
		item.setBarcode(barcode);
		item.setIncomingAmount(incomingAmount);
		item.setMaxStock(maxStock);
		item.setMinStock(minStock);
		item.setProductId(productId);
		item.setSalesPrice(salesPrice);
		item.setStore(store);

		long stockItemId = stockRepo.create(item);
		if (stockItemId == COULD_NOT_CREATE_ENTITY) {
			LOG.error("QUERY: Could not create stock item with product id:" + item.getProductId() + " in store with id:"
					+ item.getStore().getId());
			return COULD_NOT_CREATE_ENTITY;
		}

		LOG.debug("QUERY: Successfully created StockItem with product id:" + item.getProductId() + " in store with id "
				+ item.getStore().getId());
		
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
	public StockItem getStockItemById(long stockItemId) {
		LOG.debug("QUERY: Get StockItem with id: " + stockItemId);
		StockItem item = stockRepo.find(stockItemId);
		if (item == null) {
			LOG.debug("QUERY: DId not fin StockItem with ID " + stockItemId);
			return null;
		}
		LOG.debug("QUERY: Successfully found StockItem with Id: " + stockItemId);
		return item;

	}

	@Override
	public Collection<StockItem> getStockItemsByStore(long storeId) {
		LOG.debug("Trying to find all Stock items of store with id: " + storeId);
		Store store = storeRepo.find(storeId);
		if (store == null) {
			LOG.debug("QUERY: Cannot retrieve Stock items of store with id: " + storeId + ". Store not Found!");
			return null;
		}
		Collection<StockItem> collection = new LinkedList<StockItem>();

		StringBuilder sb = new StringBuilder();
		sb.append("QUERY: Found following StockItem in Store with Id: " + storeId + " [product, Id]: ");

		for (StockItem item : store.getStockItems()) {
			sb.append("[ " + item.getProductId() + " ," + store.getId() + " ]");
		}
		LOG.debug(sb.toString());

		return collection;
	}

	

	@Override
	public boolean updateStockeItem(long id, double salesPrice, long amount, long minStock, long maxStock,
			long barcode, long incomingAmount) {
		LOG.debug("Trying to update StockItem wit id: " + id);
		StockItem item = stockRepo.find(id);
		if(item == null) {
			LOG.debug("QUERY: Could not update StockItem with id:" + id   +". Item not found! ");
			return false;
		}
		item.setSalesPrice(salesPrice);
		item.setAmount(amount);
		item.setMinStock(minStock);
		item.setMaxStock(maxStock);
		item.setBarcode(barcode);
		item.setIncomingAmount(incomingAmount);
		

		
		if (stockRepo.update(item) != null) {
			LOG.debug("QUERY: Successfully updated StockItem with id: " + item.getId());
			return true;
		}
		LOG.debug("QUERY: Could not update StockItem with id: " + item.getId());
		return false;
	}

	@Override
	public boolean deleteStockItem(long stockItemId) {
		LOG.debug("QUERY: Deleting StockItem from Database with id: " + stockItemId);
		if(stockRepo.delete(stockItemId)) {
			LOG.debug("QUERY: Successfully deleted StockItem with id: " + stockItemId);
			return true;
		}
		LOG.debug("QUERY: Could not delete StockItem with id: " + stockItemId);
		return false;
	}

}
