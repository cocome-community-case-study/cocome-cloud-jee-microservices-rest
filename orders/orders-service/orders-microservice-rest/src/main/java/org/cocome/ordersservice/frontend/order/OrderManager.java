package org.cocome.ordersservice.frontend.order;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;

import javax.ejb.CreateException;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import org.cocome.ordersservice.controller.ProductOrderResource;
import org.cocome.ordersservice.domain.ProductOrder;
import org.cocome.ordersservice.exceptions.QueryException;
import org.cocome.ordersservice.frontend.viewdata.OrderViewData;
import org.cocome.ordersservice.frontend.viewdata.ProductViewData;
import org.cocome.ordersservice.navigation.NavigationElements;
import org.cocome.ordersservice.orderquery.IOrderQuery;
import org.cocome.storesclient.exception.MicroserviceException;

@Named
@ApplicationScoped
public class OrderManager implements IOrderManager, Serializable {

	@EJB
	IOrderQuery orderQuery;

	ProductOrderResource resource = new ProductOrderResource();

	private static final long serialVersionUID = 8498016353431902188L;

	

	@Override
	public Collection<OrderViewData> getOrdersByStoreId(@NotNull long storeId) throws QueryException {

		Collection<ProductOrder> orders = orderQuery.getOrdersByStoreId(storeId);

		
		
		return OrderViewData.fromOrderCollection(orders);
	}

	@Override
	public long createOrder(Date deliveryDate, Date orderingDate, @NotNull long storeId) throws CreateException {

		return orderQuery.createOrder(deliveryDate, orderingDate, storeId);
		
		
	}

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

	@Override
	public OrderViewData findOrderById(@NotNull long orderId) throws QueryException {
		ProductOrder order = orderQuery.findOrderById(orderId);
		
	

		return OrderViewData.fromOrder(order);
	}

	@Override
	public void rollInOrder(long orderId) throws MicroserviceException, QueryException {
		
			orderQuery.rollInOrder(orderId);
		
	}

}
