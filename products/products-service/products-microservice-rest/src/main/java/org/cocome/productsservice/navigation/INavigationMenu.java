package org.cocome.productsservice.navigation;

import java.util.List;

import javax.validation.constraints.NotNull;

/**
 * Interface representing the navigation menu on the site. 
 * 
 * @author Tobias Pöppke
 * @author Robert Heinrich
 */
public interface INavigationMenu {

	/**
	 * Retrieves the navigational elements that have to be shown 
	 * to the user depending on the current state of the navigation menu. 
	 * 
	 * @return the current navigation elements to display
	 */
	List<INavigationElement> getElements();
	
/**
 * Creates Header according to user that is logged in
 * @return
 */
	String changeStateTo(@NotNull NavigationView newState);
	
	
}