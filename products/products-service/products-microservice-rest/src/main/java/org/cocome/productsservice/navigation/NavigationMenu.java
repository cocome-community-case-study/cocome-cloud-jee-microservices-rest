package org.cocome.productsservice.navigation;

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

import org.cocome.productsservice.events.UserInformationProcessedEvent;
import org.cocome.productsservice.user.IUser;

/**
 * This class handles Navigation between sites within ProductsService! It
 * determines the Navigation-Elements in the Header according to the users Role.
 * <br>
 * 
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

	private NavigationView navigationState = NavigationView.DEFAULT_VIEW;

	@Inject
	ILabelResolver labelResolver;

	private IUser currentUser;

	@PostConstruct
	private synchronized void initViewLists() {
		if (STATE_MAP != null) {
			return;
		}

		List<INavigationElement> productViewList = populateProductView();
		List<INavigationElement> defaultViewList = populateDefaultView();

		STATE_MAP = new HashMap<>(NavigationView.values().length, 1);
		STATE_MAP.put(NavigationView.PRODUCTS_VIEW, productViewList);
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

	/**
	 * Change state to new State. <br>
	 * This will remove and add Links/Labels in the Navigation Bar based on the user
	 * that is Logged in<br>
	 * For now, this service provides only one View
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
			return NavigationElements.PRODUCTS_MAIN.getNavigationOutcome();
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

		/*
		 * For now only one possible State. <br> We provide this functionality in case
		 * we want to have different Headers within this microservice
		 */
		switch (newState) {
		case PRODUCTS_VIEW:
			return NavigationElements.PRODUCTS_MAIN.getNavigationOutcome();

		default:
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ouups Navigation error occured", null));
			return NavigationElements.ERROR.getNavigationOutcome();
		}

	}

	/**
	 * Processes user data from frontend
	 * 
	 * @param event
	 */
	public void observe(@Observes UserInformationProcessedEvent event) {
		this.currentUser = event.getUser();
		changeStateTo(event.getRequestedNavViewState());

	}

	/*
	 * NavigationElements for EnterpriseView
	 */
	private List<INavigationElement> populateProductView() {
		List<INavigationElement> enterpriseViewList = new LinkedList<>();
		enterpriseViewList.add(new NavigationElement(NavigationElements.CREATE_PRODUCT, labelResolver));
		enterpriseViewList.add(new NavigationElement(NavigationElements.SHOW_PRODUCTS, labelResolver));
		enterpriseViewList.add(new NavigationElement(NavigationElements.SHOW_SUPPLIERS, labelResolver));
		enterpriseViewList.add(new NavigationElement(NavigationElements.CREATE_SUPPLIER, labelResolver));

		return enterpriseViewList;
	}

	/*
	 * Default View
	 */
	private List<INavigationElement> populateDefaultView() {
		List<INavigationElement> defaultView = new LinkedList<>();

		return defaultView;
	}

}
