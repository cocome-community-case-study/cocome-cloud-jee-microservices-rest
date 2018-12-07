package org.cocome.productsapitests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.cocome.productsclient.client.ProductSupplierClient;
import org.cocome.productsclient.domain.ProductSupplierTO;
import org.cocome.productsclient.exception.ProductsRestException;
import org.junit.Test;

public class ProductSupplierResourceTest {
	private ProductSupplierClient client = new ProductSupplierClient();

	@Test
	public void testCreateReadUpdateDelete() throws ProductsRestException {

		System.out.println("Testing creation of entity");

		ProductSupplierTO supplier = new ProductSupplierTO();
		supplier.setName("Apfel Computer");
		System.out.println(client);

		long id = this.client.create(supplier);
		System.out.println(id);
		supplier.setId(id);

		assertFalse(id == 0);

		System.out.println("Testing finding of entity");

		supplier = this.client.find(supplier.getId());
		assertNotNull(supplier);

		System.out.println("Testing updating entity");

		supplier.setId(id);
		supplier.setName("New Name");
		this.client.update(supplier);

		System.out.println("Testing deletion of entity");

		supplier.setId(id);
		this.client.delete(supplier);

	}
}
