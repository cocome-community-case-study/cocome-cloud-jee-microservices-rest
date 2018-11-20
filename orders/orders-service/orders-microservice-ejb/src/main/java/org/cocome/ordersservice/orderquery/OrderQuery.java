package org.cocome.ordersservice.orderquery;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;
import org.cocome.ordersservice.domain.ProductOrder;
import org.cocome.ordersservice.repository.OrderEntryRepository;
import org.cocome.ordersservice.repository.ProductOrderRepository;

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
	private Logger LOG = Logger.getLogger(OrderQuery.class);
	private final long COULD_NOT_CREATE_ENTITY = -1;

	@Override
	public ProductOrder findOrderById(long id) {
		LOG.debug("QUERY: Retrieving Order from Database with id: " + id);
		ProductOrder order = orderRepo.find(id);

		if (order != null) {
			LOG.debug("QUERY: Successfully found order with id: " + id);
			return order;
		}
		LOG.debug("QUERY: Did not find order with id: " + id);
		return null;
	}

	@Override
	public Collection<ProductOrder> getAllOrders() {
		Collection<ProductOrder> orders = orderRepo.all();

		StringBuilder sb = new StringBuilder();
		sb.append("QUERY: Retrieving ALL Orders from database with following [id, StoreId]: ");

		for (ProductOrder order : orders) {
			sb.append("[ " + order.getId() + " ," + order.getStoreId() + " ]");
		}
		LOG.debug(sb.toString());
		return orders;
	}

	@Override
	public boolean updateOrder(long id, Date deliveryDate, Date orderingDate) {
		LOG.debug("QUERY: Trying to update Ordert with id: " + id);

		ProductOrder order = orderRepo.find(id);
		if (order == null) {
			LOG.debug("QUERY: Could not update Order with id: " + id + ". Order not found");
			return false;
		}

		if (orderRepo.update(order) == null) {
			LOG.error("QUERY: Could not update Order with Id: " + order.getId());
			return false;
		}
		LOG.debug("QUERY: Successfully updated Order with  Id: " + order.getId());
		return true;
	}

	@Override
	public boolean deleteOrder(long id) {
		LOG.debug("QUERY: Deleting Order from Database with Id: " + id);

		if (orderRepo.delete(id)) {
			LOG.debug("QUERY: Successfully deleted Order with Id: " + id);
			return true;
		}
		LOG.debug("QUERY: Did not find Order with Id: " + id);
		return false;
	}

	@Override
	public Collection<ProductOrder> getOrdersByStoreId(long storeId) {
		// TODO: Further improvement --> Do SQL statement in Repo
		LOG.debug("QUERY: Retrieving Orders from Database with storeId: " + storeId);
		Collection<ProductOrder> orders = orderRepo.all();

		if (orders == null) { // Database error, Prevent NullPointer Access when doing removeIf
          return null;
		}
		orders.removeIf(order -> order.getStoreId() != storeId);

		if (orders.isEmpty()) {
			LOG.debug("QUERY: No orders availabel for Store with id: " + storeId);
		}

		return orders;
	}

	@Override
	public long createOrder(Date deliveryDate, Date orderingDate, long storeId) {
		LOG.debug("QUERY: Trying to create Order for store with storeId: " + storeId);

		ProductOrder order = new ProductOrder();
		order.setDeliveryDate(deliveryDate);
		order.setOrderingDate(orderingDate);
		order.setStoreId(storeId);
		
		

		long orderId = orderRepo.create(order);
		if (orderId == COULD_NOT_CREATE_ENTITY) {
			LOG.error("QUERY: Could not create Order for store with storeId: " + storeId);
			return COULD_NOT_CREATE_ENTITY;
		}
		LOG.debug("QUERY: Successfully created Order for Store with storeId: " + storeId + " Having ID " + orderId);

		return orderId;
	}

}
