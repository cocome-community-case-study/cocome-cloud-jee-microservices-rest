package org.cocome.ordersservice.productquery;

import java.util.Collection;

import org.cocome.productsclient.domain.ProductTO;
import org.cocome.productsclient.exception.ProductsRestException;



public interface IProductQuery {
	public Collection<ProductTO> getAllProducts() throws ProductsRestException;

}
