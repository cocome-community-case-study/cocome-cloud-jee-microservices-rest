package org.cocome.productsservice.microservice;

import javax.ejb.EJB;
import org.cocome.productsservice.domain.Product;
import org.cocome.productsservice.repository.ProductRepository;
import org.cocome.productsservice.repository.ProductSupplierRepository;

public class Productmanager implements Productmanagement{

	@EJB
	private ProductRepository productRepository;
	
	@EJB
	private ProductSupplierRepository productSupplierRepository;
	
	@Override
	public Product find(long id) {
		return productRepository.find(id);
	}

	@Override
	public void update(long id, long barcode, double purchasePrice, String name, long supplierId) {
		Product product = new Product();
		product.setBarcode(barcode);
		product.setId(id);
		product.setName(name);
		product.setPurchasePrice(purchasePrice);
		product.setSupplier(productSupplierRepository.find(supplierId));
		
		productRepository.update(product);
		
		//TODO update Stock items
	}

	@Override
	public void delete(long id) {
		productRepository.delete(id);
		//TODO delete stock item
	}

}
