package org.cocome.reportsservice.events;

import org.cocome.reportservice.user.IUser;
import org.cocome.reportservice.user.UserRole;
import org.cocome.reportsservice.navigation.NavigationView;

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
