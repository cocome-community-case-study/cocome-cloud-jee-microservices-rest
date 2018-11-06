package org.cocome.storesservice.user;

import org.cocome.storesservice.navigation.NavigationView;
/**
 * UserRole a user can have plus the according View
 * @author Niko Benkler
 * @author Robert Heinrich
 * @author Tobias Haßberg
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
