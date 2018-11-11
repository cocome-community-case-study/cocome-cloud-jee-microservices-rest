package org.cocome.productsservice.user;

import org.cocome.productsservice.navigation.NavigationView;
/**
 * UserRole a user can have plus the according HeaderView/NavigationView in this Service
 * @author Niko Benkler
 * @author Robert Heinrich
 * @author Tobias Haßberg
 *
 */
public enum UserRole {
	/*
	 * Important: This Enum Determines, which Navigation Elements are presented to which User
	 */
	ENTERPRISE_MANAGER(NavigationView.PRODUCTS_VIEW),
	STORE_MANAGER(NavigationView.DEFAULT_VIEW),
	STOCK_MANAGER(NavigationView.DEFAULT_VIEW),
	CASHIER(NavigationView.DEFAULT_VIEW),
	ADMIN(NavigationView.PRODUCTS_VIEW),
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
