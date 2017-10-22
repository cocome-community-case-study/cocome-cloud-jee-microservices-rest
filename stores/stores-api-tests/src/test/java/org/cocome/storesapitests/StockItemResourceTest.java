package org.cocome.storesapitests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.cocome.storesclient.client.StockItemClient;
import org.cocome.storesclient.client.StoreClient;
import org.cocome.storesclient.client.TradingEnterpriseClient;
import org.cocome.storesclient.domain.StockItem;
import org.cocome.storesclient.domain.Store;
import org.cocome.storesclient.domain.TradingEnterprise;
import org.junit.Before;
import org.junit.Test;

public class StockItemResourceTests {
	private StockItemClient client = new StockItemClient();
	private Store store = new Store();
	
	@Before
	public void setup() {
		TradingEnterpriseClient enterpriseClient = new TradingEnterpriseClient();
		TradingEnterprise enterprise = new TradingEnterprise();
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
		
		StockItem stockItem = new StockItem();
		stockItem.setAmount(5);
		stockItem.setIncomingAmount(3);
		stockItem.setMaxStock(10);
		stockItem.setMinStock(1);
		stockItem.setProductId(1);
		stockItem.setSalesPrice(9.99d);
		long id = this.client.create(stockItem, this.store.getId());
		stockItem.setId(id);
		
		assertFalse(id == 0);
		
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
		boolean deleteSuccess = this.client.delete(stockItem);
		
		assertTrue(deleteSuccess);
	}
}
