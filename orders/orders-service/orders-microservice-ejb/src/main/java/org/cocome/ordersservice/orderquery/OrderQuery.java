package org.cocome.ordersservice.orderquery;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.CreateException;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.validation.constraints.NotNull;

import org.apache.log4j.Logger;
import org.cocome.ordersservice.domain.OrderEntry;
import org.cocome.ordersservice.domain.ProductOrder;
import org.cocome.ordersservice.exceptions.QueryException;
import org.cocome.ordersservice.repository.OrderEntryRepository;
import org.cocome.ordersservice.repository.ProductOrderRepository;
import org.cocome.productsclient.client.ProductClient;
import org.cocome.productsclient.domain.ProductTO;
import org.cocome.productsclient.exception.ProductsRestException;
import org.cocome.storesclient.client.StockItemClient;
import org.cocome.storesclient.domain.StockItemTO;
import org.cocome.storesclient.exception.StoreRestException;

/**
 * Query that provides high level methods for database queries concerning Orders
 * 
 * @author Niko Benkler
 * @author Robert Heinrich
 *
 */
@Local
@Stateless
public class OrderQuery implements IOrderQuery, Serializable {

	@EJB
	OrderEntryRepository entryRepo;

	@EJB
	ProductOrderRepository orderRepo;
	/**
	 * 
	 */
	private static final long serialVersionUID = -3723414108809787295L;
	private final Logger LOG = Logger.getLogger(OrderQuery.class);
	private final long COULD_NOT_CREATE_ENTITY = -1;

	// Initialize SearchValues and Clients for Rest Query
	private final StockItemClient stockClient = new StockItemClient();
	private final ProductClient productclient = new ProductClient();

	/**
	 * Simple CRUD-Functionality to Find entity
	 */
	@Override
	public ProductOrder findOrderById(long id) throws QueryException {
		LOG.debug("QUERY: Retrieving Order from Database with id: " + id);

		// find order
		ProductOrder order = orderRepo.find(id);

		// error handling
		if (order != null) {
			LOG.debug("QUERY: Successfully found order with id: " + id);
			return order;
		}
		LOG.debug("QUERY: Did not find order with id: " + id);
		throw new QueryException("Did not find order with id: " + id);
	}

	/**
	 * Simple CRUD-Functionality to get all Orders from repository
	 * @throws QueryException 
	 */
	@Override
	public Collection<ProductOrder> getAllOrders() throws QueryException {

		// find all orders
		Collection<ProductOrder> orders = orderRepo.all();
		
		if(orders == null) {
			LOG.error("QUERY: Could retreive all orders");
			throw new QueryException("Could retreive all orders");
		}

		// Logging
		StringBuilder sb = new StringBuilder();
		sb.append("QUERY: Retrieving ALL Orders from database with following [id, StoreId]: ");

		for (ProductOrder order : orders) {
			sb.append("[ " + order.getId() + " ," + order.getStoreId() + " ]");
		}
		LOG.debug(sb.toString());
		return orders;
	}

	/**
	 * Simple CRUD-Functionality to update order, Only Delivery and ordering Date
	 * can be updated. Entries are updated with Entry Query
	 */
	@Override
	public void updateOrder(long id, Date deliveryDate, Date orderingDate) throws QueryException {
		LOG.debug("QUERY: Trying to update Ordert with id: " + id);

		// find order
		ProductOrder order = orderRepo.find(id);
		if (order == null) {
			LOG.debug("QUERY: Could not update Order with id: " + id + ". Order not found");
			throw new QueryException("Could not update Order with id: " + id + ". Order not found");
		}
		// update order
		if (orderRepo.update(order) == null) {
			LOG.error("QUERY: Could not update Order with Id: " + order.getId());
			throw new QueryException("Could not update Order with id: " + id);
		}

		LOG.debug("QUERY: Successfully updated Order with  Id: " + order.getId());

	}

	/**
	 * Simple CRUD-Functionality to delete Entity
	 */
	@Override
	public void deleteOrder(long id) throws QueryException {
		LOG.debug("QUERY: Deleting Order from Database with Id: " + id);

		// delete entity
		if (orderRepo.delete(id)) {
			LOG.debug("QUERY: Successfully deleted Order with Id: " + id);
			return;
		}

		LOG.debug("QUERY: Did not find Order with Id: " + id);
		throw new QueryException("Did not find Order with Id: " + id);

	}

	/**
	 * This Method retrieves all orders for a given Store.
	 */
	@Override
	public Collection<ProductOrder> getOrdersByStoreId(long storeId) throws QueryException {
		// TODO: Further improvement --> Do SQL statement in Repo
		LOG.debug("QUERY: Retrieving Orders from Database with storeId: " + storeId);

		// find all
		Collection<ProductOrder> orders = orderRepo.all();

		if (orders == null) { // Database error, Prevent NullPointer Access when doing removeIf
			throw new QueryException("An error occured while retrieving Orders from Repository");
		}

		// Remove all orders where store id is not given store id
		orders.removeIf(order -> order.getStoreId() != storeId);

		// error handling
		if (orders.isEmpty()) {
			LOG.debug("QUERY: No orders availabel for Store with id: " + storeId);
		}

		return orders;
	}

	/**
	 * Create order with given parameter
	 */
	@Override
	public long createOrder(@NotNull Date deliveryDate, @NotNull Date orderingDate, @NotNull long storeId)
			throws CreateException {
		LOG.debug("QUERY: Trying to create Order for store with storeId: " + storeId);

		// Create order and set attributes
		ProductOrder order = new ProductOrder();
		order.setDeliveryDate(deliveryDate);
		order.setOrderingDate(orderingDate);
		order.setStoreId(storeId);

		// Persist Entity
		long orderId = orderRepo.create(order);

		// Error Handling
		if (orderId == COULD_NOT_CREATE_ENTITY) {
			LOG.error("QUERY: Could not create Order for store with storeId: " + storeId);
			throw new CreateException("Could not create Order for store with store id: " + storeId);

		}
		LOG.debug("QUERY: Successfully created Order for Store with storeId: " + storeId + " Having ID " + orderId);

		return orderId;
	}

	/**
	 * This Method updates the stock items of the store saved in the given order.
	 * This means REST-Calls are necessary. <br>
	 * We are not interested in Roll-Back Mechanisms... therefore Error Handling is
	 * very simple
	 */
	@Override
	public void rollInOrder(long orderId) throws QueryException, StoreRestException, ProductsRestException {
		LOG.debug("QUERY: Trying to roll in order with orderId: " + orderId);

		// find corresponding order
		ProductOrder order = orderRepo.find(orderId);
		if (order == null) {
			LOG.debug("QUERY: Could not roll in Order with orderID: " + orderId + ". Order not found!");
			throw new QueryException("Could not roll in Order with orderID: " + orderId + ". Order not found!");
		}

		long storeId = order.getStoreId();

		/*
		 * Find all stockItems that are already in stock. We want to add the ordered
		 * amount to the available amount <br> Convert to map as we need a reliable
		 * search
		 */
		Map<Long, StockItemTO> availableStock = stockClient.findByStore(storeId).stream()
				.collect(Collectors.toMap(StockItemTO::getProductId, item -> item));

		/*
		 * We iterate over the Order entries. In case one of the ordered products is
		 * already in stock, we simply update the corresponding item. If it is not in
		 * stock we need to create one
		 */
		for (OrderEntry entry : order.getOrderEntries()) {
			StockItemTO stockItem = availableStock.get(entry.getProductId());

			if (stockItem == null) {
				createNewStockItem(entry, storeId);
			} else {
				updateStockItem(stockItem, entry);
			}

		}

		// delete Order after successful rolled in
		orderRepo.delete(orderId);

	}
	
	/*
	 * Helper Function to update existing stock item in store
	 */
	private void updateStockItem(StockItemTO stockItem, OrderEntry entry) throws StoreRestException {
		long newAmount = stockItem.getAmount() + entry.getAmount();
		stockItem.setAmount(newAmount);
		stockClient.update(stockItem); //REST Call

	}
	
	/*
	 * Helper Function to Create new Item
	 */
	private void createNewStockItem(OrderEntry entry, long storeID) throws StoreRestException, ProductsRestException {

		// Get ProductInformation to create StockItem
		ProductTO productInformation = getProductById(entry.getProductId()); //REST-Call

		StockItemTO newStockItem = new StockItemTO();

		// set product infos
		newStockItem.setBarcode(productInformation.getBarcode());
		newStockItem.setProductId(productInformation.getId());
		newStockItem.setSalesPrice(productInformation.getPurchasePrice());
		newStockItem.setName(productInformation.getName());

		// set default infos
		newStockItem.setMinStock(0);
		newStockItem.setMaxStock(999); // can be adjusted afterwards in store service
		newStockItem.setIncomingAmount(0);

		// set the ordered amount
		newStockItem.setAmount(entry.getAmount());

		stockClient.create(newStockItem, storeID); //REST-Call

	}

	/*
	 * REST-Call to productsservice to get productInformation
	 */
	private ProductTO getProductById(long productId) throws org.cocome.productsclient.exception.ProductsRestException {

		return productclient.find(productId);
	}

}
