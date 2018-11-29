package org.cocome.frontendservice.logindata;

import java.util.HashMap;

import org.apache.log4j.Logger;

/**
 * Combines credential with user
 * @author Niko Benkler
 * @author Robert Heinrich
 *
 */
public class DummyUser implements IUser {
	
	private static final Logger LOG = Logger.getLogger(DummyUser.class);
	
	private String username;
	
	private ICredential credentials;
	
	private HashMap<String, IPermission> permissions;
	
	
	
	

	public DummyUser(String username, String password) {
		this(username, new PlainCredential(password));
	}

	public DummyUser(String username, ICredential credentials) {
		this.username = username;
		this.credentials = credentials;
		this.permissions = new HashMap<String, IPermission>();
	
		
	}
	
	
	
	
	
	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public void setCredentials(ICredential credentials) {
		this.credentials = credentials;
	}

	@Override
	public ICredential getCredentials() {
		return credentials;
	}

	@Override
	public boolean checkCredentials(ICredential credentials) {
		return this.credentials.isMatching(credentials);
	}

	@Override
	public boolean hasPermission(IPermission permission) {
		LOG.debug("Checking for permission " + permission.getName() + " in " + permissions.toString());
		IPermission stored = null;
		if (permission != null) {
			stored = permissions.get(permission.getName());
		}
		return stored != null;
	}

	@Override
	public void addPermission(IPermission permission) {
		if (permission != null) {
			permissions.put(permission.getName(), permission);
		}
	}

	@Override
	public boolean hasPermissionString(String permission) {
		IPermission checkPermission = new DummyPermission(permission);
		return hasPermission(checkPermission);
	}
	
	@Override
	public HashMap<String, IPermission> getPermissions() {
		return permissions;
	}
	
	

}
