package org.cocome.ordersservice.frontend.entry;

import javax.ejb.CreateException;
import javax.validation.constraints.NotNull;

public interface IEntryManager {

	
	long createEntry(@NotNull long orderId, @NotNull long productId, @NotNull long amount) throws CreateException;
}
