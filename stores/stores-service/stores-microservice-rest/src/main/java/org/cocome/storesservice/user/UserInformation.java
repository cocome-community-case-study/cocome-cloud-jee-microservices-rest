package org.cocome.storesservice.user;

import java.io.Serializable;
import java.util.HashMap;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;


@Named
@SessionScoped
public class UserInformation implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    private String username;
	
	
	
	private HashMap<String, IPermission> permissions;



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
