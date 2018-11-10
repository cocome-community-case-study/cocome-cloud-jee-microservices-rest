package org.cocome.productsservice.user;

import java.util.HashMap;

import javax.validation.constraints.NotNull;

import org.cocome.productsservice.navigation.NavigationView;



/**
 * This class represents a simple userdataObject for JSON transmission
 * 
 * @author nikolaus
 *
 */

public class UserDataTO {

	private String username;

	private HashMap<String, IPermission> permissions;

	private UserRole userRole;

	private NavigationView requestedView;

	private long storeID;

	public UserDataTO(@NotNull String userName, @NotNull HashMap<String, IPermission> permissions,
			@NotNull UserRole useRole, @NotNull NavigationView requestedView, long storeID) {
		this.username = userName;
		this.permissions = permissions;
		this.userRole = useRole;
		this.setRequestedView(requestedView);
		this.setStoreID(storeID);

	}

	/**
	 * Default Constructor!!! Only for JSON Deserialization!!! DO NOT USE
	 */
	public UserDataTO() {

	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public HashMap<String, IPermission> getPermissions() {
		return permissions;
	}

	public void setPermissions(HashMap<String, IPermission> permissions) {
		this.permissions = permissions;
	}

	public UserRole getUserRole() {
		return userRole;
	}

	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}

	public NavigationView getRequestedView() {
		return requestedView;
	}

	public void setRequestedView(NavigationView requestedView) {
		this.requestedView = requestedView;
	}

	public long getStoreID() {
		return storeID;
	}

	public void setStoreID(long storeID) {
		this.storeID = storeID;
	}

}