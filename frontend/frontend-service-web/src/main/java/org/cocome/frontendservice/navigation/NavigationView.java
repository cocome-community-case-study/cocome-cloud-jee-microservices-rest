package org.cocome.frontendservice.navigation;

/**
 * Possible Views a user can Request
 * This is necessary as we might have two different services on one remote server (store, enterprise on storesservice!)
 * @author Niko Benkler
 * @author Robert Heinrich
 *
 */
public enum NavigationView {
	ENTERPRISE_VIEW,
	STORE_VIEW,
	PRODUCTS_VIEW,
	ORDERS_VIEW,
	DEFAULT_VIEW, 
    LOGIN;
}
