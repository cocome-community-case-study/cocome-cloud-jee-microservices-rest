package org.cocome.storesservice.storeManager;

import java.util.Collection;
import java.util.HashMap;

import javax.ejb.EJB;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import org.cocome.enterpriseservice.enterpriseManager.EnterpriseManager;
import org.cocome.storageOrganizer.IStorageOrganizerSystem;
import org.cocome.storageOrganizer.StorageOrganizerSystem;
import org.cocome.storesservice.cashDesk.cashDeskModel.CashDesk;
import org.cocome.storesservice.cashDesk.cashDeskModel.ICashDesk;
import org.cocome.storesservice.domain.StockItem;
import org.cocome.storesservice.domain.Store;
import org.cocome.storesservice.repository.StockItemDBRepository;
import org.cocome.storesservice.repository.StoreDBRepository;
import org.cocome.productsservice.domain.*;

public class StoreAdminManager implements IStoreManagement, IStockAdmin{

	@EJB
	private StoreDBRepository storeRepo;
	
	@EJB 
	private StockItemDBRepository stockItemRepo;
	
	private HashMap<String, ICashDesk> cashDesks;
	
	private IStorageOrganizerSystem storageOrganizer;
	
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
		this.storageOrganizer = new StorageOrganizerSystem(this.storeId);
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

	@Override
	public void createStockItem(long productId, long salePrice, long minStockAmount, long maxStockAmount, long storeId) {
		// rest call to get product item and read out the needed informations
		Client client = ClientBuilder.newClient();
		String targetString = "http://localhost:8980/productsmicroservice/rest/products/" + Long.toString(productId);
		Product response = client.target(targetString).request(MediaType.APPLICATION_XML).get(Product.class);
		
		StockItem stockItem = new StockItem();
		stockItem.setProductId(productId);
		stockItem.setSalesPrice(salePrice);
		stockItem.setMaxStock(maxStockAmount);
		stockItem.setMinStock(minStockAmount);
		
		Store store = storeRepo.find(storeId);
		stockItem.setStore(store);
		
		stockItemRepo.create(stockItem);
		Collection<StockItem> item = store.getStockItems();
		item.add(stockItem);
		store.setStockItems(item);
		storeRepo.update(store);
		
		stockItem.setBarcode(response.getBarcode());
		
		storeRepo.update(store);
		stockItemRepo.update(stockItem);
		
	}

	
	@Override
	public void updateStockItem(long stockItemId, long salePrice, long minStockAmount, long maxStockAmount, long storeId) {
		Store store = storeRepo.find(storeId);
		
		Collection<StockItem> items = store.getStockItems();
		
		for (StockItem stockItem : items) {
			if(stockItem.getId() == stockItemId) {
				stockItem.setSalesPrice(salePrice);
				stockItem.setMinStock(minStockAmount);
				stockItem.setMaxStock(maxStockAmount);
				stockItemRepo.update(stockItem);
				break;
			}
		}
		//not needed
		store.setStockItems(items);
		storeRepo.update(store);
		
	}
}
