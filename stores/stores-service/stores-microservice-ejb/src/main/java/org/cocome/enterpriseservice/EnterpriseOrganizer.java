package org.cocome.enterpriseservice;

import java.util.Collection;
import java.util.HashMap;

import javax.ejb.EJB;

import org.cocome.enterpriseservice.enterpriseManager.EnterpriseManager;
import org.cocome.enterpriseservice.enterpriseManager.IEnterpriseManager;
import org.cocome.storesservice.domain.Store;
import org.cocome.storesservice.domain.TradingEnterprise;
import org.cocome.storesservice.repository.TradingEnterpriseDBRepository;

public class EnterpriseOrganizer implements IEnterpriseOrganizer{
	private HashMap<Long, IEnterpriseManager> activeEnterprises;
	
	public EnterpriseOrganizer() {
		activeEnterprises = new HashMap<Long,IEnterpriseManager>();
	}
	@EJB
	private TradingEnterpriseDBRepository enterpriseDBRepository;
	
	@Override
	public IEnterpriseManager getActiveEnterprise(long enterpriseId) {
		if(activeEnterprises.containsKey(enterpriseId)) {
			return activeEnterprises.get(enterpriseId);
		}else {
			if(!enterpriseDBRepository.find(enterpriseId).equals(null)) {
				IEnterpriseManager enterpriseManager = new EnterpriseManager(enterpriseId);			
				activeEnterprises.put(enterpriseId, enterpriseManager);
				return enterpriseManager;
			} else {
				return null;
			}
		}
	}


	@Override
	public Collection<TradingEnterprise> getAll() {
		return enterpriseDBRepository.all();
	}


	@Override
	public long createNewEnterprise(String name, Collection<Store> stores) {
		TradingEnterprise enterprise = new TradingEnterprise();
		enterprise.setName(name);
		enterprise.setStores(stores);
		
		return enterpriseDBRepository.create(enterprise);	
	}


	@Override
	public void deleteEnterprise(long enterpriseId) {
		IEnterpriseManager enterpriseManager = getActiveEnterprise(enterpriseId);
		
		for (Store store: enterpriseManager.getAll()) {
			enterpriseManager.deleteStore(store.getId());
		}
		activeEnterprises.remove(enterpriseId);
		enterpriseDBRepository.delete(enterpriseId);
	}
	
}
