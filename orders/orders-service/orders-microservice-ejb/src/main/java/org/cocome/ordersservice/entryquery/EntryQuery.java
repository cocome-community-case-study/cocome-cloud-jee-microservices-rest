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

/**
 * Query that provides high level methods for database queries converning
 * Order-Entries
 * 
 * @author Niko Benkler
 * @author Robert Heinrich
 *
 */
@Local
@Stateless
public class EntryQuery implements IEntryQuery, Serializable {

	private static final long serialVersionUID = -1473925571122335629L;
	private Logger LOG = Logger.getLogger(EntryQuery.class);
	private final long COULD_NOT_CREATE_ENTITY = -1;
	@EJB
	OrderEntryRepository entryRepo;

	@EJB
	ProductOrderRepository orderRepo;

	/**
	 * An Order has multiple Entries. This Method return all entries for one
	 * specific order
	 */
	@Override
	public Collection<OrderEntry> getEntriesByOrderId(long orderId) throws QueryException {
		LOG.debug("QUERY: Trying to retrieve Order Entries for order with orderId: " + orderId);

		// Query
		ProductOrder order = orderRepo.find(orderId);
		if (order == null) {
			LOG.debug("QUERY: Could not find Entries for order with id: " + orderId + ". Order not found");
			throw new QueryException("Could not find Entries for order with id: " + orderId + ". Order not found");
		}

		// Logging
		StringBuilder sb = new StringBuilder();
		sb.append("QUERY: Sucessfully found Order with id: " + orderId
				+ ". It has following entries [productId, amount]");
		for (OrderEntry entry : order.getOrderEntries()) {
			sb.append("[ " + entry.getProductId() + " ," + entry.getAmount() + " ]");
		}

		// result
		return order.getOrderEntries();
	}

	/**
	 * Create new Entry for given order. Cannot create Entry if no corresponding
	 * order is found
	 */
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

		// error handling
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

	/**
	 * Update Entry with given id. Only Amount can be updated. ProductId, OrderId,
	 * EntryId cannot be updated as this may lead to inconsistency
	 */
	@Override
	public void updateEntry(long id, long amount) throws QueryException {
		LOG.debug("QUERY: Trying to update Entry with id: " + id);

		// find order
		OrderEntry entry = entryRepo.find(id);
		if (entry == null) {
			LOG.debug("QUERY: Could not update Entry with id: " + id + ". Entry does not exist.");
			throw new QueryException("Could not update Entry with id: " + id + ". Entry does not exist.");
		}

		// update order
		entry.setAmount(amount);

		// persist update
		if (entryRepo.update(entry) == null) {
			LOG.error("QUERY: Could not update Entry with id: " + id);
			throw new QueryException(" Could not update Entry with id: " + id);
		}

		LOG.debug("QUery: Successfully updated entry with id: " + id);

	}
    
	/**
	 * Simple CRUD-Functionality to Find entry
	 * @throws QueryException 
	 */
	@Override
	public OrderEntry findEntryById(long id) throws QueryException {
		LOG.debug("QUERY: Trying to find Entry with id: " + id);
		OrderEntry entry = entryRepo.find(id);

		if (entry == null) {
			LOG.debug("QUERY: Did not find OrderEntry with id: " + id);
			throw new QueryException("Could not find Entry with id: " + id);
		}
		return entry;
	}

	/**
	 * Simple CRUD-Functionality to Delete entry
	 */
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

	/**
	 * Simple CRUD-Entry to retrieve all available Entries. <br>
	 * Logically, this function should never been used.
	 * @throws QueryException 
	 */
	@Override
	public Collection<OrderEntry> getAllEntries() throws QueryException {
		LOG.debug("QUERY: Find all Entries");
        Collection<OrderEntry> entries = entryRepo.all();
        
        if(entries == null) {
        	LOG.debug("QUERY: Could not retrieve all entries");
    		throw new QueryException("Could not retrieve all entries");
        }
		return entries;
	}

}
