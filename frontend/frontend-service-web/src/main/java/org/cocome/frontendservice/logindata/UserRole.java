package org.cocome.frontendservice.logindata;

import org.cocome.frontendservice.navigation.NavigationViewStates;

public enum UserRole {
	ENTERPRISE_MANAGER(NavigationViewStates.ENTERPRISE_VIEW),
	STORE_MANAGER(NavigationViewStates.STORE_VIEW),
	STOCK_MANAGER(NavigationViewStates.STORE_VIEW),
	CASHIER(NavigationViewStates.STORE_VIEW),
	ADMIN(NavigationViewStates.STORE_VIEW),
	DATABASE_MANAGER(NavigationViewStates.ENTERPRISE_VIEW),
	DEFAULT(NavigationViewStates.DEFAULT_VIEW);
	
	private NavigationViewStates assocView;
	
	UserRole(NavigationViewStates assocView) {
		this.assocView = assocView;
	}
	
	public NavigationViewStates associatedView() {
		return assocView;
	}
}
