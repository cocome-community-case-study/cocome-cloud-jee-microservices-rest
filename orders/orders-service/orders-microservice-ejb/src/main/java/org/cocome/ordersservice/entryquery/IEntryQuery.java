package org.cocome.ordersservice.entryquery;

import java.util.Collection;

import javax.ejb.CreateException;
import javax.validation.constraints.NotNull;

import org.cocome.ordersservice.domain.OrderEntry;
import org.cocome.ordersservice.exceptions.QueryException;

public interface IEntryQuery {

	Collection<OrderEntry> getEntriesByOrderId(@NotNull long OrderId) throws QueryException;
	
	Collection<OrderEntry> getAllEntries();
	
	long createEntry(@NotNull long orderId, @NotNull long productId, @NotNull long amount) throws CreateException;
	
	void updateEntry(@NotNull long id, @NotNull long amount) throws QueryException;
	
	OrderEntry findEntryById(@NotNull long id);
	
	void deleteEntry(@NotNull long id) throws QueryException;
	
}
