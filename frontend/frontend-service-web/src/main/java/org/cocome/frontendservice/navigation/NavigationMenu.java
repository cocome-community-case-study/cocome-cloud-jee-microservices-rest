package org.cocome.frontendservice.navigation;

import java.io.IOException;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.cocome.frontendservice.events.LoginEvent;
import org.cocome.frontendservice.events.LogoutEvent;
import org.cocome.frontendservice.logindata.IUser;
import org.cocome.frontendservice.logindata.UserDataTO;
import org.cocome.frontendservice.logindata.UserRole;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * This class stores the current User information, transform and updates the
 * user information and requested views in JSON format. Further, it builds the
 * Navigation Menu for the main frontned
 * 
 * @author Niko Benkler
 * @author Robert Heinrich
 *
 */
@Named
@SessionScoped
public class NavigationMenu implements INavigationMenu, Serializable {

	private static final Logger LOG = Logger.getLogger(NavigationMenu.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static List<INavigationElement> navElements; // Might need to be split in different Lists according to Role

	private final NavigationElement enterpriseNavItem = new NavigationElement(NavigationElements.ENTERPRISE);
	private final NavigationElement storeNavItem = new NavigationElement(NavigationElements.STORE);
	private final NavigationElement productsNavItem = new NavigationElement(NavigationElements.PRODUCTS);
	private final NavigationElement ordersNavItem = new NavigationElement(NavigationElements.ORDERS);
	private final NavigationElement reportsNavItem = new NavigationElement(NavigationElements.REPORTS);
	// private final NavigationElement loginNavItem = new
	// NavigationElement(NavigationElements.LOGIN);
	// private final NavigationElement defaultNavItem = new
	// NavigationElement(NavigationElements.DEFAULT);

	private IUser currentUser;
	private UserRole userRole;
	private long storeID;
	private NavigationView currentState;

	private UserDataTO userDataTO;

	/*
	 * UserDataTO as JSON
	 */
	private String userDataAsJSON;

	public UserRole getUserRole() {
		return userRole;
	}

	public long getStoreID() {
		return storeID;
	}

	@Override
	public List<INavigationElement> getNavigationElements() {
		return navElements;
	}

	@Override
	public NavigationView getCurrentState() {
		return currentState;
	}

	public String getCurrentUserAsJSON() {
		return userDataAsJSON;
	}

	@PostConstruct
	private void initElements() {

		currentState = NavigationView.LOGIN;

		navElements = new LinkedList<>();
		navElements.add(enterpriseNavItem);

		navElements.add(storeNavItem);

		navElements.add(productsNavItem);

		navElements.add(ordersNavItem);
		navElements.add(reportsNavItem);

	}

	/**
	 * Change state of Navigation redirection when user selects other Service
	 * userDataAsJSON has to be recalculated
	 */
	@Override
	public void changeStateTo(NavigationView requestedState) {
		this.currentState = requestedState;

		this.userDataTO = new UserDataTO(currentUser.getUsername(), currentUser.getPermissions(), userRole, requestedState,
				storeID);
		try {
			userDataAsJSON = new ObjectMapper().writeValueAsString(userDataTO);
		} catch (JsonGenerationException e) {
			LOG.debug("Error while transforming userData: " + e.getMessage());
		} catch (JsonMappingException e) {
			LOG.debug("Error while transforming userData: " + e.getMessage());
		} catch (IOException e) {
			LOG.debug("Error while transforming userData: " + e.getMessage());
		}

		LOG.debug("Serialization of: " + userDataTO.getUsername() + " and Permissions: " + userDataTO.getPermissions()
				+ " and UserRole: " + userDataTO.getUserRole());
		LOG.debug("Serializeed Value: " + userDataAsJSON);
		

	}

	public void observeLoginEvent(@Observes LoginEvent loginEvent) {
		this.currentUser = loginEvent.getUser();
		this.userRole = loginEvent.getRole();
		this.storeID = loginEvent.getStoreID();
		changeStateTo(NavigationView.DEFAULT_VIEW);

	}

	public void observeLogoutEvent(@Observes LogoutEvent logoutEvent) {
		this.currentUser = null;
		this.userDataAsJSON = null;
		this.userDataTO = null;
		currentState = NavigationView.DEFAULT_VIEW;
	}

	public IUser getCurrentUser() {
		return currentUser;
	}

}
