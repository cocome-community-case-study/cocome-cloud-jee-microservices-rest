package org.cocome.enterpriseservice.enterpriseManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.EJB;

import org.cocome.storesservice.domain.StockItem;
import org.cocome.storesservice.domain.Store;
import org.cocome.storesservice.domain.TradingEnterprise;
import org.cocome.storesservice.repository.StoreDBRepository;
import org.cocome.storesservice.repository.TradingEnterpriseDBRepository;
import org.cocome.storesservice.storeManager.IStoreAdminManagement;
import org.cocome.storesservice.storeManager.StoreAdminManager;

public class EnterpriseManager implements IEnterpriseManager{

	private Map<Long, IStoreAdminManagement> activeStores;

	@EJB
	private StoreDBRepository storeRepo;
	
	@EJB
	private TradingEnterpriseDBRepository enterpriseRepo;
	
	private long enterpriseId;
	
	public EnterpriseManager(long id) {
		activeStores = new HashMap<Long, IStoreAdminManagement>();
		enterpriseId = id;
	}
	
	@Override
	public IStoreAdminManagement getActiveStore(long storeId) {
		if(activeStores.containsKey(storeId)) {
			return activeStores.get(storeId);
		}else {
			if(!storeRepo.find(storeId).equals(null)) {
				StoreAdminManager storeManager = new StoreAdminManager(enterpriseId, storeId);			
				activeStores.put(storeId, storeManager);
				return storeManager;
			} else {
				return null;
			}
		}
	}

	@Override
	public Collection<Store> getAll() {
		return enterpriseRepo.find(enterpriseId).getStores();
	}

	@Override
	public long createNewStore(String name, String location) {
		Store store = new Store();
		store.setName(name);
		store.setLocation(location);
		store.setStockItems(new ArrayList<StockItem>());
		
		TradingEnterprise enterprise = enterpriseRepo.find(enterpriseId);
		store.setEnterprise(enterprise);
		
		long id = storeRepo.create(store);
		
		
		Collection<Store> stores = enterprise.getStores();
		stores.add(storeRepo.find(id));
		enterprise.setStores(stores);
		
		enterpriseRepo.update(enterprise);
		
		return id;
	}

	@Override
	public void deleteStore(long storeId) {
		if(activeStores.containsKey(storeId)){
			activeStores.remove(storeId);
		}
		TradingEnterprise enterprise = enterpriseRepo.find(enterpriseId);
		Collection<Store> stores = enterprise.getStores();
		for (Store store : stores) {
			if(store.getId() == storeId) {
				stores.remove(store);
				break;
			}
		}
		enterprise.setStores(stores);
		enterpriseRepo.update(enterprise);
		
		storeRepo.delete(storeId);
	}

}
