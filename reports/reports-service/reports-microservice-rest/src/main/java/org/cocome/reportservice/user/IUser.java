package org.cocome.reportservice.user;

import java.util.HashMap;

public interface IUser {
	public String getUsername();
	
	
	
	public boolean hasPermissionString(String permission);
	
	public boolean hasPermission(IPermission permission);
	
	

	public HashMap<String, IPermission> getPermissions();
	public void addPermissions(HashMap<String, IPermission> permissions);

	
	
	
}
