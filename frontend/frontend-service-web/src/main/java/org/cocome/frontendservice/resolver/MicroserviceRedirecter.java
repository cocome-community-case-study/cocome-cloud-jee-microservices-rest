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
import org.cocome.frontendservice.navigation.NavigationViewStates;

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

		navMenu.changeStateTo(NavigationViewStates.DEFAULT_VIEW);
	    navOutcome = NavigationElements.DEFAULT.getNavOutcome();
		
		 
	}

	public void redirect(NavigationElement navElement) { // TODO hier überall noch die Parameter anhängen?!
		
		

		switch (navElement.getNavElementAsEnum()) {
		case DEFAULT:
			navMenu.changeStateTo(NavigationViewStates.DEFAULT_VIEW);
			navOutcome = navElement.getNavOutcome();
			break;
		case ENTERPRISE:
			navMenu.changeStateTo(NavigationViewStates.ENTERPRISE_VIEW);
			navOutcome = navElement.getNavOutcome();
			break;
		case ORDERS:
			navMenu.changeStateTo(NavigationViewStates.ORDERS_VIEW);
			navOutcome = navElement.getNavOutcome();
			break;
		case PRODUCTS:
			navMenu.changeStateTo(NavigationViewStates.PRODUCTS_VIEW);
			navOutcome = navElement.getNavOutcome();
			break;
		case STORE:
			navMenu.changeStateTo(NavigationViewStates.STORE_VIEW);
			navOutcome = navElement.getNavOutcome();
			break;
		default:
			navMenu.changeStateTo(NavigationViewStates.DEFAULT_VIEW); //TODO Better default case?
			navOutcome = navElement.getNavOutcome();
			
			break;
		}

		//return navOutcome;
	}

	public String getNavOutcome() {
		return navOutcome;
	}

	public NavigationElements getNavElement() {
		return element;
	}

}