package org.cocome.storesservice.storeManager;

import java.util.Collection;
import java.util.HashMap;

import javax.ejb.EJB;

import org.cocome.enterpriseservice.enterpriseManager.EnterpriseManager;
import org.cocome.storageOrganizer.IStorageOrganizer;
import org.cocome.storageOrganizer.StorageOrganizer;
import org.cocome.storesservice.cashDesk.cashDeskModel.CashDesk;
import org.cocome.storesservice.cashDesk.cashDeskModel.ICashDesk;
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
	
	private IStorageOrganizer storageOrganizer;
	
	private final long storeId;
	public long getStoreId() {
		return storeId;
	}

	private final long enterpriseId;
	private final EnterpriseManager eManager;
	
	public StoreAdminManager(EnterpriseManager eManager, long enterpriseId, long storeId) {
		this.storeId = storeId;
		cashDesks = new HashMap<String, ICashDesk>();
		this.enterpriseId = enterpriseId;
		this.storageOrganizer = new StorageOrganizer(this.storeId);
		this.eManager = eManager;
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
			ICashDesk cDesk = new CashDesk(this.enterpriseId, this, cashDeskName, this.storageOrganizer);
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

	@Override
	public Collection<StockItem> getStoreProducts(long storeId) {
		return storeRepo.find(storeId).getStockItems();
	}
	
	public void runningOutOfItem(long productId) {
		eManager.shiftItem(storeId, productId);
	}
}
