package org.cocome.frontendservice.navigation;

public enum NavigationElements {
	
	//TODO Links anpassen ggf. mit setttings.xml
	LOGIN("/login.xhtml", "Login"),
	DEFAULT("http://localhost:8580/frontendservice/faces/templates/defaultView.xhtml", "Default"),
	ENTERPRISE("http://localhost:8880/storesmicroservice/faces/enterpriseMain.xhtml", "Enterprise"),
	STORE("http://localhost:8880/storesmicroservice/faces/storeMain.xhtml","Store"),
	PRODUCTS("http://localhost:8580/frontendservice/faces/enterprise/show_products.xhtml","Products"),
	ORDERS("http://localhost:8580/frontendservice/faces/store/order_products.xhtml","Orders"),
	;
	
	private String navOutcome;
	private String labelName;
	
	private NavigationElements(String navOutcome, String labelName) {
		this.navOutcome = navOutcome;
		this.labelName = labelName;
	}

	public String getNavOutcome() {
		return navOutcome;
	}

	public String getLabelName() {
		return labelName;
	}
	
	

}
