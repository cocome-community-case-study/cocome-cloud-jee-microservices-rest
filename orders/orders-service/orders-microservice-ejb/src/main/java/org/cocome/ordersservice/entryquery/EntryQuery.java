package org.cocome.ordersservice.entryquery;

import java.io.Serializable;
import java.util.Collection;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;
import org.cocome.ordersservice.domain.OrderEntry;
import org.cocome.ordersservice.domain.ProductOrder;
import org.cocome.ordersservice.repository.OrderEntryRepository;
import org.cocome.ordersservice.repository.ProductOrderRepository;

@Local
@Stateless
public class EntryQuery implements IEntryQuery, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1473925571122335629L;
	private Logger LOG = Logger.getLogger(EntryQuery.class);
	private final long COULD_NOT_CREATE_ENTITY = -1;
	@EJB
	OrderEntryRepository entryRepo;

	@EJB
	ProductOrderRepository orderRepo;

	@Override
	public Collection<OrderEntry> getEntriesByOrderId(long orderId) {
		LOG.debug("QUERY: Trying to retrieve Order Entries for order with orderId: " + orderId);

		ProductOrder order = orderRepo.find(orderId);
		if (order == null) {
			LOG.debug("QUERY: Could not find Entries for order with id: " + orderId + ". Order not found");
			return null;
		}
		StringBuilder sb = new StringBuilder();
		sb.append("QUERY: Sucessfully found Order with id: " + orderId
				+ ". It has following entries [productId, amount]");
		for (OrderEntry entry : order.getOrderEntries()) {
			sb.append("[ " + entry.getProductId() + " ," + entry.getAmount() + " ]");
		}
		return order.getOrderEntries();
	}

	@Override
	public long createEntry(long orderId, long productId, long amount) {
		LOG.debug("QUERY: Trying to create Entry for order with orderId: " + orderId + " , productId: " + productId
				+ " and amount: " + amount);
		ProductOrder order = orderRepo.find(orderId);
		if (order == null) {
			LOG.debug("QUERY: Could not create Entry for order with orderId: " + orderId + " , productId: " + productId
					+ " and amount: " + amount + ". Order does not exist!");
			return COULD_NOT_CREATE_ENTITY;
		}
		OrderEntry entry = new OrderEntry();
		entry.setOrder(order);
		entry.setAmount(amount);
		entry.setProductId(productId);

		long entryId = entryRepo.create(entry);
		if (entryId == COULD_NOT_CREATE_ENTITY) {
			LOG.debug("QUERY: Could not create Entry with orderId: " + orderId + " , productId: " + productId
					+ " and amount: " + amount);
			return COULD_NOT_CREATE_ENTITY;
		}
		LOG.debug("QUERY: Sucessfully created Entry for order with orderId: " + order.getId() + " , productId: " + productId
				+ " and amount: " + amount);
		return entryId;
	}

	@Override
	public boolean updateEntry(long id, long amount) {
		LOG.debug("QUERY: Trying to update Entry with id: " + id);
		OrderEntry entry = entryRepo.find(id);
		if (entry == null) {
			LOG.debug("QUERY: Could not update Entry with id: " + id + ". Entry does not exist.");
			return false;
		}

		entry.setAmount(amount);

		if (entryRepo.update(entry) == null) {
			LOG.error("QUERY: Could not update Entry with id: " + id);
			return false;
		}
		LOG.debug("QUery: Successfully updated entry with id: " + id);
		return true;
	}

	@Override
	public OrderEntry findEntryById(long id) {
		LOG.debug("QUERY: Trying to find Entry with id: " + id);
		OrderEntry entry = entryRepo.find(id);

		if (entry == null) {
			LOG.debug("QUERY: Did not find OrderEntry with id: " + id);
			return null;
		}
		return entry;
	}

	@Override
	public boolean deleteEntry(long id) {
		LOG.debug("QUERY: Trying to dele entry with id: " + id);
		if (entryRepo.delete(id)) {
			LOG.debug("QUERY: Successfully deleted Entry with id: " + id);
			return true;
		}
		LOG.debug("QUERY: Could not delete Entry with id: " + id);
		return false;
	}

	@Override
	public Collection<OrderEntry> getAllEntries() {
		LOG.debug("QUERY: Find all Entries");
		
		return entryRepo.all();
	}

}
