package org.cocome.frontendservice.navigation;

import org.apache.log4j.Logger;
import org.cocome.frontendservice.util.ResourceLoader;

/**
 * Possible Navigation Links ==> If an Service is added, just change this Enum
 * and add new NavigationELement in NavigationMenu
 * 
 * @author Niko Benkler
 * @author Robert Heinrich
 *
 */
public enum NavigationElements {
	
	


	/*
	 * 
	 * Navigation Outcomes are saved in Config.properties file and loaded via
	 * ResourceLoader class
	 */
	 
	LOGIN("org.cocome.login", "Login"),
	DEFAULT("org.cocome.default", "Default"),
	ENTERPRISE("org.cocome.enterpriseservice", "Enterprise"),
	STORE("org.cocome.storesservice", "Store"),
	PRODUCTS("org.cocome.productsservice", "Products"),
	ORDERS("org.cocome.ordersservice", "Orders"),
	REPORTS("org.cocome.reportsservice", "Reports");
	

	private String navOutcome;
	private String labelName;
	private static final Logger LOG = Logger.getLogger(NavigationMenu.class);

	private NavigationElements(String navOutcome, String labelName) {
		this.navOutcome = navOutcome;
		this.labelName = labelName;
	}

	public String getNavOutcome() {
		String outcome = ResourceLoader.getProperty(navOutcome);
		LOG.debug("Requesting outcome for " + labelName +". Outcome is: " +outcome);
		return outcome;
	}

	public String getLabelName() {
		return labelName;
	}
	
	

}
