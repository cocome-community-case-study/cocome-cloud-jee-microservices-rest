package org.cocome.productsservice.productquery;

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

	public long createProduct(@NotNull String name, @NotNull long barcode, @NotNull double purchasePrice,
			@NotNull long supplierId);
	
	public boolean updateProduct(@NotNull long id, @NotNull String name, @NotNull double purchasePrice, @NotNull long barcode);
	
	public boolean deleteProduct(@NotNull long productId);

}
