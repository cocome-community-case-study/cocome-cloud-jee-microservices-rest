package org.cocome.frontendservice.navigation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.inject.Named;

import org.cocome.frontendservice.events.LoginEvent;
import org.cocome.frontendservice.events.LogoutEvent;
import org.cocome.frontendservice.logindata.IUser;


@Named
@SessionScoped
public class NavigationMenu implements INavigationMenu, Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private NavigationViewStates currentState;
	private static List<INavigationElement> navElements; //Might need to be split in different Lists according to Role
	
	private final NavigationElement enterpriseNavItem = new NavigationElement(NavigationElements.ENTERPRISE);
	private final NavigationElement storeNavItem = new NavigationElement(NavigationElements.STORE);
	private final NavigationElement productsNavItem = new NavigationElement(NavigationElements.PRODUCTS);
	private final NavigationElement ordersNavItem = new NavigationElement(NavigationElements.ORDERS);
	//private final NavigationElement loginNavItem = new NavigationElement(NavigationElements.LOGIN);
	//private final NavigationElement defaultNavItem = new NavigationElement(NavigationElements.DEFAULT);
	
	private IUser currentUser;
	
	
	
	@PostConstruct
	private void initElements() {
		
	 currentState = NavigationViewStates.LOGIN;

	  navElements = new LinkedList<>();
	   navElements.add(enterpriseNavItem);
		
	   navElements.add(storeNavItem);
		
	   navElements.add(productsNavItem);
		
	   navElements.add(ordersNavItem);
		
	}
	
	
	
	@Override
	public List<INavigationElement> getNavigationElements() {
		return navElements;
	}
	
	
	
	@Override
	public void changeStateTo(NavigationViewStates newState) {
		
		this.currentState = newState;

		
	}
	
	
	@Override
	public NavigationViewStates getCurrentState() {
		return currentState;
	}
	
	public void observeLoginEvent(@Observes LoginEvent loginEvent) {
		this.currentUser = loginEvent.getUser();
		changeStateTo(NavigationViewStates.DEFAULT_VIEW);
	}
	
	public void observeLogoutEvent(@Observes LogoutEvent logoutEvent) {
		this.currentUser = null;
		changeStateTo(NavigationViewStates.DEFAULT_VIEW);
	}



	public IUser getCurrentUser() {
		return currentUser;
	}
	
	





	



	

	

}
