package org.cocome.productsapitests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.cocome.productsclient.client.ProductClient;
import org.cocome.productsclient.client.ProductSupplierClient;
import org.cocome.productsclient.domain.Product;
import org.cocome.productsclient.domain.ProductSupplier;
import org.junit.Before;
import org.junit.Test;

public class ProductResourceTests {
	private ProductClient client = new ProductClient();
	private ProductSupplier supplier = new ProductSupplier();
	
	@Before
	public void setup() {
		ProductSupplierClient supplierClient = new ProductSupplierClient();
		this.supplier.setName("Apfel Computer");
		long id = supplierClient.create(this.supplier, 1);
		this.supplier.setId(id);
	}
	
	@Test
	public void testCreateReadUpdateDelete() {
		System.out.println("Testing creation of entity");
		
		Product product = new Product();
		product.setName("Laptop");
		product.setBarcode(12345);
		product.setPurchasePrice(999.99);
		long id = this.client.create(product, this.supplier.getId());
		product.setId(id);
		
		assertFalse(id == 0);
		
		System.out.println("Testing finding of entity");
		
		product = this.client.find(product.getId());
		assertNotNull(product);
		
		System.out.println("Testing updating entity");
		
		product.setId(id);
		product.setName("New Name");
		boolean updateSuccess = this.client.update(product);
		
		assertTrue(updateSuccess);
		
		System.out.println("Testing deletion of entity");
		
		product.setId(id);
		boolean deleteSuccess = this.client.delete(product);
		
		assertTrue(deleteSuccess);
	}
}
