package org.cocome.storesservice.storeManager;

import java.util.Collection;
import java.util.HashMap;

import javax.ejb.EJB;

import org.cocome.storesservice.cashDesk.ICashDesk;
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
	
	private HashMap<String, ICashDesk> cashDesks;
	
	private final long storeId;
	private final long enterpriseId;
	
	
	public StoreAdminManager(long enterpriseId, long storeId) {
		this.storeId = storeId;
		cashDesks = new HashMap<String, ICashDesk>();
		this.enterpriseId = enterpriseId;
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
	public ICashDesk getCashdesk(String cashDeskName) {
		if(cashDesks.containsKey(cashDeskName)){
			return cashDesks.get(cashDeskName);
		} else {
			ICashDesk cDesk = new CashDesk(this.enterpriseId, this.storeId, cashDeskName);
			cashDesks.put(cashDeskName, cDesk);
			return cDesk;
		}
	}

	@Override
	public Collection<ICashDesk> getAll() {
		return cashDesks.values();
	}

	@Override
	public void deleteCashdesk(String cashDeskName) {
		cashDesks.remove(cashDeskName);
	}
}
