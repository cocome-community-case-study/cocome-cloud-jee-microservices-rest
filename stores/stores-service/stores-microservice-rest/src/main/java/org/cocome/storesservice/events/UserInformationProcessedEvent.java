package org.cocome.storesservice.events;

import org.cocome.storesservice.navigation.NavigationView;
import org.cocome.storesservice.user.IUser;
import org.cocome.storesservice.user.UserRole;

public class UserInformationProcessedEvent {
	
	private IUser user;
	private UserRole userRole;
	private NavigationView requestedNavViewState;
	private long storeID;
	
	public UserInformationProcessedEvent(IUser user, UserRole userRole, NavigationView requestedNavViewState, long storeID) {
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
