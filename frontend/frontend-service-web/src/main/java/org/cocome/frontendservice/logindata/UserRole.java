package org.cocome.frontendservice.logindata;

import org.cocome.frontendservice.navigation.NavigationView;
/**
 * Represents UserRole and determines whether StoreID is required when loggin in or not <br>
 * ENTERPRISE_VIEW ==> NO
 * STORE_VIEW ==> YES
 * @author Niko Benkler
 * @author Robert Heinrich
 *
 */
public enum UserRole {
	ENTERPRISE_MANAGER(NavigationView.ENTERPRISE_VIEW),
	STORE_MANAGER(NavigationView.STORE_VIEW),
	STOCK_MANAGER(NavigationView.STORE_VIEW),
	CASHIER(NavigationView.STORE_VIEW),
	ADMIN(NavigationView.STORE_VIEW),
	DATABASE_MANAGER(NavigationView.ENTERPRISE_VIEW),
	DEFAULT(NavigationView.DEFAULT_VIEW);
	
	private NavigationView assocView;
	
	UserRole(NavigationView assocView) {
		this.assocView = assocView;
	}
	
	public NavigationView associatedView() {
		return assocView;
	}
	
	
	
	
	
}
