package org.cocome.frontendservice.resolver;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.cocome.frontendservice.logindata.UserRole;
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
public class MicroserviceRedirecter implements IServiceRedirecter, Serializable {

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
		if (navOutcome == navElement.getNavOutcome()) {
			return;
		}

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
		case REPORTS:
			navMenu.changeStateTo(NavigationView.REPORTS_VIEW);
			navOutcome = navElement.getNavOutcome();
			break;

		default:
			navMenu.changeStateTo(NavigationView.DEFAULT_VIEW); // TODO Better default case?
			navOutcome = navElement.getNavOutcome();

			break;
		}

	}

	public String getNavOutcome() {
		/*
		 * This is an ugly workaround to redirect the cashier directly to his store
		 * service. This is done this way, because s/he should not click on "Store"
		 * navigation, as this rerenders the session of the storesservice and destroys
		 * the scope (delete current cashdesk nam, expressmode etc)
		 */
		if (navMenu.getCurrentUser().checkRole(UserRole.CASHIER)) {
			navMenu.changeStateTo(NavigationView.STORE_VIEW);
			return NavigationElements.STORE.getNavOutcome();
		}
		return navOutcome;
	}

	public NavigationElements getNavElement() {
		return element;
	}

}