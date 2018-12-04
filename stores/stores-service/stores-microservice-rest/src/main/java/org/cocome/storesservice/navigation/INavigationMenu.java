package org.cocome.storesservice.navigation;

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
	 * Changes the state of the menu to a new state and changes 
	 * the elements that will be displayed to the user based on the new state.
	 * After a call to this method the {@code getElements} method should be 
	 * called to get the elements of the new navigation state.  
	 * 
	 * @param newState - the new state to set
	 */
	String changeStateTo(@NotNull NavigationView newState);
	
	/**
	 * Returns the current state of this navigation menu.
	 * 
	 * @return the current state
	 */
	NavigationView getCurrentState();

	long getActiveStoreId();

	void setActiveStoreId(long activeStoreId);
}