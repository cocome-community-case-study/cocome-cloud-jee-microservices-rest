package org.cocome.frontendservice.events;

import javax.validation.constraints.NotNull;

import org.cocome.frontendservice.logindata.IUser;

import org.cocome.frontendservice.logindata.UserRole;
import org.cocome.frontendservice.navigation.NavigationViewStates;



public class LoginEvent {
	private IUser user;
	private UserRole role;
	private long storeID;
	
	public LoginEvent(@NotNull IUser user, @NotNull UserRole role, long storeID) {
		this.user = user;
		this.role = role;
		this.setStoreID(storeID);
	}
	
	public UserRole getRole() {
		return role;
	}
	
	public void setRole(@NotNull UserRole role) {
		this.role = role;
	}

	public IUser getUser() {
		return user;
	}

	public void setUser(@NotNull IUser user) {
		this.user = user;
	}
	
	public NavigationViewStates getRequestedView() {
		return role.associatedView();
	}

	public long getStoreID() {
		return storeID;
	}

	public void setStoreID(long storeID) {
		this.storeID = storeID;
	}
	
}
