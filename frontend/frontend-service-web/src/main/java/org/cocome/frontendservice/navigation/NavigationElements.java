package org.cocome.frontendservice.navigation;

public enum NavigationElements {
	
	//TODO Links anpassen ggf. mit setttings.xml
	LOGIN("/login.xhtml", "Login"),
	DEFAULT("http://localhost:8580/frontendservice/faces/templates/defaultView.xhtml", "Default"),
	ENTERPRISE("http://localhost:8880/storesmicroservice/faces/enterprise_main.xhtml", "Enterprise"),
	STORE("http://localhost:8880/storesmicroservice/faces/store_main.xhtml","Store"),
	PRODUCTS("http://localhost:8980/productsmicroservice/faces/products_main.xhtml","Products"),
	ORDERS("http://localhost:8780/ordersmicroservice/faces/orders_main.xhtml","Orders"),
	REPORTS("http://localhost:8680/reportsmicroservice/faces/reports_main.xhtml","Reports");
	
	
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
