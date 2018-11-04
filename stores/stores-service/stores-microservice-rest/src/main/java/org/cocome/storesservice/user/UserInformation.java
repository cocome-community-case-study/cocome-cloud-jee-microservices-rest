package org.cocome.storesservice.user;

import java.io.Serializable;
import java.util.HashMap;

import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;


@Named
@SessionScoped
public class UserInformation implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    private String username = "init";
    int i =0;
    private String output="start";
	
	
	
	public String getOutput() {
		return output;
	}



	public void setOutput(String output) {
		this.output = output;
	}



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
	
	public void processUIInput() {
		output= username + "Versuch "+ i; 
		i++;
		
	}
	
	public void logout() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
	}



	public void setPermissions(HashMap<String, IPermission> permissions) {
		this.permissions = permissions;
	}
	
	

}
