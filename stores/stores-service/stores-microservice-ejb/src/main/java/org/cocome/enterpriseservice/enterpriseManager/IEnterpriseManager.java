package org.cocome.enterpriseservice.enterpriseManager;

import java.util.Collection;

import org.cocome.storesservice.domain.Store;
import org.cocome.storesservice.storeManager.IStoreManagement;

/**
 * Works on enterprises in form of editing the enterprise structure - creating/deleting etc..
 *
 * @author Tobias Haßberg
 */
public interface IEnterpriseManager {
	public IStoreManagement getActiveStore(long storeId);
	
	public Collection<Store> getAll();
	
	public long createNewStore(String Name, String location);
	
	public void deleteStore(long storeId);
}
