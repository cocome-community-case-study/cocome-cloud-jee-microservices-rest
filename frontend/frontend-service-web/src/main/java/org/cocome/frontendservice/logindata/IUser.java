package org.cocome.frontendservice.logindata;

import java.util.HashMap;

public interface IUser {
	public String getUsername();
	
	public void setCredentials(ICredential credentials);
	
	public ICredential getCredentials();
	
	public boolean checkCredentials(ICredential credentials);
	
	public boolean hasPermissionString(String permission);
	
	public boolean hasPermission(IPermission permission);
	
	public void addPermission(IPermission permission);

	public HashMap<String, IPermission> getPermissions();

	UserRole getRole();

	public boolean checkRole(UserRole requestedRole);

	
	
	
}
