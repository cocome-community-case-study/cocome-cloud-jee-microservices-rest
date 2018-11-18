package org.cocome.ordersservice.frontend.entry;

import javax.validation.constraints.NotNull;

public interface IEntryManager {

	
	long createEntry(@NotNull long orderId, @NotNull long productId, @NotNull long amount);
}
