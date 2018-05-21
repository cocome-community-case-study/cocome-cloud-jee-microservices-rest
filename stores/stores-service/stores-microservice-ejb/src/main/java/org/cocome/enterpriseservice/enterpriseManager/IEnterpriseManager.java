package org.cocome.enterpriseservice.enterpriseManager;

import java.util.Collection;

import org.cocome.storesservice.domain.Store;
import org.cocome.storesservice.storeManager.IStoreAdminManagement;

/**
 * Works on enterprises in form of editing the enterprise structur - creating/deleting etc..
 *
 * @author Tobias Haßberg
 */
public interface IEnterpriseManager {
	public IStoreAdminManagement getActiveStore(long storeId);
	
	public Collection<Store> getAll();
	
	public long createNewStore(String Name, String location);
	
	public void deleteStore(long storeId);
}
