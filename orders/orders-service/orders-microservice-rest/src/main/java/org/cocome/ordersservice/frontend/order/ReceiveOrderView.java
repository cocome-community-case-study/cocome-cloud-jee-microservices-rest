package org.cocome.ordersservice.frontend.order;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
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

/**
 * This class is used as CDI-Bean for JSF-requests. It stores information and
 * provides functionality for the receive-order use case
 * 
 * @author Niko Benkler
 * @author Robert Heinrich
 *
 */
@Named
@ViewScoped
public class ReceiveOrderView implements Serializable {

	@Inject
	IOrderManager orderManager;

	@Inject
	INavigationMenu navMenu;

	private static final long serialVersionUID = -3063243515118934691L;
	
	/**
	 * Caching for order!
	 */
	private Map<Long, OrderViewData> orders;

	@PostConstruct
	private void postContruct() {
		orders = new HashMap<>();

	}

	/**
	 * Load all Order for currently active store (sotored in nacMenu)
	 */
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

	/**
	 * Load Order with a given order ID based on currently logged in
	 * store/stock-manager <br>
	 * ==> Depends on Active Store: Does not display order that exist but has a
	 * different store id
	 * 
	 * @param orderId
	 */
	public void loadOrder(long orderId) {
		OrderViewData order;
		try {
			order = orderManager.findOrderById(orderId);
			if (order.getStoreId() != navMenu.getActiveStoreId()) {

				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Order with id: " + orderId + " does not exist for store with id: " + navMenu.getActiveStoreId(),
						null));
				return;
			}
			orders.put(order.getId(), order);
		} catch (QueryException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
		}

	}

	/**
	 * Return currently cached orders!
	 * @return
	 */
	public Collection<OrderViewData> getOrders() {
		return orders.values();
	}

	/**
	 * Roll in order with given id
	 * @param order
	 * @return
	 */
	public String rollInOrder(OrderViewData order) {

		try {
			orderManager.rollInOrder(order.getId());

		} catch (ProductsRestException | QueryException | StoreRestException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
		}

		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Order was rolled in successfully! ", null));
		return NavigationElements.EMPTY_PAGE.getNavigationOutcome();
	}
}
