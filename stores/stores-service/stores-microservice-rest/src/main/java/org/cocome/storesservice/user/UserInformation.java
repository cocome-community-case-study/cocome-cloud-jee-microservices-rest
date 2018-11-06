package org.cocome.storesservice.user;

import java.io.IOException;
import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * This class stores and process UserInformation
 * @author Niko Benkler
 * @author Robert Heinrich
 * @author Tobias Haßberg
 *
 */
@Named
@SessionScoped
public class UserInformation implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    
    private String userAsJSON;
    private IUser user;
    
   
    
    
	private static final Logger LOG = Logger.getLogger(DummyPermission.class);

    public IUser getUser() {
    	return user;
    }
	

	public String getUserAsJSON() {
		return userAsJSON;
	}

	public void setUserAsJSON(String userAsJSON) {
		this.userAsJSON = userAsJSON;
	}

	/**
	 * Processing JSON-String into userPOJO
	 * and creates new IUser out of it
	 */
	public void processUIInput() {
		
		try {
			
			UserPOJO userPOJO = new ObjectMapper().readValue(userAsJSON, UserPOJO.class);
			this.user = new DummyUser(userPOJO.getUsername());
			this.user.addPermissions(userPOJO.getPermissions());
		} catch (JsonParseException e) {
			LOG.debug("Error parsing JSON!:  " + e.getMessage());
		} catch (JsonMappingException e) {
			LOG.debug("Error parsing JSON!:  " +e.getMessage());
		} catch (IOException e) {
			LOG.debug("Error parsing JSON!:  " +e.getMessage());
		}
		
		
		
		LOG.debug("Processing User with name: " +user.getUsername() + " and Permissions: " + user.getPermissions());
		
	}
	
	public void logout() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
	}



	
	
	

}
