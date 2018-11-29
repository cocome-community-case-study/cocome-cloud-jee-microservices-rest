package org.cocome.ordersservice.events;

import org.cocome.ordersservice.navigation.NavigationView;
import org.cocome.ordersservice.user.IUser;
import org.cocome.ordersservice.user.UserRole;

/**
 * As soon as JSON-Object with user information coming from frontend is
 * processed, this events get emitted
 * 
 * @author Niko Benkler
 * @author Robert Heinrich
 *
 */
public class UserInformationProcessedEvent {

	private IUser user;
	private UserRole userRole;
	private NavigationView requestedNavViewState;
	private long storeID;

	public UserInformationProcessedEvent(IUser user, UserRole userRole, NavigationView requestedNavViewState,
			long storeID) {
		this.user = user;
		this.userRole = userRole;
		this.requestedNavViewState = requestedNavViewState;
		this.storeID = storeID;
	}

	public IUser getUser() {
		return user;
	}

	public UserRole getUserRole() {
		return userRole;
	}

	public NavigationView getRequestedNavViewState() {
		return requestedNavViewState;
	}

	public long getStoreID() {
		return storeID;
	}

}
