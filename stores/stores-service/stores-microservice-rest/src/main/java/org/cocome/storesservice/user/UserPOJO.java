package org.cocome.storesservice.user;

import java.util.HashMap;

/**
 * This class represents a simple user-object for JSON transmission
 * @author nikolaus
 *
 */

public class UserPOJO {
	
    private String username;
	
	private HashMap<String, IPermission> permissions;
	
	public UserPOJO(String userName, HashMap<String, IPermission> permissions) {
		this.username = userName;
		this.permissions = permissions;
	}
	
	/**
	 * Default constructor for JSON serialization
	 */
	public UserPOJO() {
		
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
	
	

}
