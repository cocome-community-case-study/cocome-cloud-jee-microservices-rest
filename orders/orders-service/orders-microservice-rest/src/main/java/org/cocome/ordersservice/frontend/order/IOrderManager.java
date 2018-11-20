package org.cocome.ordersservice.frontend.order;

import java.util.Collection;
import java.util.Date;

import javax.ejb.CreateException;
import javax.validation.constraints.NotNull;

import org.cocome.ordersservice.exceptions.QueryException;
import org.cocome.ordersservice.frontend.viewdata.OrderViewData;
import org.cocome.productsclient.exception.ProductsRestException;
import org.cocome.storesclient.exception.StoreRestException;

public interface IOrderManager {
	
	Collection<OrderViewData> getOrdersByStoreId(@NotNull long storeId) throws QueryException;
	
	long createOrder(Date deliveryDate, Date orderingDate,@NotNull long storeId) throws CreateException;
	
	String deleteOrder(@NotNull long orderId);
	
	OrderViewData findOrderById(@NotNull long orderId) throws QueryException;
	
	void rollInOrder(long orderId) throws ProductsRestException, QueryException, StoreRestException;
	

}
