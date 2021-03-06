package org.cocome.ordersservice.navigation;


/**
 * Enum containing all possible Navigation-Links within this Microservice
 * @author Niko Benkler
 * @author Robert Heinrich
 *
 */
public enum NavigationElements {
	ERROR("/error", null),
	ORDERS_MAIN("/orders_main", null),
	ORDER_PRODUCTS("/orders/order_products", "store manager"),
	RECEIVE_ORDER("/orders/receive_order", "stock manager"),
	EMPTY_PAGE("/orders/empty_page" , null);
	
	private String navOutcome;
	private String permission;
	
	private NavigationElements(String navOutcome, String permission) {
		this.navOutcome = navOutcome;
		this.permission = permission;
	}
	
	public String getNavigationOutcome() {
		return this.navOutcome;
	}
	
	public String getNeededPermission() {
		return this.permission;
	}
}
