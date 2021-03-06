package org.cocome.ordersapitests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.cocome.ordersclient.client.ProductOrderClient;
import org.cocome.ordersclient.domain.ProductOrderTO;
import org.cocome.ordersclient.exception.OrdersRestException;
import org.junit.Test;

public class ProductOrderResourceTest {
	private ProductOrderClient client = new ProductOrderClient();
	
	@Test
	public void testCreateReadUpdateDelete() throws OrdersRestException {
		System.out.println("Testing creation of entity");
		
		ProductOrderTO order = new ProductOrderTO();
		order.setOrderingDate(new Date());
		order.setDeliveryDate(new Date()); 
		long id = this.client.create(order, 1);
		order.setId(id);
		
		assertFalse(id == 0);
		
		System.out.println("Testing finding of entity");
		
		order = this.client.find(order.getId());
		assertNotNull(order);
		
		System.out.println("Testing updating entity");
		
		order.setId(id);
		order.setDeliveryDate(new Date());
		boolean updateSuccess = this.client.update(order);
		
		assertTrue(updateSuccess);
		
		System.out.println("Testing deletion of entity");
		
		order.setId(id);
		boolean deleteSuccess = this.client.delete(order);
		
		assertTrue(deleteSuccess);
	} 
}
