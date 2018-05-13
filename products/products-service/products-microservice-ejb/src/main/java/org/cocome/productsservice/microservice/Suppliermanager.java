package org.cocome.productsservice.microservice;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Iterator;

import javax.ejb.EJB;

import org.cocome.productsservice.domain.Product;
import org.cocome.productsservice.domain.ProductSupplier;
import org.cocome.productsservice.repository.ProductRepository;
import org.cocome.productsservice.repository.ProductSupplierRepository;

public class Suppliermanager implements Suppliermanagement{

	@EJB
	private ProductRepository productRepository;
	
	@EJB
	private ProductSupplierRepository productSupplierRepository;
	
	
	@Override
	public ProductSupplier find(Long id) {		
		return productSupplierRepository.find(id)	;
	}

	@Override
	public void update(long id, String name, Collection<Product> products, long enterpriseId) {
		ProductSupplier supplier = new ProductSupplier();
		supplier.setName(name);
		supplier.setEnterpriseId(enterpriseId);
		supplier.setId(id);
		supplier.setProducts(products);
		
		productSupplierRepository.update(supplier);
	}

	@Override
	public void delete(long id) {
		productSupplierRepository.delete(id);
	}

	@Override
	public Collection<Product> getProducts(long id) {
		return productSupplierRepository.find(id).getProducts();
	}

	@Override
	public void createProduct(long supplierId, long barcode, double purchasePrice, String name) {
		Product product = new Product();
		ProductSupplier supplier = productSupplierRepository.find(supplierId);
		
		product.setBarcode(barcode);
		product.setSupplier(supplier);
		product.setName(name);
		product.setPurchasePrice(purchasePrice);		
		
		supplier.addProduct(product);
		
		productRepository.create(product);
	}

	@Override
	public void createSupplier(String name, long enterpriseId) {
		ProductSupplier supplier = new ProductSupplier();
		supplier.setEnterpriseId(enterpriseId);
		supplier.setName(name);
		supplier.setProducts(new LinkedList<Product>());
		
	}

}
