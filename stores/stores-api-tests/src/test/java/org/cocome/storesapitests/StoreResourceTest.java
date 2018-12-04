package org.cocome.storesapitests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.cocome.storesclient.client.StoreClient;
import org.cocome.storesclient.client.TradingEnterpriseClient;
import org.cocome.storesclient.domain.StoreTO;
import org.cocome.storesclient.domain.TradingEnterpriseTO;
import org.cocome.storesclient.exception.StoreRestException;
import org.junit.Before;
import org.junit.Test;

public class StoreResourceTest {
	private StoreClient client = new StoreClient();
	private TradingEnterpriseTO enterprise = new TradingEnterpriseTO();

	@Before
	public void setup() throws StoreRestException {
		TradingEnterpriseClient enterpriseClient = new TradingEnterpriseClient();
		this.enterprise.setName("Test");
		long enterpriseId = enterpriseClient.create(enterprise);
		enterprise.setId(enterpriseId);
		System.out.println("EnterpriseID: " + enterpriseId);
	}

	@Test
	public void testCreateReadUpdateDelete() throws StoreRestException {
		System.out.println("Testing creation of entity");

		StoreTO store = new StoreTO();
		store.setName("Test Store");
		store.setLocation("Test Location");
		long id = this.client.create(store, this.enterprise.getId());
		store.setId(id);

		assertFalse(id == -1);
		System.out.println("Store id is: " + id);
		System.out.println("Testing finding of entity");

		store = this.client.find(store.getId());
		assertNotNull(store);

		System.out.println("Testing updating entity");

		store.setId(id);
		store.setName("New Name");
		this.client.update(store);

		System.out.println("Testing deletion of entity");

		store.setId(id);
		this.client.delete(store);

	}
}
