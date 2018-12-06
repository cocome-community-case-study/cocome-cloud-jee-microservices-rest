package org.cocome.frontendservice.logindata;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;

import org.apache.log4j.Logger;
/**
 * This class creates the user, the permissions and its passwords that are available in the CoCoME-System
 * @author Niko Benkler
 * @author Robert Heinrich
 *
 */
@ApplicationScoped
public class DummyAuthenticator implements IAuthenticator {
	private static final Logger LOG = Logger.getLogger(DummyAuthenticator.class);
	
	private Map<String, IUser> users = Collections.synchronizedMap(new HashMap<String, IUser>());
	
	private void createDummyUsers() {
		
		//create permissions
		IPermission adminPermission = new DummyPermission("admin");
		IPermission enterprisePermission = new DummyPermission("enterprise manager");
		IPermission storePermission = new DummyPermission("store manager");
		IPermission stockPermission = new DummyPermission("stock manager");
		IPermission cashierPermission = new DummyPermission("cashier");
		IPermission databasePermission = new DummyPermission("database manager");
		
		//create credentials
		ICredential adminCredentials = new PlainCredential("admin");
		ICredential enterpriseCredentials = new PlainCredential("enterprise");
		ICredential storeCredentials = new PlainCredential("store");
		ICredential stockCredentials = new PlainCredential("stock");
		ICredential cashierCredentials = new PlainCredential("cashier");
		ICredential databaseCredentials = new PlainCredential("database");
		
		//add permissions to admin
		IUser admin = new DummyUser("admin", adminCredentials, UserRole.ADMIN);
		admin.addPermission(cashierPermission);
		admin.addPermission(stockPermission);
		admin.addPermission(storePermission);
		admin.addPermission(adminPermission);
		admin.addPermission(enterprisePermission);
		admin.addPermission(databasePermission);
		
		//add permissions to enterprisemanager
		IUser enterpriseManager = new DummyUser("enterprisemanager", enterpriseCredentials, UserRole.ENTERPRISE_MANAGER);
		enterpriseManager.addPermission(enterprisePermission);
		
		//add permissions to storemanager
		IUser storeManager = new DummyUser("storemanager", storeCredentials, UserRole.STORE_MANAGER);
		storeManager.addPermission(storePermission);
		storeManager.addPermission(stockPermission);
		
		
		//add permissions to stockmanager
		IUser stockManager = new DummyUser("stockmanager", stockCredentials, UserRole.STOCK_MANAGER);
		stockManager.addPermission(stockPermission);
		
		
		//add permission to cashier
		IUser cashier = new DummyUser("cashier", cashierCredentials, UserRole.CASHIER);
		cashier.addPermission(cashierPermission);
		
		IUser databaseManager = new DummyUser("databasemanager", databaseCredentials, UserRole.DATABASE_MANAGER);
		admin.addPermission(databasePermission);
		
		
		
		//add user to userList
		users.put(admin.getUsername(), admin);
		users.put(enterpriseManager.getUsername(), enterpriseManager);
		users.put(storeManager.getUsername(), storeManager);
		users.put(stockManager.getUsername(), stockManager);
		users.put(cashier.getUsername(), cashier);
		users.put(databaseManager.getUsername(), databaseManager);
	}
	
	@PostConstruct
	public void initAuthenticator() {
		LOG.debug("Initializing users...");
		createDummyUsers();
	}
	
	@Override
	public boolean checkCredentials(IUser user) {
		LOG.debug("Checking credentials of user...");
		return checkCredential(user.getUsername(), user.getCredentials(), user.getRole()) != null;
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
	public IUser checkCredential(String username, ICredential credential, UserRole requestedRole) {
		if (credential != null && username != null && !username.trim().isEmpty()) {
			IUser storedUser = users.get(username);
			
			if (storedUser != null) {
				
				
				LOG.debug("Found user, checking credentials...");
				if(storedUser.checkCredentials(credential) && storedUser.checkRole(requestedRole) ) {
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
