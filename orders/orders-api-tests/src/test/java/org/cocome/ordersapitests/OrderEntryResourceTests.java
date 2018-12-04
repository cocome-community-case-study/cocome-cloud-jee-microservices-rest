package org.cocome.ordersapitests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.cocome.ordersclient.client.OrderEntryClient;
import org.cocome.ordersclient.client.ProductOrderClient;
import org.cocome.ordersclient.domain.OrderEntryTO;
import org.cocome.ordersclient.domain.ProductOrderTO;
import org.cocome.ordersclient.exception.OrdersRestException;
import org.junit.Before;
import org.junit.Test;

public class OrderEntryResourceTests {
	private OrderEntryClient client = new OrderEntryClient();
	private ProductOrderTO order = new ProductOrderTO();
	
	@Before
	public void setup() throws OrdersRestException {
		ProductOrderClient orderClient = new ProductOrderClient();
		this.order.setOrderingDate(new Date());
		this.order.setDeliveryDate(new Date());
		long id = orderClient.create(this.order, 1); 
		this.order.setId(id);
		System.out.println("Order id is: " +id);
	}
	
	@Test 
	public void testCreateReadUpdateDelete() throws OrdersRestException {
		System.out.println("Testing creation of entity");
		
		OrderEntryTO entry = new OrderEntryTO();
		entry.setAmount(42);
		entry.setProductId(1);
		entry.setOrderId(order.getId());
		long id = this.client.create(entry, order.getId());
		entry.setId(id);
		System.out.println("Entry id is: " + id);
		
		assertFalse(id == -1);
		
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
