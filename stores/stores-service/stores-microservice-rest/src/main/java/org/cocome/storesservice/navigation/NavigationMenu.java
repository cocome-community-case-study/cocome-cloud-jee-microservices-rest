package org.cocome.storesservice.navigation;

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
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import org.cocome.storesservice.events.ChangeViewEvent;
import org.cocome.storesservice.events.UserInformationProcessedEvent;
import org.cocome.storesservice.user.IUser;

/**
 * This class handles Navigation between sites for Store and Enterprise! It
 * determines the Navigation-Elements in the Header according to the users Role.
 * <br>
 * It store the currently logged in user
 * 
 * @author Niko Benkler
 * @author Robert Heinrich
 */
@Named
@SessionScoped
public class NavigationMenu implements INavigationMenu, Serializable {
	private static final long serialVersionUID = -6352541874730024270L;

	private static Map<NavigationView, List<INavigationElement>> STATE_MAP;

	private List<INavigationElement> elements;

	@Inject
	ILabelResolver labelResolver;

	private IUser currentUser;

	private NavigationView navigationState = NavigationView.DEFAULT_VIEW;

	/*
	 * Saves whether the Proxy-Frontend requests Store oder Enterprise-Service <br>
	 * true => Store <br> false => Enterprise <br>
	 */
	private boolean isStoreService = true;

	@PostConstruct
	private synchronized void initViewLists() {
		if (STATE_MAP != null) {
			return;
		}

		List<INavigationElement> enterpriseViewList = populateEnterpriseView();

		List<INavigationElement> storeViewList = populateStoreView();

		List<INavigationElement> cashpadViewList = populateCashpadView();

		List<INavigationElement> defaultViewList = populateDefaultView();

		STATE_MAP = new HashMap<>(NavigationView.values().length, 1);
		STATE_MAP.put(NavigationView.ENTERPRISE_VIEW, enterpriseViewList);
		STATE_MAP.put(NavigationView.STORE_VIEW, storeViewList);
		STATE_MAP.put(NavigationView.CASHPAD_VIEW, cashpadViewList);
		STATE_MAP.put(NavigationView.DEFAULT_VIEW, defaultViewList);
		STATE_MAP = Collections.unmodifiableMap(STATE_MAP);

	}

	/**
	 * Returns the currents Elements that will appear in Navigation-Header
	 */
	@Override
	public List<INavigationElement> getElements() {
		if (elements == null || elements.isEmpty()) {
			elements = new LinkedList<>(STATE_MAP.get(NavigationView.DEFAULT_VIEW));
		}
		return elements;
	}

	@Override
	public NavigationView getCurrentState() {
		return navigationState;
	}

	/**
	 * States whether the Proxy-Frontend requests Store oder Enterprise-Service <br>
	 * true => Store <br>
	 * false => Enterprise <br>
	 * 
	 * @return
	 */
	public boolean isStoreService() {
		return isStoreService;
	}

	/**
	 * Change state to new State. <br>
	 * States in this service can be EnterpriseView, StoreView, CashpasView <br>
	 * This will remove and add Links/Labels in the Navigation Bar based on the user
	 */
	@Override
	public String changeStateTo(@NotNull NavigationView newState) {

		navigationState = newState;
		elements = new LinkedList<>(STATE_MAP.get(navigationState));

		Iterator<INavigationElement> iterator = elements.iterator();

		// error Case
		if (currentUser == null) {
			navigationState = NavigationView.DEFAULT_VIEW;
			elements = STATE_MAP.get(NavigationView.DEFAULT_VIEW);

			FacesContext context = FacesContext.getCurrentInstance();
			String message = context.getApplication().evaluateExpressionGet(context,
					"#{strings['navigation.failed.no_user']}", String.class);
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));

			/*
			 * Determines whether user will be redirected to StoreService main or
			 * EnterpriseService Main in case of an Error
			 */
			if (isStoreService) {
				return NavigationElements.STORE_MAIN.getNavigationOutcome();
			} else {
				return NavigationElements.ENTERPRISE_MAIN.getNavigationOutcome();
			}

		}

		/*
		 * Removes Header field if the user does not have proper permission
		 */
		while (iterator.hasNext()) {
			INavigationElement element = iterator.next();
			if (element.getRequiredPermission() != null
					&& !currentUser.hasPermissionString(element.getRequiredPermission())) {
				iterator.remove();
			}
		}

		switch (newState) {
		case CASHPAD_VIEW:
			isStoreService = true;
			return NavigationElements.START_SALE.getNavigationOutcome();
		case STORE_VIEW:
			isStoreService = true;
			return NavigationElements.STORE_MAIN.getNavigationOutcome();
		case ENTERPRISE_VIEW:
			isStoreService = false;
			return NavigationElements.SHOW_ENTERPRISES.getNavigationOutcome();
		default:
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ouups Navigation error occured", null));
			return NavigationElements.ERROR.getNavigationOutcome();
		}
	}

	/**
	 * Change Header according to requested View
	 * 
	 * @param changeEvent
	 */
	public void observeChangeViewEvent(@Observes ChangeViewEvent changeEvent) {
		changeStateTo(changeEvent.getNewViewState());
	}

	/**
	 * Processes user data from frontend
	 * 
	 * @param event
	 */
	public void observe(@Observes UserInformationProcessedEvent event) {
		this.currentUser = event.getUser();
		this.navigationState = event.getRequestedNavViewState();

		/*
		 * StoreInformation also has an Observe ==> event.getStoreID is catched directly
		 * at this class
		 */

		changeStateTo(navigationState);
	}

	private List<INavigationElement> populateCashpadView() {
		List<INavigationElement> cashpadViewList = new LinkedList<>();
		cashpadViewList.add(new NavigationElement(NavigationElements.ENTERPRISE_MAIN, labelResolver));
		return cashpadViewList;
	}

	/*
	 * Navigation ELements for StoreView
	 */
	private List<INavigationElement> populateStoreView() {
		List<INavigationElement> storeViewList = new LinkedList<>();
		storeViewList.add(new NavigationElement(NavigationElements.START_SALE, labelResolver));
		storeViewList.add(new NavigationElement(NavigationElements.SHOW_STOCK, labelResolver));
		return storeViewList;
	}

	/*
	 * NavigationElements for EnterpriseView
	 */
	private List<INavigationElement> populateEnterpriseView() {
		List<INavigationElement> enterpriseViewList = new LinkedList<>();
		enterpriseViewList.add(new NavigationElement(NavigationElements.SHOW_ENTERPRISES, labelResolver));
		enterpriseViewList.add(new NavigationElement(NavigationElements.CREATE_ENTERPRISE, labelResolver));

		return enterpriseViewList;
	}

	/*
	 * Default View
	 */
	private List<INavigationElement> populateDefaultView() {
		List<INavigationElement> enterpriseViewList = new LinkedList<>();
		// TODO wohin wenn default?
		return enterpriseViewList;
	}

}
