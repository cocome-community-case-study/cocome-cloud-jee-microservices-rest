package org.cocome.enterpriseservice.enterpriseManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.ejb.EJB;

import org.apache.bcel.verifier.exc.LoadingException;
import org.cocome.storesservice.domain.StockItem;
import org.cocome.storesservice.domain.Store;
import org.cocome.storesservice.domain.TradingEnterprise;
import org.cocome.storesservice.repository.StockItemDBRepository;
import org.cocome.storesservice.repository.StoreDBRepository;
import org.cocome.storesservice.repository.TradingEnterpriseDBRepository;
import org.cocome.storesservice.storeManager.IStoreManagement;
import org.cocome.storesservice.storeManager.StoreAdminManager;

public class EnterpriseManager implements IEnterpriseManager{

	private Map<Long, IStoreManagement> activeStores;

	@EJB
	private StoreDBRepository storeRepo;
	
	@EJB
	private StockItemDBRepository stockRepo;
	
	@EJB
	private TradingEnterpriseDBRepository enterpriseRepo;
	
	private long enterpriseId;
	
	public EnterpriseManager(long id) {
		activeStores = new HashMap<Long, IStoreManagement>();
		enterpriseId = id;
	}
	
	@Override
	public IStoreManagement getActiveStore(long storeId) {
		if(activeStores.containsKey(storeId)) {
			return activeStores.get(storeId);
		}else {
			if(!storeRepo.find(storeId).equals(null)) {
				StoreAdminManager storeManager = new StoreAdminManager(this, enterpriseId, storeId);			
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
	
	public void shiftItem(long storeId, long productId) {
		Store toShift = findOptimum(findStores(productId));
		long shiftAmount =0;
		
		for (StockItem item : toShift.getStockItems()) {
			if (item.getProductId() == productId) {
				shiftAmount = (item.getAmount()- item.getMinStock())/2; 
				item.setAmount(item.getAmount()-shiftAmount);
				stockRepo.update(item);
				break;
			}
		}
		for (StockItem item : storeRepo.find(storeId).getStockItems()) {
			if (item.getProductId() == productId) { 
				item.setAmount(item.getAmount()+shiftAmount);
				stockRepo.update(item);
				break;
			}
		}
	}
	
	private Collection<Store> findStores(long productId) {
		Collection<Store> stores = new ArrayList<Store>();
		for (Store store : getAll()) {
			for (StockItem item : store.getStockItems()) {
				if(item.getProductId() == productId) {
					if(item.getAmount() > item.getMinStock() + 4) {
						stores.add(store);
					}
					break;
				}
			}
		}
		return stores;
	}
	
	private Store findOptimum(Collection<Store> stores) {
	   Random randomGenerator = new Random();
	    int index = randomGenerator.nextInt(stores.size());
		
		return stores.toArray(new Store[stores.size()])[index];
	}


}
