package org.cocome.productsservice.microservice;

import org.cocome.productsservice.domain.Product;

public interface IProductQuery {
	
	/**
	 * finds and returns product
	 * 
	 * @param
	 * @return
	 */
	Product findProductByid(long id);
	
	

}
