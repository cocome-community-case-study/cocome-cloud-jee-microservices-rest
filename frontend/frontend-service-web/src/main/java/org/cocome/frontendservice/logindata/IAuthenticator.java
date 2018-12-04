package org.cocome.frontendservice.logindata;

import javax.ejb.Local;

@Local
public interface IAuthenticator {	
	
	/**
	 * 
	 * 
	 * @param user
	 * @return
	 */
	public boolean checkCredentials(IUser user);
	
	/**
	 * Checks the given user credentials and returns the user as a result if the
	 * credentials matched. Otherwise null is returned. 
	 * 
	 * @param username
	 * @param credential
	 * @param requestedRole 
	 * @return the user object or null if check failed
	 */
	public IUser checkCredential(String username, ICredential credential, UserRole requestedRole);
	
	/**
	 * 
	 * 
	 * @param user
	 * @param permission
	 * @return
	 */
	public boolean checkHasPermission(IUser user, IPermission permission);

}
