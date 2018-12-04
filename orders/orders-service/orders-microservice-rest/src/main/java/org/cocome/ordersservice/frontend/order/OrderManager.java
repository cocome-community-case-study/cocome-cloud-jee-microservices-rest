package org.cocome.ordersservice.frontend.order;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.ejb.ApplicationException;
import javax.ejb.CreateException;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import org.cocome.ordersservice.controller.ProductOrderResource;
import org.cocome.ordersservice.domain.ProductOrder;
import org.cocome.ordersservice.exceptions.QueryException;
import org.cocome.ordersservice.frontend.viewdata.OrderViewData;
import org.cocome.ordersservice.navigation.NavigationElements;
import org.cocome.ordersservice.orderquery.IOrderQuery;
import org.cocome.productsclient.exception.ProductsRestException;
import org.cocome.storesclient.exception.StoreRestException;

/**
 * This class is an interface between frontend and backend functionality for order functionality
 * @author Niko Benkler
 * @author Robert Heinrich
 *
 */
@Named
@SessionScoped
public class OrderManager implements IOrderManager, Serializable {

	@EJB
	IOrderQuery orderQuery;

	ProductOrderResource resource = new ProductOrderResource();

	private static final long serialVersionUID = 8498016353431902188L;

	

	/**
	 * Retrieve orders from backend by given storeId
	 */
	@Override
	public Collection<OrderViewData> getOrdersByStoreId(@NotNull long storeId) throws QueryException {

		Collection<ProductOrder> orders = orderQuery.getOrdersByStoreId(storeId);

		
		
		return OrderViewData.fromOrderCollection(orders);
	}

	/**
	 * Send create query 
	 */
	@Override
	public long createOrder(Date deliveryDate, Date orderingDate, @NotNull long storeId) throws CreateException {

		return orderQuery.createOrder(deliveryDate, orderingDate, storeId);
		
		
	}

	/**
	 * Send delete query
	 */
	@Override
	public String deleteOrder(@NotNull long orderId) {

		
		try {
			orderQuery.deleteOrder(orderId);
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Successfully deleted the Order!", null));
			
			
		} catch (QueryException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Cannot delete Order! ", null));
		}
		
		
		return NavigationElements.EMPTY_PAGE.getNavigationOutcome();
	}

	/**
	 * Send backend query to get order by given id
	 */
	@Override
	public OrderViewData findOrderById(@NotNull long orderId) throws QueryException {
		ProductOrder order = orderQuery.findOrderById(orderId);
		
	

		return OrderViewData.fromOrder(order);
	}
    
	/**
	 * Send backend query to roll in order ==> Update Stock items in store
	 */
	@Override
	public void rollInOrder(long orderId) throws ProductsRestException, QueryException, StoreRestException {
		
			orderQuery.rollInOrder(orderId);
		
	}

}
