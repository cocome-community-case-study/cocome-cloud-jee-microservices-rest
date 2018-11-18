package org.cocome.ordersservice.frontend.order;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import org.cocome.ordersservice.domain.ProductOrder;
import org.cocome.ordersservice.frontend.viewdata.OrderViewData;
import org.cocome.ordersservice.navigation.NavigationElements;
import org.cocome.ordersservice.orderquery.IOrderQuery;

@Named
@ApplicationScoped
public class OrderManager implements IOrderManager, Serializable {

	@EJB
	IOrderQuery orderQuery;

	private static final long serialVersionUID = 8498016353431902188L;

	private final long COULD_NOT_CREATE_ENTITY = -1;

	@Override
	public Collection<OrderViewData> getOrdersByStoreId(@NotNull long storeId) {
		 
		Collection<ProductOrder> orders = orderQuery.getOrdersByStoreId(storeId);
		
		if(orders == null) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "An Error occured while retrieving Orders ", null));
           return new LinkedList<OrderViewData>(); //Prevent Nullpointer access
			
		}
		if(orders.isEmpty()) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "No Orders available for this store", null));
			
		}
		return OrderViewData.fromOrderCollection(orders);
	}

	@Override
	public long createOrder(Date deliveryDate, Date orderingDate, @NotNull long storeId) {

		long orderId = orderQuery.createOrder(deliveryDate, orderingDate, storeId);
		if (orderId == COULD_NOT_CREATE_ENTITY) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Cannot create Order! ", null));

			return COULD_NOT_CREATE_ENTITY;
		}

		return orderId;
	}

	@Override
	public String deleteOrder(@NotNull long orderId) {
		
		if(orderQuery.deleteOrder(orderId)) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Successfully deleted the Order!", null));
			return NavigationElements.ORDERS_MAIN.getNavigationOutcome();
		}
		
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, "Cannot delete Order! ", null));

		return NavigationElements.ORDERS_MAIN.getNavigationOutcome();
	}

	@Override
	public OrderViewData findOrderById(@NotNull long orderId) {
		ProductOrder order = orderQuery.findOrderById(orderId);
		if(order==null) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Did not find Order! ", null));
			return null;
		}
		
		return OrderViewData.fromOrder(order);
	}

}
