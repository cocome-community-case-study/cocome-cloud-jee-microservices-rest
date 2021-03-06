package org.cocome.ordersservice.navigation;



/**
 * Represents an element inside an {@link INavigationMenu}.
 * 
 * @author Tobias Pöppke
 * @author Robert Heinrich
 */
public class NavigationElement implements INavigationElement {
	private String navOutcome;
	private String displayText;
	private ILabelResolver resolver;
	private String requiredPermission;
	
	/**
	 * 
	 * Constructor to initialize NavigationElement with NavigationElements (Enum) Variable
	 * @param navElement
	 * @param resolver
	 */
	public NavigationElement(NavigationElements navElement, ILabelResolver resolver) {
		this(navElement.getNavigationOutcome(), null, navElement.getNeededPermission(), resolver);
	}
	
	/**
	 * Constructor to initialize NavigationElement with basic Info
	 * @param navOutcome
	 * @param displayText
	 */
	public NavigationElement(String navOutcome, String displayText, String requiredPermission, ILabelResolver resolver) {
		this.navOutcome = navOutcome;
		this.displayText = displayText;
		this.resolver = resolver;
		this.requiredPermission = requiredPermission;
	}
	
	public NavigationElement(NavigationElements navElement, String displayText, String requiredPermission, ILabelResolver labelResolver) {
		this(navElement.getNavigationOutcome(), displayText, requiredPermission, labelResolver);
	}

	
	@Override
	public String getNavOutcome() {
		return navOutcome;
	}
	
	/**
	 * 
	 * @param navOutcome  Link to .xhtml-File that belongs to NavigationElement
	 */
	public void setNavOutcome(String navOutcome) {
		this.navOutcome = navOutcome;
	}
	
	/**
	 * Text to Display in Navigation-Bar
	 */
	@Override
	public String getDisplayText() {
		if ((displayText == null || displayText.equals("")) && resolver != null) {
			displayText = resolver.getLabel(navOutcome);
		}
		return displayText;
	}
	
	/**
	 * 
	 * @param displayText
	 */
	public void setDisplayText(String displayText) {
		this.displayText = displayText;
	}

	/**
	 * String containing Permission to use this Navigation Link
	 */
	@Override
	public String getRequiredPermission() {
		return requiredPermission;
	}

	public void setRequiredPermission(String requiredPermission) {
		this.requiredPermission = requiredPermission;
	}
}
