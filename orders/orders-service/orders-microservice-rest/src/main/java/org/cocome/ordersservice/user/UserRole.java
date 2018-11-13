package org.cocome.ordersservice.user;

import org.cocome.ordersservice.navigation.NavigationView;
/**
 * UserRole a user can have plus the according View
 * @author Niko Benkler
 * @author Robert Heinrich
 * @author Tobias Haßberg
 *
 */
public enum UserRole {
	ENTERPRISE_MANAGER(NavigationView.DEFAULT_VIEW),
	STORE_MANAGER(NavigationView.ORDERS_VIEW),
	STOCK_MANAGER(NavigationView.ORDERS_VIEW),
	CASHIER(NavigationView.DEFAULT_VIEW),
	ADMIN(NavigationView.ORDERS_VIEW),
	DATABASE_MANAGER(NavigationView.DEFAULT_VIEW),
	DEFAULT(NavigationView.DEFAULT_VIEW);
	
	private NavigationView assocView;
	
	UserRole(NavigationView assocView) {
		this.assocView = assocView;
	}
	
	public NavigationView associatedView() {
		return assocView;
	}
}
