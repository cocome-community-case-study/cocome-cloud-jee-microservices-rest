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

import org.cocome.ordersservice.frontend.viewdata.OrderViewData;
import org.cocome.ordersservice.navigation.INavigationMenu;
import org.cocome.ordersservice.navigation.NavigationElements;

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

		orders = orderManager.getOrdersByStoreId(navMenu.getActiveStoreId())
				.stream()
				.collect(Collectors
				.toMap(OrderViewData::getId, item -> item));

	}

	public void loadOrder(long orderId) {
		OrderViewData order = orderManager.findOrderById(orderId);
		if (order == null) {

			return;
		}

		orders.put(order.getId(), order);

	}

	public Collection<OrderViewData> getOrders() {
		return orders.values();
	}

	public String rollInOrder(OrderViewData order) {
		if (orderManager.rollInOrder(order)) {

			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Order was rolled in successfully! ", null));
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error while rolling in the order! ", null));

		}
		return NavigationElements.ORDERS_MAIN.getNavigationOutcome(); // TODO other navigation?
	}
}
