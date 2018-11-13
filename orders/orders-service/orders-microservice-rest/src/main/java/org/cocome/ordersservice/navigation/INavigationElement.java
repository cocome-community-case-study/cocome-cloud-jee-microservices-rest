package org.cocome.ordersservice.navigation;

public interface INavigationElement {

	/**
	 * 
	 * @return
	 */
	String getNavOutcome();

	/**
	 * 
	 * @return
	 */
	String getDisplayText();
	
	/**
	 * 
	 * @return
	 */
	String getRequiredPermission();

}