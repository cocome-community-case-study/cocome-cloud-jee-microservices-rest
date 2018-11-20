package org.cocome.productsservice.productquery;

import java.util.Collection;

import javax.validation.constraints.NotNull;

import org.cocome.productsservice.domain.Product;
import org.cocome.productsservice.exceptions.CreateException;
import org.cocome.productsservice.exceptions.QueryException;

public interface IProductQuery {

	/**
	 * finds and returns product
	 * 
	 * @param
	 * @return
	 * @throws QueryException 
	 */
	public Product findProductByid(@NotNull long id) throws QueryException;

	public Product findProductByBarcode(@NotNull long barcode) throws QueryException;

	public Collection<Product> getAllProducts();

	public Collection<Product> getProductsBySupplier(@NotNull long supplierId) throws QueryException;

	public long createProduct(@NotNull String name, @NotNull long barcode, @NotNull double purchasePrice,
			@NotNull long supplierId) throws CreateException;
	
	public void updateProduct(@NotNull long id, @NotNull String name, @NotNull double purchasePrice, @NotNull long barcode) throws QueryException;
	
	public void deleteProduct(@NotNull long productId) throws QueryException;

}
