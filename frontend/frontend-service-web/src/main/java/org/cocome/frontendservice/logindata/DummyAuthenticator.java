package org.cocome.frontendservice.logindata;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;

import org.apache.log4j.Logger;

@ApplicationScoped
public class DummyAuthenticator implements IAuthenticator {
	private static final Logger LOG = Logger.getLogger(DummyAuthenticator.class);
	
	private Map<String, IUser> users = Collections.synchronizedMap(new HashMap<String, IUser>());
	
	private void createDummyUsers() {
		IPermission adminPermission = new DummyPermission("admin");
		IPermission enterprisePermission = new DummyPermission("enterprise manager");
		IPermission storePermission = new DummyPermission("store manager");
		IPermission stockPermission = new DummyPermission("stock manager");
		IPermission cashierPermission = new DummyPermission("cashier");
		IPermission databasePermission = new DummyPermission("database manager");
		
		ICredential adminCredentials = new PlainCredential("admin");
		ICredential enterpriseCredentials = new PlainCredential("enterprise");
		ICredential storeCredentials = new PlainCredential("store");
		ICredential cashierCredentials = new PlainCredential("cashier");
		
		IUser admin = new DummyUser("admin", adminCredentials);
		admin.addPermission(cashierPermission);
		admin.addPermission(stockPermission);
		admin.addPermission(storePermission);
		admin.addPermission(adminPermission);
		admin.addPermission(enterprisePermission);
		admin.addPermission(databasePermission);
		
		IUser enterpriseManager = new DummyUser("enterprisemanager", enterpriseCredentials);
		enterpriseManager.addPermission(enterprisePermission);
		
		IUser storeManager = new DummyUser("storemanager", storeCredentials);
		storeManager.addPermission(storePermission);
		storeManager.addPermission(stockPermission);
		storeManager.addPermission(cashierPermission);
		
		IUser stockManager = new DummyUser("stockmanager", storeCredentials);
		stockManager.addPermission(stockPermission);
		stockManager.addPermission(cashierPermission);
		
		IUser cashier = new DummyUser("cashier", cashierCredentials);
		cashier.addPermission(cashierPermission);
		
		users.put(admin.getUsername(), admin);
		users.put(enterpriseManager.getUsername(), enterpriseManager);
		users.put(storeManager.getUsername(), storeManager);
		users.put(stockManager.getUsername(), stockManager);
		users.put(cashier.getUsername(), cashier);
	}
	
	@PostConstruct
	public void initAuthenticator() {
		LOG.debug("Initializing users...");
		createDummyUsers();
	}
	
	@Override
	public boolean checkCredentials(IUser user) {
		LOG.debug("Checking credentials of user...");
		return checkCredential(user.getUsername(), user.getCredentials()) != null;
	}

	@Override
	public boolean checkHasPermission(IUser user, IPermission permission) {
		LOG.debug("Checking permissions of user " + user.getUsername());
		if (user != null && permission != null) {
			IUser storedUser = users.get(user.getUsername());
			return storedUser != null ? storedUser.hasPermission(permission) : false;
		}
		return false;
	}

	@Override
	public IUser checkCredential(String username, ICredential credential) {
		if (credential != null && username != null && !username.trim().isEmpty()) {
			IUser storedUser = users.get(username);
			
			if (storedUser != null) {
				LOG.debug("Found user, checking credentials...");
				if(storedUser.checkCredentials(credential)) {
					return storedUser;
				}
				LOG.debug("Wrong credentials provided.");
			} else {
				LOG.warn("No user with name " + username + " found!");
			}
		}
		return null;
	}
}
