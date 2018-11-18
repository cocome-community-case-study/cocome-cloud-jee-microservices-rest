package org.cocome.ordersservice.frontend.order;

import java.util.Collection;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.cocome.ordersservice.frontend.viewdata.OrderViewData;

public interface IOrderManager {
	
	Collection<OrderViewData> getOrdersByStoreId(@NotNull long storeId);
	
	long createOrder(Date deliveryDate, Date orderingDate,@NotNull long storeId);
	
	String deleteOrder(@NotNull long orderId);
	
	OrderViewData findOrderById(@NotNull long orderId);
	

}
