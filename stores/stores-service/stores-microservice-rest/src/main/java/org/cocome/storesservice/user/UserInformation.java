package org.cocome.storesservice.user;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;

import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;


@Named
@SessionScoped
public class UserInformation implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    private UserPOJO userPOJO;
    private String userAsJSON;
    
    
    


	


	
	
	public UserPOJO getUserPOJO() {
		return userPOJO;
	}

	public void setUserPOJO(UserPOJO userPOJO) {
		this.userPOJO = userPOJO;
	}

	public String getUserAsJSON() {
		return userAsJSON;
	}

	public void setUserAsJSON(String userAsJSON) {
		this.userAsJSON = userAsJSON;
	}

	public void processUIInput() {
		//userName already set through JSF
		try {
			userPOJO = new ObjectMapper().readValue(userAsJSON, UserPOJO.class);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void logout() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
	}



	
	
	

}
