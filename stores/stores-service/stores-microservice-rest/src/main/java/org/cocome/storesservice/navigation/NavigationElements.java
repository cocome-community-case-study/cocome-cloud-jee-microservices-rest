package org.cocome.storesservice.navigation;

/**
 * Enum containing all possible Navigation-Links within this Microservice
 * @author Niko Benkler
 * @author Robert Heinrich
 *
 */
public enum NavigationElements {
	LOGIN("/login", null),
	ENTERPRISE_MAIN("/enterpriseMain", null),
	STORE_MAIN("/storeMain", null),
	START_SALE("/store/start_sale", "cashier"),
	ORDER_PRODUCTS("/store/order_products", "stock manager"),
	SHOW_STOCK("/store/show_stock", "stock manager"),
	STOCK_REPORT("/store/show_reports", "store manager"),
	RECEIVE_PRODUCTS("/store/receive_products", "stock manager"),
	SHOW_ENTERPRISES("/enterprise/show_enterprises", "enterprise manager"),
	CREATE_ENTERPRISE("/enterprise/create_enterprise", "enterprise manager"),
	CREATE_PRODUCT("/enterprise/create_product", "enterprise manager"),
	SHOW_PRODUCTS("/enterprise/show_products", "enterprise manager");
	
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
