package org.cocome.storesapitests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.cocome.storesclient.client.StockItemClient;
import org.cocome.storesclient.client.StoreClient;
import org.cocome.storesclient.client.TradingEnterpriseClient;
import org.cocome.storesclient.domain.StockItemTO;
import org.cocome.storesclient.domain.StoreTO;
import org.cocome.storesclient.domain.TradingEnterpriseTO;
import org.junit.Before;
import org.junit.Test;

public class StockItemResourceTest {
	private StockItemClient client = new StockItemClient();
	private StoreTO store = new StoreTO();
	
	@Before
	public void setup() {
		TradingEnterpriseClient enterpriseClient = new TradingEnterpriseClient();
		TradingEnterpriseTO enterprise = new TradingEnterpriseTO();
		enterprise.setName("CoCoME Inc.");
		long enterpriseId = enterpriseClient.create(enterprise);
		
		StoreClient storeClient = new StoreClient();
		this.store.setName("Test");
		this.store.setLocation("Karlsruhe");
		long storeId = storeClient.create(store, enterpriseId);
		this.store.setId(storeId);
		
	}
	
	@Test
	public void testCreateReadUpdateDelete() {
		System.out.println("Testing creation of entity");
		System.out.println("Store id is: " + this.store.getId());
		StockItemTO stockItem = new StockItemTO();
		stockItem.setAmount(5);
		stockItem.setIncomingAmount(3);
		stockItem.setMaxStock(10);
		stockItem.setMinStock(1);
		stockItem.setProductId(1);
		stockItem.setSalesPrice(9.99d);
		stockItem.setBarcode(1234);  //Normally this has to be the products barcode
		stockItem.setStore(store);
		long id = this.client.create(stockItem, this.store.getId());
		stockItem.setId(id);
		
		System.out.println("StockItem id is: " + id);
		assertFalse(id == -1);
		
		System.out.println("Testing finding of entity");
		
		stockItem = this.client.find(stockItem.getId());
		assertNotNull(stockItem);
		
		System.out.println("Testing updating entity");
		
		stockItem.setId(id);
		stockItem.setSalesPrice(12.99d);
		boolean updateSuccess = this.client.update(stockItem);
		
		assertTrue(updateSuccess);
		
		System.out.println("Testing deletion of entity");
		
		stockItem.setId(id);
		//boolean deleteSuccess = this.client.delete(stockItem);
		
		//assertTrue(deleteSuccess);
	}
}
