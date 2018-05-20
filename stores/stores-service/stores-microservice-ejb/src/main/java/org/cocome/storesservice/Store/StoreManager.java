package org.cocome.storesservice.Store;

import java.util.Collection;

import javax.ejb.EJB;

import org.cocome.storesservice.domain.StockItem;
import org.cocome.storesservice.domain.Store;
import org.cocome.storesservice.repository.StockItemDBRepository;
import org.cocome.storesservice.repository.StoreDBRepository;

public class StoreManager implements IStoreManagement{

	@EJB
	private StoreDBRepository storeRepo;
	
	@EJB 
	private StockItemDBRepository stockItemRepo;
	
	@Override
	public void changePrice(long storeId,long productId, double price) {
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
	
}
