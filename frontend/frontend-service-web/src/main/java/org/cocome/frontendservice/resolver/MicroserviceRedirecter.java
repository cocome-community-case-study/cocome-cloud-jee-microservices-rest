package org.cocome.frontendservice.resolver;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.cocome.frontendservice.login.Login;
import org.cocome.frontendservice.navigation.INavigationMenu;
import org.cocome.frontendservice.navigation.NavigationElement;
import org.cocome.frontendservice.navigation.NavigationElements;
import org.cocome.frontendservice.navigation.NavigationView;

/**
 * Delegates new NavigationView to NavigationMenu and handles navigation
 * destinations (remote server URL's) for this View
 * 
 * @author Niko Benkler
 * @author Robert Heinrich
 *
 */
@Named
@SessionScoped
public class MicroserviceRedirecter implements Serializable {

	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	@Inject
	INavigationMenu navMenu;

	private String navOutcome;

	private NavigationElements element;

	@PostConstruct
	public void init() {

		
		navOutcome = NavigationElements.DEFAULT.getNavOutcome();

	}

	public void redirect(NavigationElement navElement) {

		switch (navElement.getNavElementAsEnum()) {
		case DEFAULT:
			navMenu.changeStateTo(NavigationView.DEFAULT_VIEW);
			navOutcome = navElement.getNavOutcome();
			break;
		case ENTERPRISE:
			navMenu.changeStateTo(NavigationView.ENTERPRISE_VIEW);
			navOutcome = navElement.getNavOutcome();
			break;
		case ORDERS:
			navMenu.changeStateTo(NavigationView.ORDERS_VIEW);
			navOutcome = navElement.getNavOutcome();
			break;
		case PRODUCTS:
			navMenu.changeStateTo(NavigationView.PRODUCTS_VIEW);
			navOutcome = navElement.getNavOutcome();
			break;
		case STORE:
			navMenu.changeStateTo(NavigationView.STORE_VIEW);
			navOutcome = navElement.getNavOutcome();
			break;
		default:
			navMenu.changeStateTo(NavigationView.DEFAULT_VIEW); // TODO Better default case?
			navOutcome = navElement.getNavOutcome();

			break;
		}

	}

	public String getNavOutcome() {
		return navOutcome;
	}

	public NavigationElements getNavElement() {
		return element;
	}

}