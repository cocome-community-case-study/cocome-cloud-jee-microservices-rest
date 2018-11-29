package org.cocome.ordersservice.frontend.order;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.CreateException;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.cocome.ordersservice.domain.OrderEntry;
import org.cocome.ordersservice.frontend.entry.EntryManager;
import org.cocome.ordersservice.frontend.viewdata.EntryViewData;
import org.cocome.ordersservice.frontend.viewdata.ProductViewData;
import org.cocome.ordersservice.navigation.NavigationElements;
import org.cocome.ordersservice.navigation.NavigationMenu;

/**
 * This class is used as CDI-Bean for JSF-requests. It stores information and
 * provides functionality for the create-order use case
 * 
 * @author Niko Benkler
 * @author Robert Heinrich
 *
 */
@Named
@ViewScoped
public class CreateOrderView implements Serializable {

	@Inject
	NavigationMenu menu;

	@Inject
	OrderManager orderManager;

	@Inject
	EntryManager entryManager;

	private static final long serialVersionUID = -5947547750360036448L;

	private Map<Long, OrderEntry> entries = new HashMap<>();

	private long activeStoreId;

	private long orderId;
	

	/**
	 * Add a product to the order that is still in creation process
	 * 
	 * @param product
	 * @param amount
	 */
	public void addProductToOrder(ProductViewData product, long amount) {

		/*
		 * If Product is selected twice, just add the amount to the existing
		 */
		if (entries.containsKey(product.getId())) {
			amount += entries.get(product.getId()).getAmount();
		}
		
		//Create new Entry
		OrderEntry entry = new OrderEntry();
		entry.setAmount(amount);
		entry.setProductId(product.getId());
		// entry.setOrder(productOrder); Set order when submitting it
		
		//Add to current Entry-List
		entries.put(product.getId(), entry);

	}

	/**
	 * Remove entry from current List
	 * @param entryId
	 */
	public void removeOrderEntry(long entryId) {
		entries.remove(entryId);

	}

	/**
	 * Submitting an order is done by creating an Order and creating the
	 * OrderEntries which where store temporarily in the entries Map
	 * 
	 * @return
	 */
	public String submitOrder() {
		activeStoreId = menu.getActiveStoreId();

		if (entries.isEmpty()) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Please add something to your order!", null));
			return NavigationElements.ORDER_PRODUCTS.getNavigationOutcome();
		}

		// persist order
		try {

			// persist order
			orderId = orderManager.createOrder(new Date(), new Date(), activeStoreId);

			// persist the entries
			for (OrderEntry entry : entries.values()) {
				// TODO Error Handling!
				entryManager.createEntry(orderId, entry.getProductId(), entry.getAmount());
			}

		} catch (CreateException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
			return NavigationElements.EMPTY_PAGE.getNavigationOutcome();
		}

		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Successfully submitted the order! Order Id is: " + orderId, null));
		return NavigationElements.EMPTY_PAGE.getNavigationOutcome();
	}

	public void resetOrder() {
		entries.clear();
		activeStoreId = -1;
		orderId = -1;
		// return NavigationElements.ORDERS_MAIN.getNavigationOutcome();

	}

	public void loadProducts() {

	}

	public Collection<EntryViewData> getCurrentEntries() {

		return EntryViewData.fromEntryCollection(entries.values());
	}

}
