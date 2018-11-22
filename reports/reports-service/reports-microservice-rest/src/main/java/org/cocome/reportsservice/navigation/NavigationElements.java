package org.cocome.reportsservice.navigation;

/**
 * Enum containing all possible Navigation-Links within this Microservice
 * @author Niko Benkler
 * @author Robert Heinrich
 *
 */
public enum NavigationElements {
	ERROR("/error", null),
	REPORTS_MAIN("/reports_main", null),
	ENTERPRISE_STOCK("/reports/enterprisestock_report", "enterprise manager"),
	STORE_STOCK("/reports/storestock_report", "stock manager"),
	ENTERPRISE_DELIVERY("/reports/enterprise_delivery", "enterprise manager");
	
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
