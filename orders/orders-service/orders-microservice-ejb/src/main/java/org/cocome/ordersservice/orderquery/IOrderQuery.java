package org.cocome.ordersservice.orderquery;

import java.util.Collection;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.cocome.ordersservice.domain.ProductOrder;

public interface IOrderQuery {
	
	ProductOrder findOrderById(@NotNull long id);
	
	Collection<ProductOrder> getAllOrders();
	
	boolean updateOrder(@NotNull long id, @NotNull Date deliveryDate, @NotNull Date orderingDate);
	
	boolean deleteOrder(@NotNull long id);
	
	Collection<ProductOrder> getOrdersByStoreId(@NotNull long storeId);
	
	long createOrder(@NotNull Date deliveryDate, @NotNull Date orderingDate, @NotNull long storeId);
	
	

}
