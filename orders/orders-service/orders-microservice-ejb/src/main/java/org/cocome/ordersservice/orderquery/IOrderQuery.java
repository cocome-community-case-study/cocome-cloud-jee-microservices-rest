package org.cocome.ordersservice.orderquery;

import java.util.Collection;
import java.util.Date;

import javax.ejb.CreateException;
import javax.validation.constraints.NotNull;

import org.cocome.ordersservice.domain.ProductOrder;
import org.cocome.ordersservice.exceptions.QueryException;
import org.cocome.productsclient.exception.ProductsRestException;
import org.cocome.storesclient.exception.StoreRestException;


public interface IOrderQuery {
	
	ProductOrder findOrderById(@NotNull long id) throws QueryException;
	
	Collection<ProductOrder> getAllOrders() throws QueryException;
	
	void updateOrder(@NotNull long id, @NotNull Date deliveryDate, @NotNull Date orderingDate) throws QueryException;
	
	void deleteOrder(@NotNull long id) throws QueryException;
	
	Collection<ProductOrder> getOrdersByStoreId(@NotNull long storeId) throws QueryException;
	
	long createOrder(@NotNull Date deliveryDate, @NotNull Date orderingDate, @NotNull long storeId) throws CreateException;
	
	void rollInOrder(long orderId) throws StoreRestException, QueryException, ProductsRestException ;
	
	

}
