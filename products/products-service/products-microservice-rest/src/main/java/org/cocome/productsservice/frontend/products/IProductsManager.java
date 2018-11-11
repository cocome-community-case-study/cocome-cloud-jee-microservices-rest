package org.cocome.productsservice.frontend.products;

import javax.validation.constraints.NotNull;

import org.cocome.productsservice.frontendViewData.ProductViewData;

import java.util.Collection;

public interface IProductsManager {

	public String createProduct(@NotNull String productName, @NotNull long productsBarcode,
			@NotNull double purchasePrice, @NotNull long supplierId);
	
	public Collection<ProductViewData> getAllProducts();
}
