package org.cocome.frontendservice.navigation;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
	private static final long serialVersionUID = 1L;

	//Header for frontend Navigation
	private List<INavigationElement> headerNavigationElements; 

	//Map that saves possible Headers according to their role
	private static Map<UserRole, List<INavigationElement>> HEADERS;

	//all possible NavigationItems
	private final NavigationElement enterpriseNavItem = new NavigationElement(NavigationElements.ENTERPRISE);
	private final NavigationElement storeNavItem = new NavigationElement(NavigationElements.STORE);
	private final NavigationElement productsNavItem = new NavigationElement(NavigationElements.PRODUCTS);
	private final NavigationElement ordersNavItem = new NavigationElement(NavigationElements.ORDERS);
	private final NavigationElement reportsNavItem = new NavigationElement(NavigationElements.REPORTS);

	//user and state data
	private IUser currentUser;
	private UserRole userRole;
	private long storeID;
	private NavigationView currentState;
	private UserDataTO userDataTO;

	//user data in JSON format 
	private String userDataAsJSON;

	@PostConstruct
	private void initElements() {

		currentState = NavigationView.LOGIN;
		headerNavigationElements = new LinkedList<>();
		HEADERS = new HashMap<>(UserRole.values().length, 1);

		//init all different header possibilities
		List<INavigationElement> adminHeaders = populateAdminHeaders();
		List<INavigationElement> cashierHeaders = populateCashierHeaders();
		List<INavigationElement> stockManageHeaders = populateStockManagerHeaders();
		List<INavigationElement> enterpriseManagerHeaders = populateEnterpriseManagerHeaders();
		List<INavigationElement> storeManagerHeaders = populateStoreManagerHeaders();
		List<INavigationElement> databaseManagerHeaders = populateDatabaseManagerHeaders();
		List<INavigationElement> defaultHeaders = populateDefaultHeaders();

		HEADERS.put(UserRole.ADMIN, adminHeaders);
		HEADERS.put(UserRole.CASHIER, cashierHeaders);
		HEADERS.put(UserRole.DATABASE_MANAGER, databaseManagerHeaders);
		HEADERS.put(UserRole.ENTERPRISE_MANAGER, enterpriseManagerHeaders);
		HEADERS.put(UserRole.STOCK_MANAGER, stockManageHeaders);
		HEADERS.put(UserRole.STORE_MANAGER, storeManagerHeaders);
		HEADERS.put(UserRole.DEFAULT, defaultHeaders);

		headerNavigationElements = HEADERS.get(UserRole.DEFAULT); // init navElements

	}

	public UserRole getUserRole() {
		return userRole;
	}

	public long getStoreID() {
		return storeID;
	}

	@Override
	public List<INavigationElement> getNavigationElements() {
		return headerNavigationElements;
	}

	@Override
	public NavigationView getCurrentState() {
		return currentState;
	}

	public String getCurrentUserAsJSON() {
		return userDataAsJSON;
	}

	/**
	 * Change state of Navigation redirection when user selects other Service
	 * userDataAsJSON has to be recalculated
	 */
	@Override
	public void changeStateTo(NavigationView requestedState) {
		this.currentState = requestedState;

		this.userDataTO = new UserDataTO(currentUser.getUsername(), currentUser.getPermissions(), userRole,
				requestedState, storeID);
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
		this.headerNavigationElements = HEADERS.get(loginEvent.getRole());
		changeStateTo(NavigationView.DEFAULT_VIEW);

	}

	public void observeLogoutEvent(@Observes LogoutEvent logoutEvent) {
		this.currentUser = null;
		this.userDataAsJSON = null;
		this.userDataTO = null;
		currentState = NavigationView.DEFAULT_VIEW;
	}

	@Override
	public IUser getCurrentUser() {
		return currentUser;
	}

	private List<INavigationElement> populateDefaultHeaders() {
		//currently no default site
		return new LinkedList<>();
	}

	private List<INavigationElement> populateDatabaseManagerHeaders() {
		//currently no use case for a database manager
		return new LinkedList<>();
	}

	private List<INavigationElement> populateStoreManagerHeaders() {
		List<INavigationElement> navElements = new LinkedList<>();
		navElements.add(storeNavItem);
		navElements.add(ordersNavItem);
		navElements.add(reportsNavItem);
		return navElements;
	}

	private List<INavigationElement> populateEnterpriseManagerHeaders() {
		List<INavigationElement> navElements = new LinkedList<>();
		navElements.add(enterpriseNavItem);
		navElements.add(productsNavItem);
		navElements.add(reportsNavItem);
		return navElements;
	}

	private List<INavigationElement> populateStockManagerHeaders() {
		List<INavigationElement> navElements = new LinkedList<>();
		navElements.add(storeNavItem);
		navElements.add(ordersNavItem);
		navElements.add(reportsNavItem);
		return navElements;
	}

	private List<INavigationElement> populateCashierHeaders() {
		//We do not want the cashier to navigate!
		return new LinkedList<>();
	}

	private List<INavigationElement> populateAdminHeaders() {
		List<INavigationElement> navElements = new LinkedList<>();
		navElements.add(enterpriseNavItem);
		navElements.add(storeNavItem);
		navElements.add(productsNavItem);
		navElements.add(ordersNavItem);
		navElements.add(reportsNavItem);
	
		return navElements;
	}

}
