package org.cocome.frontendservice.resolver;

import org.cocome.frontendservice.navigation.NavigationElement;

/**
 * Delegates new NavigationView to NavigationMenu and handles navigation
 * destinations (remote server URL's) for this View
 * 
 * @author Niko Benkler
 * @author Robert Heinrich
 *
 */

public interface IServiceRedirecter {

	

	public void redirect(NavigationElement navElement);
	
	

}