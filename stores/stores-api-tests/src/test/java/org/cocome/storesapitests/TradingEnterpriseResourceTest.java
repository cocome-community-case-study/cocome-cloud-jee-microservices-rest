package org.cocome.storesapitests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.cocome.storesclient.client.TradingEnterpriseClient;
import org.cocome.storesclient.domain.TradingEnterpriseTO;
import org.cocome.storesclient.exception.StoreRestException;
import org.junit.Test;

public class TradingEnterpriseResourceTest {
	private TradingEnterpriseClient client = new TradingEnterpriseClient();
	
	@Test
	public void testCreateReadUpdateDelete() throws StoreRestException {
		System.out.println("Testing creation of entity");
		
		TradingEnterpriseTO enterprise = new TradingEnterpriseTO();
		enterprise.setName("Test Enterprise");

		long id = this.client.create(enterprise);
		enterprise.setId(id);
		System.out.println("Id is: " + id);
		assertFalse(id == -1);
		
		System.out.println("Testing finding of entity");
		
		enterprise = this.client.find(enterprise.getId());
		assertNotNull(enterprise);
		
		System.out.println("Testing updating entity");
		
		enterprise.setId(id);
		enterprise.setName("New Name");
		this.client.update(enterprise);
		
		
		
		System.out.println("Testing deletion of entity");
		
		enterprise.setId(id);
		this.client.delete(enterprise);
		
	
	}
}
