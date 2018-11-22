package org.cocome.ordersservice.frontend.order;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.cocome.ordersservice.exceptions.QueryException;
import org.cocome.ordersservice.frontend.viewdata.OrderViewData;
import org.cocome.ordersservice.navigation.INavigationMenu;
import org.cocome.ordersservice.navigation.NavigationElements;
import org.cocome.productsclient.exception.ProductsRestException;
import org.cocome.storesclient.exception.StoreRestException;

@Named
@ViewScoped
public class ReceiveOrderView implements Serializable {

	@Inject
	IOrderManager orderManager;

	@Inject
	INavigationMenu navMenu;

	/**
	 * 
	 */
	private static final long serialVersionUID = -3063243515118934691L;
	private Map<Long, OrderViewData> orders;

	@PostConstruct
	private void postContruct() {
		orders = new HashMap<>();

	}

	public void loadAllOrders() {

		try {
			orders = orderManager.getOrdersByStoreId(navMenu.getActiveStoreId()).stream()
					.collect(Collectors.toMap(OrderViewData::getId, item -> item));
		} catch (QueryException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
		}

		if (orders.isEmpty()) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "No Orders available for this Store", null));
		}

	}

	public void loadOrder(long orderId) {
		OrderViewData order;
		try {
			order = orderManager.findOrderById(orderId);
			orders.put(order.getId(), order);
		} catch (QueryException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
		}

	}

	public Collection<OrderViewData> getOrders() {
		return orders.values();
	}

	public String rollInOrder(OrderViewData order) {

		try {
			orderManager.rollInOrder(order.getId());
			
		} catch (ProductsRestException  | QueryException  | StoreRestException e ) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
		} 
		
		
		
		
		
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Order was rolled in successfully! ", null));
		return NavigationElements.EMPTY_PAGE.getNavigationOutcome();
	}
}
