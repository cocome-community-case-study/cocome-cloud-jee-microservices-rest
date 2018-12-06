package org.cocome.frontendservice.navigation;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.cocome.frontendservice.logindata.IUser;


/**
 * This class creates the NavigationMenu-Bar of the Frontend-Header.
 * Besides, it stores the current state of the Frontend (OrderMicroserviceView included etc...)
 * We decided to separate the actual State and the NavigationElement in two Enums. This required some more Code but
 * might help for extensions in the future.
 * @author Niko Benkler
 *
 */
public interface INavigationMenu {

	/**
	 * Retrieves the navigational elements that have to be shown 
	 * to the user depending on the current state of the navigation menu. 
	 * 
	 * @return the current navigation elements to display
	 */
	List<INavigationElement> getNavigationElements();
	
	/**
	 * Changes the state of the menu to a new state and changes 
	 * the elements that will be displayed to the user based on the new state.
	 * After a call to this method the {@code getElements} method should be 
	 * called to get the elements of the new navigation state.  
	 * 
	 * @param newState - the new state to set
	 */
	void changeStateTo(@NotNull NavigationView newState);
	
	
	

	/**
	 * Returns the current state of this navigation menu.
	 * 
	 * @return the current state
	 */
	NavigationView getCurrentState();

	IUser getCurrentUser();

	

	
	
}