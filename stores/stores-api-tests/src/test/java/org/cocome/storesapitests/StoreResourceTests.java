package org.cocome.storesapitests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.cocome.storesclient.client.StoreClient;
import org.cocome.storesclient.client.TradingEnterpriseClient;
import org.cocome.storesclient.domain.Store;
import org.cocome.storesclient.domain.TradingEnterprise;
import org.junit.Before;
import org.junit.Test;

public class StoreResourceTests {
	private StoreClient client = new StoreClient();
	private TradingEnterprise enterprise = new TradingEnterprise();
	
	@Before
	public void setup() {
		TradingEnterpriseClient enterpriseClient = new TradingEnterpriseClient();
		this.enterprise.setName("Test");
		enterpriseClient.create(enterprise);
	}
	
	@Test
	public void testCreateReadUpdateDelete() {
		System.out.println("Testing creation of entity");
		
		Store store = new Store();
		store.setName("Test Enterprise");
		long id = this.client.create(store, this.enterprise.getId());
		store.setId(id);
		
		assertFalse(id == 0);
		
		System.out.println("Testing finding of entity");
		
		store = this.client.find(store.getId());
		assertNotNull(store);
		
		System.out.println("Testing updating entity");
		
		store.setId(id);
		store.setName("New Name");
		boolean updateSuccess = this.client.update(store);
		
		assertTrue(updateSuccess);
		
		System.out.println("Testing deletion of entity");
		
		store.setId(id);
		boolean deleteSuccess = this.client.delete(store);
		
		assertTrue(deleteSuccess);
	}
}
