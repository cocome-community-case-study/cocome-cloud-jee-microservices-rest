package org.cocome.productsapitests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.cocome.productsclient.client.ProductClient;
import org.cocome.productsclient.client.ProductSupplierClient;
import org.cocome.productsclient.domain.ProductSupplierTO;
import org.cocome.productsclient.domain.ProductTO;
import org.cocome.productsclient.exception.ProductsRestException;
import org.junit.Before;
import org.junit.Test;

public class ProductResourceTests {
	private ProductClient client = new ProductClient();
	private ProductSupplierTO supplier = new ProductSupplierTO();
	private ProductSupplierClient supplierClient= new ProductSupplierClient();
	
	 
	@Before
	public void setup() throws ProductsRestException { 
	 
		this.supplier.setName("Apfel Computer");
		
		long id = supplierClient.create(this.supplier);
		this.supplier.setId(id);
	}
	
	@Test
	public void testCreateReadUpdateDelete() throws ProductsRestException {
		System.out.println("Testing creation of entity");
		
		ProductTO product = new ProductTO();
		product.setName("Laptop");
		product.setBarcode(12345);
		product.setPurchasePrice(999.99);
		System.out.println("SupplierId: " + this.supplier.getId());

		long id = this.client.create(product, this.supplier.getId());
		product.setId(id);
		System.out.println("Product id is: " + id);
		
		assertFalse(id == 0);
		
		System.out.println("Testing finding of entity");
		
		product = this.client.find(product.getId());
		assertNotNull(product);
		
		System.out.println("Testing updating entity");
		
		product.setId(id);
		product.setName("New Name");
		this.client.update(product);
		
		
		
		System.out.println("Testing deletion of entity");
		
		product.setId(id);
		this.client.delete(product);
		
		
	}
}
