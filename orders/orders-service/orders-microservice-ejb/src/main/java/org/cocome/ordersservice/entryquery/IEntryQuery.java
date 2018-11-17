package org.cocome.ordersservice.entryquery;

import java.util.Collection;

import javax.validation.constraints.NotNull;

import org.cocome.ordersservice.domain.OrderEntry;

public interface IEntryQuery {

	Collection<OrderEntry> getEntriesByOrderId(@NotNull long OrderId);
	
	Collection<OrderEntry> getAllEntries();
	
	long createEntry(@NotNull long orderId, @NotNull long productId, @NotNull long amount);
	
	boolean updateEntry(@NotNull long id, @NotNull long amount);
	
	OrderEntry findEntryById(@NotNull long id);
	
	boolean deleteEntry(@NotNull long id);
	
}
