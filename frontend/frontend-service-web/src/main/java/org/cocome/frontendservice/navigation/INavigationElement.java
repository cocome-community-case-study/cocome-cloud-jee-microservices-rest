package org.cocome.frontendservice.navigation;

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
	 * get corresponding EnumElement for frontend
	 * @return
	 */
	NavigationElements getNavElementAsEnum();
	


}