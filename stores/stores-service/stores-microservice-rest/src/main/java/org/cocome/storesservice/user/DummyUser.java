package org.cocome.storesservice.user;

import java.util.HashMap;

import org.apache.log4j.Logger;

/**
 * Basic Class representing a User with UserName and serveral Permission
 * @author Niko Benkler
 * @author Robert Heinrich
 *
 */
public class DummyUser implements IUser {

	private static final Logger LOG = Logger.getLogger(DummyUser.class);

	private String username;

	private HashMap<String, IPermission> permissions;

	public DummyUser(String username) {
		this.username = username;
		this.permissions = new HashMap<String, IPermission>();

	}

	@Override
	public String getUsername() {
		return username;
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

	@Override
	public void addPermissions(HashMap<String, IPermission> permissions) {
		this.permissions.putAll(permissions);
	}

}
