package org.cocome.productsservice.microservice;

import org.cocome.productsservice.domain.ProductSupplier;
import java.util.Collection;
import org.cocome.productsservice.domain.Product;

public interface ISupplierQuery {

	/*
	 * find
	 * update
	 * delete
	 * get products
	 * createProduct 
	 * 
	 */
	
	/**
	 * finds and returns a productsupplier
	 * @param id
	 * @return
	 */
	ProductSupplier find(Long id);
	
	/**
	 * updates DB entry of Supplier
	 * @param id
	 * @param name
	 * @param products
	 * @param enterpriceID
	 */
	void update(long id, String name, Collection<Product> products, long enterpriseId);
	
	/**
	 * removes entry from DB
	 * 
	 * @param id
	 */
	void delete(long id);
	
	/**
	 * returns collection of products provided by specified supplier
	 * @param id
	 * @return
	 */
	Collection<Product> getProducts(long id);
	
	/**
	 * adds new product to supplier
	 * 
	 * @param supplierID
	 * @param productId
	 * @param barcode
	 * @param purchasePrice
	 * @param name
	 */
	void createProduct(long supplierID, long barcode, double purchasePrice, String name);
	
	/**
	 * creates new Supplier
	 * @param name
	 * @param enterpriseID
	 */
	void createSupplier(String name, long enterpriseID); 
	
	
}
