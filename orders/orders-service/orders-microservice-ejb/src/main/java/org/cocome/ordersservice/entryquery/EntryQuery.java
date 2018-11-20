package org.cocome.ordersservice.entryquery;

import java.io.Serializable;
import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;
import org.cocome.ordersservice.domain.OrderEntry;
import org.cocome.ordersservice.domain.ProductOrder;
import org.cocome.ordersservice.exceptions.QueryException;
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
	public Collection<OrderEntry> getEntriesByOrderId(long orderId) throws QueryException {
		LOG.debug("QUERY: Trying to retrieve Order Entries for order with orderId: " + orderId);

		ProductOrder order = orderRepo.find(orderId);
		if (order == null) {
			LOG.debug("QUERY: Could not find Entries for order with id: " + orderId + ". Order not found");
			throw new QueryException("Could not find Entries for order with id: " + orderId + ". Order not found");
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
	public long createEntry(long orderId, long productId, long amount) throws CreateException {
		LOG.debug("QUERY: Trying to create Entry for order with orderId: " + orderId + " , productId: " + productId
				+ " and amount: " + amount);

		// find corresponding order
		ProductOrder order = orderRepo.find(orderId);
		if (order == null) {
			LOG.debug("QUERY: Could not create Entry for order with orderId: " + orderId + " , productId: " + productId
					+ " and amount: " + amount + ". Order does not exist!");
			throw new CreateException("QUERY: Could not create Entry for order with orderId: " + orderId
					+ " , productId: " + productId + " and amount: " + amount + ". Order does not exist!");
		}

		// create new entry
		OrderEntry entry = new OrderEntry();
		entry.setOrder(order);
		entry.setAmount(amount);
		entry.setProductId(productId);

		// persist entry
		long entryId = entryRepo.create(entry);

		if (entryId == COULD_NOT_CREATE_ENTITY) {
			LOG.debug("QUERY: Could not create Entry with orderId: " + orderId + " , productId: " + productId
					+ " and amount: " + amount);
			throw new CreateException("QUERY: Could not create Entry with orderId: " + orderId + " , productId: "
					+ productId + " and amount: " + amount);
		}

		LOG.debug("QUERY: Sucessfully created Entry for order with orderId: " + order.getId() + " , productId: "
				+ productId + " and amount: " + amount);

		// update order
		order.addToOrder(entry);
		orderRepo.update(order);

		return entryId;
	}

	@Override
	public void updateEntry(long id, long amount) throws QueryException {
		LOG.debug("QUERY: Trying to update Entry with id: " + id);
		OrderEntry entry = entryRepo.find(id);
		if (entry == null) {
			LOG.debug("QUERY: Could not update Entry with id: " + id + ". Entry does not exist.");
			throw new QueryException("Could not update Entry with id: " + id + ". Entry does not exist.");
		}

		entry.setAmount(amount);

		if (entryRepo.update(entry) == null) {
			LOG.error("QUERY: Could not update Entry with id: " + id);
			throw new QueryException(" Could not update Entry with id: " + id);
		}
		
		
		LOG.debug("QUery: Successfully updated entry with id: " + id);
		

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
	public void deleteEntry(long id) throws QueryException {
		LOG.debug("QUERY: Trying to dele entry with id: " + id);
		if (entryRepo.delete(id)) {
			LOG.debug("QUERY: Successfully deleted Entry with id: " + id);
			return;
		}
		LOG.debug("QUERY: Could not delete Entry with id: " + id);
		throw new QueryException("Could not delete Entry with id: " + id);
		
		
	}

	@Override
	public Collection<OrderEntry> getAllEntries() {
		LOG.debug("QUERY: Find all Entries");

		return entryRepo.all();
	}

}
