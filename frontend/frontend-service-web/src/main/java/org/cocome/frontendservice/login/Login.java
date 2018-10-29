package org.cocome.frontendservice.login;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import org.apache.log4j.Logger;
import org.cocome.frontendservice.events.LoginEvent;
import org.cocome.frontendservice.events.LogoutEvent;
import org.cocome.frontendservice.logindata.IAuthenticator;
import org.cocome.frontendservice.logindata.ICredential;
import org.cocome.frontendservice.logindata.ICredentialFactory;
import org.cocome.frontendservice.logindata.IUser;

import org.cocome.frontendservice.logindata.UserRole;
import org.cocome.frontendservice.navigation.NavigationElements;
import org.cocome.frontendservice.navigation.NavigationViewStates;
import org.cocome.frontendservice.resolver.MicroserviceRedirecter;



@Named
@SessionScoped
public class Login implements Serializable {
	private static final long serialVersionUID = 8680398263548700980L;

	@Inject
	IAuthenticator authenticator;
	
	@Inject
	ICredentialFactory credFactory;
	
	//TODO Inject
	MicroserviceRedirecter microserviceRedirecter;

	@Inject
	Event<LoginEvent> loginEvent;

	@Inject
	Event<LogoutEvent> logoutEvent;

	private String username = "";
	private ICredential password;
	private UserRole requestedRole = UserRole.ENTERPRISE_MANAGER;
	public static final long STORE_ID_NOT_SET = Long.MIN_VALUE;

	private IUser user = null;

	private long requestedStoreId = STORE_ID_NOT_SET;

	private boolean loggedIn = false;

	private static final Logger LOG = Logger.getLogger(Login.class);
	
	@PostConstruct
	private void init() {
		password = credFactory.createPlainPassword("");
		microserviceRedirecter  = new MicroserviceRedirecter();
	}

	public String getUserName() {
		return username;
	}

	public void setUserName(@NotNull String userName) {
		this.username = userName;
	}

	public String getPassword() {
		return password.getCredentialString();
	}

	public void setPassword(@NotNull String password) {
		this.password = credFactory.createPlainPassword(password);
	}

	public String login() {
		IUser storedUser = authenticator.checkCredential(username, password);
		String outcome;

		if (storedUser != null) {
			setLoggedIn(true);
			user = storedUser;
			loginEvent.fire(new LoginEvent(storedUser, requestedRole, requestedStoreId)); //TODO implment catching for loginInformation
			LOG.info(String.format("Successful login: username %s.", getUserName()));
			
			
			outcome = "templates/commonTemplate";
		} else {
			FacesContext context = FacesContext.getCurrentInstance();
			String message = context.getApplication().evaluateExpressionGet(context, "#{strings['login.failed.text']}",
					String.class);
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
			outcome = NavigationElements.LOGIN.getNavOutcome();
			LOG.warn(String.format("Failed login: username %s.", getUserName()));
		}
		return outcome;
	}

	public String logout() {
		username = "";
		password = credFactory.createPlainPassword("");
		requestedRole = UserRole.ENTERPRISE_MANAGER;
		requestedStoreId = 0;

		logoutEvent.fire(new LogoutEvent(user));
		user = null;

		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return NavigationElements.LOGIN.getNavOutcome();
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	public long getRequestedStoreId() {
		return requestedStoreId == STORE_ID_NOT_SET ? 0 : requestedStoreId;
	}

	public void setRequestedStoreId(long requestedStoreId) {
		this.requestedStoreId = requestedStoreId;
	}

	public UserRole getRequestedRole() {
		return requestedRole;
	}

	public void setRequestedRole(UserRole requestedRole) {
		this.requestedRole = requestedRole;
	}

	public boolean isStoreRequired() {
		if (requestedRole.associatedView() != NavigationViewStates.ENTERPRISE_VIEW) {
			return true;
		}
		return false;
	}

	@Produces
	@javax.enterprise.context.SessionScoped
	public IUser getUser() {
		return user;
	}

	public void setUser(IUser user) {
		this.user = user;
	}
}
