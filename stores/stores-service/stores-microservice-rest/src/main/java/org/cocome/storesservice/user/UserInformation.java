package org.cocome.storesservice.user;

import java.io.IOException;
import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Event;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.cocome.storesservice.events.UserInformationProcessedEvent;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * This class holds the UserInformation of the current Session <br>
 * It processes the Input-Messages from the Proxy-Frontend and fires
 * (distributes) the content to each Bean that requires any Information about
 * the User, its permission, the requested store or view
 * 
 * @author Niko Benkler
 * @author Robert Heinrich
 * @author Tobias Haßberg
 *
 */
@Named
@SessionScoped
public class UserInformation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	Event<UserInformationProcessedEvent> userInfoProcessedEvent;

	private String userAsJSON;
	private IUser user = null;
	private UserRole userRole = null;

	private static final Logger LOG = Logger.getLogger(DummyPermission.class);

	// TODO was davon ist wichtig?!
	public IUser getUser() {
		return user;
	}

	public boolean isLoggedIn() {
		return user != null;
	}

	public String getUserAsJSON() {
		return userAsJSON;
	}

	public void setUserAsJSON(String userAsJSON) {
		this.userAsJSON = userAsJSON;
	}

	public UserRole getUserRole() {
		return this.userRole;
	}

	/**
	 * Processing JSON-String into userPOJO and creates new IUser out of it
	 */
	public void processUIInput() {

		try {

			UserDataTO userPOJO = new ObjectMapper().readValue(userAsJSON, UserDataTO.class);
			this.user = new DummyUser(userPOJO.getUsername());
			this.user.addPermissions(userPOJO.getPermissions());

			LOG.debug("Deserialization of  UserPOJO with name: " + userPOJO.getUsername() + " and Permissions: "
					+ userPOJO.getPermissions() + " and UserRole: " + userPOJO.getUserRole() + "successfull");

			userInfoProcessedEvent.fire(new UserInformationProcessedEvent(this.user, userPOJO.getUserRole(),
					userPOJO.getRequestedView(), userPOJO.getStoreID()));

		} catch (JsonParseException e) {
			// TODO show error Page
			LOG.error("Error parsing JSON!:  " + e.getMessage());
		} catch (JsonMappingException e) {
			LOG.error("Error parsing JSON!:  " + e.getMessage());
		} catch (IOException e) {
			LOG.error("Error parsing JSON!:  " + e.getMessage());
		}

	}

	public void logout() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
	}

}
