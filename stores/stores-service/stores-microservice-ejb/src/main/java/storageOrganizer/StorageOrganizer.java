package storageOrganizer;

import java.awt.im.spi.InputMethod;

import javax.ejb.EJB;

import org.cocome.storesservice.domain.StockItem;
import org.cocome.storesservice.repository.StockItemRepository;
import org.cocome.storesservice.repository.StoreDBRepository;

public class StorageOrganizer implements IStorageOrganizer{
	
	@EJB
	private StoreDBRepository storeRepo;
	
	@EJB
	private StockItemRepository stockItemRepository;
		
	private final long storeId;
	
	public StorageOrganizer(long storeId) {
		this.storeId = storeId;
	}
	
	@Override
	public boolean containStockItem(long id) {	
		return storeRepo.find(storeId).getStockItems().contains(stockItemRepository.find(id));
	}

	@Override
	public StockItem getItem(long id) {
		return storeRepo.find(id);
	}

	@Override
	public boolean reduceInventory(long id, int amount) {
		StockItem item = stockItemRepository.find(id);
		if(item.getAmount() > 0) {
			item.setAmount(item.getAmount()-1);
			stockItemRepository.update(item);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void incrementInventory(long id, int amount) {
		StockItem item = stockItemRepository.find(id);
		item.setAmount(item.getAmount() + amount);
		stockItemRepository.update(item);
	}
	
}
