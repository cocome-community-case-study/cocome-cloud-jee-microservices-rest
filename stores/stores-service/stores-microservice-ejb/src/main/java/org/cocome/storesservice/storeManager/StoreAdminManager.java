package org.cocome.storesservice.storeManager;

import java.util.Collection;
import java.util.HashMap;

import javax.ejb.EJB;
import javax.net.ssl.ExtendedSSLSession;

import org.cocome.storesservice.cashDesk.cashDeskModel.CashDesk;
import org.cocome.storesservice.domain.StockItem;
import org.cocome.storesservice.domain.Store;
import org.cocome.storesservice.repository.StockItemDBRepository;
import org.cocome.storesservice.repository.StoreDBRepository;

public class StoreAdminManager implements IStoreAdminManagement{

	@EJB
	private StoreDBRepository storeRepo;
	
	@EJB 
	private StockItemDBRepository stockItemRepo;
	
	private HashMap<String, CashDesk> cashdesks;
	
	private long storeId;
	
	private StoreAdminManager() {
	}
	
	public StoreAdminManager(long storeId) {
		this.storeId = storeId;
		cashdesks = new HashMap<String, CashDesk>();
	}
	
	@Override
	public void changePrice(long productId, double price) {
		Store store = storeRepo.find(storeId);
		
		Collection<StockItem> items = store.getStockItems();
		
		for (StockItem stockItem : items) {
			if(stockItem.getId() == productId) {
				stockItem.setSalesPrice(price);
				stockItemRepo.update(stockItem);
				break;
			}
		}
		
		store.setStockItems(items);
		storeRepo.update(store);	
	}

	@Override
	public CashDesk getCashdesk(long storeId, String cashDeskName) {
		if(cashdesks.containsKey(cashDeskName)){
			return cashdesks.get(cashDeskName);
		} else {
			CashDesk cDesk = new CashDesk(cashDeskName);
			cashdesks.put(cashDeskName, cDesk);
			return cDesk;
		}
	}
}
