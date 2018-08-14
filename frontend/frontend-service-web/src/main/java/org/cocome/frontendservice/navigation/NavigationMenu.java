package org.cocome.frontendservice.navigation;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import org.cocome.frontendservice.events.ChangeViewEvent;
import org.cocome.frontendservice.events.LoginEvent;
import org.cocome.frontendservice.events.LogoutEvent;
import org.cocome.frontendservice.logindata.IUser;



/**
 * Implements the navigation menu for the site. THe navigation Menu is a bar 
 * that contains different possibilities to switch the current view.
 * The selection depends on the current user and its permissions.
 * 
 * @author Niko Benkler
 * @author Tobias Haßberg
 * @author Robert Heinrich
 *
 */
@Named
@SessionScoped
public class NavigationMenu implements INavigationMenu, Serializable {
	private static final long serialVersionUID = -6352541874730024270L;
	
	
	private static Map<NavigationViewStates, List<INavigationElement>> STATE_MAP;
	
	//contains the current elements
	private List<INavigationElement> elements;

	@Inject
	ILabelResolver labelResolver;
	
	IUser currentUser;
	
	private NavigationViewStates navigationState = NavigationViewStates.DEFAULT_VIEW;
	

	@PostConstruct
	private synchronized void initViewLists() {
		if (STATE_MAP != null) {
			return;
		}
		
		List<INavigationElement> enterpriseViewList = populateEnterpriseView();
		
		List<INavigationElement> storeViewList = populateStoreView();
		
		List<INavigationElement> cashpadViewList = populateCashpadView();
		
		List<INavigationElement> defaultViewList = populateDefaultView();
		
		STATE_MAP = new HashMap<>(NavigationViewStates.values().length, 1);
		STATE_MAP.put(NavigationViewStates.ENTERPRISE_VIEW, enterpriseViewList); //
		STATE_MAP.put(NavigationViewStates.STORE_VIEW, storeViewList);
		STATE_MAP.put(NavigationViewStates.CASHPAD_VIEW, cashpadViewList);
		STATE_MAP.put(NavigationViewStates.DEFAULT_VIEW, defaultViewList);
		STATE_MAP = Collections.unmodifiableMap(STATE_MAP);
		
	}

	private List<INavigationElement> populateCashpadView() {
		List<INavigationElement> cashpadViewList = new LinkedList<>();
		cashpadViewList.add(new NavigationElement(NavigationElements.ENTERPRISE_MAIN, labelResolver));
		return cashpadViewList;
	}
	
	//List for Store View
	private List<INavigationElement> populateStoreView() {
		List<INavigationElement> storeViewList = new LinkedList<>();
		storeViewList.add(new NavigationElement(NavigationElements.START_SALE, labelResolver));
		storeViewList.add(new NavigationElement(NavigationElements.SHOW_STOCK, labelResolver));
		storeViewList.add(new NavigationElement(NavigationElements.STOCK_REPORT, labelResolver));
		storeViewList.add(new NavigationElement(NavigationElements.RECEIVE_PRODUCTS, labelResolver));
		return storeViewList;
	}
	
	//List for enterprise view
	private List<INavigationElement> populateEnterpriseView() {
		List<INavigationElement> enterpriseViewList = new LinkedList<>();
		enterpriseViewList.add(new NavigationElement(NavigationElements.SHOW_ENTERPRISES, labelResolver));
		enterpriseViewList.add(new NavigationElement(NavigationElements.CREATE_ENTERPRISE, labelResolver));
		enterpriseViewList.add(new NavigationElement(NavigationElements.SHOW_PRODUCTS, labelResolver));
		enterpriseViewList.add(new NavigationElement(NavigationElements.CREATE_PRODUCT, labelResolver));
		
		return enterpriseViewList;
	}
	
	//empty list
	private List<INavigationElement> populateDefaultView() {
		List<INavigationElement> enterpriseViewList = new LinkedList<>();
		return enterpriseViewList;
	}
	
	/* (non-Javadoc)
	 * @see org.cocome.cloud.shop.navigation.INavigationMenu#getElements()
	 */
	@Override
	public List<INavigationElement> getElements() {
		if (elements == null || elements.isEmpty()) {
			elements = new LinkedList<>(STATE_MAP.get(NavigationViewStates.DEFAULT_VIEW));
		}
		return elements;
	}

	@Override
	public String changeStateTo(@NotNull NavigationViewStates newState) {
		navigationState = newState;
		elements = new LinkedList<>(STATE_MAP.get(navigationState));
		
		Iterator<INavigationElement> iterator = elements.iterator();
		
		if (currentUser == null) {
			navigationState = NavigationViewStates.DEFAULT_VIEW;
			elements = STATE_MAP.get(NavigationViewStates.DEFAULT_VIEW);
			return NavigationElements.LOGIN.getNavigationOutcome();
		}
		
		while (iterator.hasNext()) {
			INavigationElement element = iterator.next();
			if (element.getRequiredPermission() != null &&
					!currentUser.hasPermissionString(element.getRequiredPermission())) {
				iterator.remove();
			}
		}
		
		switch (newState) {
			case CASHPAD_VIEW:
				return NavigationElements.START_SALE.getNavigationOutcome();
			case STORE_VIEW:
				return NavigationElements.STORE_MAIN.getNavigationOutcome();
			case ENTERPRISE_VIEW:
				return NavigationElements.SHOW_ENTERPRISES.getNavigationOutcome();
			default:
				return NavigationElements.LOGIN.getNavigationOutcome();
		}
	}

	@Override
	public NavigationViewStates getCurrentState() {
		return navigationState;
	}
	
	public void observeLoginEvent(@Observes LoginEvent loginEvent) {
		this.currentUser = loginEvent.getUser();
		changeStateTo(loginEvent.getRequestedView());
	}
	
	public void observeLogoutEvent(@Observes LogoutEvent logoutEvent) {
		this.currentUser = null;
		changeStateTo(NavigationViewStates.DEFAULT_VIEW);
	}
	
	public void observeChangeViewEvent(@Observes ChangeViewEvent changeEvent) {
		changeStateTo(changeEvent.getNewViewState());
	}
}
