package org.cocome.storesservice.navigation;

/**
 * Enum containing all possible Navigation-Links within this Microservice
 * @author Niko Benkler
 * @author Robert Heinrich
 *
 */
public enum NavigationElements {
	ERROR("/error", null),
	ENTERPRISE_MAIN("/enterprise_main", null),
	STORE_MAIN("/store_main", null),
	START_SALE("/store/start_sale", "cashier"),
	SHOW_STOCK("/store/show_stock", "stock manager"),
	SHOW_ENTERPRISES("/enterprise/show_enterprises", "enterprise manager"),
	CREATE_ENTERPRISE("/enterprise/create_enterprise", "enterprise manager"),
	SHOW_STORES("/enterprise/show_stores", "enterprise manager"),
	EDIT_STORE("/enterprise/edit_store","enterprise manager"),
	WELCOME_STORE("/store/welcome", null);
	
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
