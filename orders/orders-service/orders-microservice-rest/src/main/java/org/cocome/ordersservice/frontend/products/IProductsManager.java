package org.cocome.ordersservice.frontend.products;

import java.util.Collection;

import javax.validation.constraints.NotNull;

import org.cocome.ordersservice.frontend.viewdata.ProductViewData;



public interface IProductsManager {

	
	
	public Collection<ProductViewData> getAllProducts();
}
