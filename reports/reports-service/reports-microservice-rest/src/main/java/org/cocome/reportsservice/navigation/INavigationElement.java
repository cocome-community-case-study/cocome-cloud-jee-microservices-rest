package org.cocome.reportsservice.navigation;

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