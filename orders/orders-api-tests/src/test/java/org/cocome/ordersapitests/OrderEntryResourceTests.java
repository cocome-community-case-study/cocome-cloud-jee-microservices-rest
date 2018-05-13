package org.cocome.ordersapitests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.cocome.ordersclient.client.OrderEntryClient;
import org.cocome.ordersclient.client.ProductOrderClient;
import org.cocome.ordersclient.domain.OrderEntry;
import org.cocome.ordersclient.domain.ProductOrder;
import org.junit.Before;
import org.junit.Test;

public class OrderEntryResourceTests {
	private OrderEntryClient client = new OrderEntryClient();
	private ProductOrder order = new ProductOrder();
	
	@Before
	public void setup() {
		ProductOrderClient orderClient = new ProductOrderClient();
		this.order.setOrderingDate(new Date());
		this.order.setDeliveryDate(new Date());
		long id = orderClient.create(this.order, 1);
		this.order.setId(id);
	}
	
	@Test
	public void testCreateReadUpdateDelete() {
		System.out.println("Testing creation of entity");
		
		OrderEntry entry = new OrderEntry();
		entry.setAmount(42);
		entry.setProductId(1);
		long id = this.client.create(entry, 1);
		entry.setId(id);
		
		assertFalse(id == 0);
		
		System.out.println("Testing finding of entity");
		
		entry = this.client.find(entry.getId());
		assertNotNull(entry);
		
		System.out.println("Testing updating entity");
		
		entry.setId(id);
		entry.setAmount(43);
		boolean updateSuccess = this.client.update(entry);
		
		assertTrue(updateSuccess);
		
		System.out.println("Testing deletion of entity");
		
		entry.setId(id);
		boolean deleteSuccess = this.client.delete(entry);
		
		assertTrue(deleteSuccess);
	}
}
