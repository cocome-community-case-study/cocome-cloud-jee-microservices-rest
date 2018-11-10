package org.cocome.productsservice.microservice;

import java.util.Collection;

import javax.validation.constraints.NotNull;

import org.cocome.productsservice.domain.Product;

public interface IProductQuery {

	/**
	 * finds and returns product
	 * 
	 * @param
	 * @return
	 */
	public Product findProductByid(@NotNull long id);

	public Product findProductByBarcode(@NotNull long barcode);

	public Collection<Product> getAllProducts();

	public Collection<Product> getProductsBySupplier(@NotNull long supplierId);

	public boolean createProduct(@NotNull String name, @NotNull long barcode, @NotNull double purchasePrice,
			@NotNull long supplierId);

}
