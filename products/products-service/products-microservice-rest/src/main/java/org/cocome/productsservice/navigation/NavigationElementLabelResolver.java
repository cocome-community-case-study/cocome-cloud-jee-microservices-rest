package org.cocome.productsservice.navigation;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import java.io.Serializable;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * This Resolver takes the Navigation-Link and converts it to Label based on String.properties
 * @author Niko Benkler
 * @author Robert Heinrich
 *
 */
@SessionScoped
public class NavigationElementLabelResolver implements Serializable, ILabelResolver {
	private static final long serialVersionUID = 7607903884265925992L;
	private static final Logger LOG = Logger.getLogger(NavigationElementLabelResolver.class);
	
	
	
	private ResourceBundle strings;
	
	@PostConstruct
	private void initResourceBundle() {
		FacesContext ctx = FacesContext.getCurrentInstance();
	    Locale locale = ctx.getViewRoot().getLocale();
	    
	    /*
	     * Load local strings.properties!
	     */
		strings = ResourceBundle.getBundle(
	            "cocome.products.Strings", locale);
	}
	
	/**
	 * Get Label from navigation link
	 */
	@Override
	public String getLabel(String navOutcome) {
		String transformedOutcome = transformOutcomeToRef(navOutcome);
		
		StringBuilder sb = new StringBuilder();
		sb.append("navigation.");
		sb.append(transformedOutcome);
		sb.append(".label");
		return strings.getString(sb.toString());
	}

	private String transformOutcomeToRef(String navOutcome) {
		String transformedOutcome = navOutcome;
		LOG.debug(String.format("Transforming %s...", navOutcome));
		
		if (transformedOutcome.startsWith("/")) {
			transformedOutcome = transformedOutcome.substring(1);
			transformedOutcome = transformedOutcome.replaceAll("/+", "\\.");
			LOG.debug(String.format("Transformed string: %s", transformedOutcome));
		}
		return transformedOutcome;
	}

}
