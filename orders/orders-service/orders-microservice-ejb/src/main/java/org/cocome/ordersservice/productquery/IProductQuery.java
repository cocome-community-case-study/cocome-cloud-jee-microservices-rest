package org.cocome.ordersservice.productquery;

import java.util.Collection;

import org.cocome.productsclient.domain.ProductTO;



public interface IProductQuery {
	public Collection<ProductTO> getAllProducts();

}
