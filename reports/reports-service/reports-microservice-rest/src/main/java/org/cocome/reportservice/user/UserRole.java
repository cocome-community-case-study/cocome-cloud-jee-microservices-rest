package org.cocome.reportservice.user;

import org.cocome.reportsservice.navigation.NavigationView;
/**
 * UserRole a user can have plus the according View
 * @author Niko Benkler
 * @author Robert Heinrich
 * @author Tobias Haßberg
 *
 */
public enum UserRole {
	ENTERPRISE_MANAGER(NavigationView.REPORTS_VIEW),
	STORE_MANAGER(NavigationView.REPORTS_VIEW),
	STOCK_MANAGER(NavigationView.DEFAULT_VIEW),
	CASHIER(NavigationView.DEFAULT_VIEW),
	ADMIN(NavigationView.REPORTS_VIEW),
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
