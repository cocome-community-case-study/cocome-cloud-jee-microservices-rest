package org.cocome.productsservice.microservice;

import org.cocome.productsservice.domain.Product;

public interface Productmanagement {
	
	/**
	 * finds and returns product
	 * 
	 * @param
	 * @return
	 */
	Product find(long id);
	
	/**
	 * updates product in database
	 * 
	 * @param id
	 * @param barcode
	 * @param purchasePrice
	 * @param name
	 * @param supplier
	 */
	void update(long id, long barcode, double purchasePrice, String name, long supplierID);
	
	
	/**
	 * removes product from database
	 * 
	 * @param id
	 */
	void delete(long id);

}
