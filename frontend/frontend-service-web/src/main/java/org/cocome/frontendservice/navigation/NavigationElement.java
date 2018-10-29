package org.cocome.frontendservice.navigation;




public class NavigationElement implements INavigationElement {
	
	private String navOutcome;
	private String displayText;
	private NavigationElements navElement;

	
	public NavigationElement(NavigationElements navElement) {
		this.navElement = navElement;
		this.navOutcome = navElement.getNavOutcome();
		this.displayText = navElement.getLabelName();
		
	}
	
	
	
	
	@Override
	public String getNavOutcome() {
		
		return this.navOutcome;
	}

	@Override
	public String getDisplayText() {
		
		return this.displayText;
	}




	@Override
	public NavigationElements getNavElementAsEnum() {
		return this.navElement;
	}

}
