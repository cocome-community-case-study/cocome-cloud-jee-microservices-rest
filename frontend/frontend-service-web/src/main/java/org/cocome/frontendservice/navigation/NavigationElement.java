package org.cocome.frontendservice.navigation;

import org.cocome.frontendservice.util.ResourceLoader;

/**
 * Navigation Element are the Click Buttons in the frontend Header
 * 
 * @author Niko Benkler
 * @author Robert Heinrich
 *
 */
public class NavigationElement implements INavigationElement {

	private String navOutcome;
	private String displayText;
	private NavigationElements navElement;

	public NavigationElement(NavigationElements navElement) {
		this.navElement = navElement;
		this.navOutcome = navElement.getNavOutcome();
		this.displayText = navElement.getLabelName();

	}

	/**
	 * Absolute path to .xhtml-File in src/main/webapp folder
	 */
	@Override
	public String getNavOutcome() {
				
		return this.navOutcome;
	}

	/**
	 * Display Text of the Navigation Element in the Header
	 */
	@Override
	public String getDisplayText() {

		return this.displayText;
	}

	@Override
	public NavigationElements getNavElementAsEnum() {
		return this.navElement;
	}

}
