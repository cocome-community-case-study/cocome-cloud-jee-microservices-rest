package org.cocome.productsservice.navigation;

/**
 * Enum containing all possible Navigation-Links within this Microservice
 * @author Niko Benkler
 * @author Robert Heinrich
 *
 */
public enum NavigationElements {
	PRODUCTS_MAIN("products_main" , null),
	SHOW_PRODUCTS("/product/show_products", "enterprise manager"), //TODO das brauch man da doch nicht oder?
	CREATE_PRODUCT("/product/create_products", "enterprise manager"),
	SHOW_SUPPLIERS("/supplier/show_suppliers","enterprise manager"),
	CREATE_SUPPLIER("/supplier/create_supplier", "enterprise manager"),
	ERROR("error", null);
	
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
