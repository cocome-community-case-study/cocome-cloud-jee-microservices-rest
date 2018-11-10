package org.cocome.productsservice.microservice;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import org.cocome.productsservice.domain.Product;
import org.cocome.productsservice.repository.ProductRepository;
import org.cocome.productsservice.repository.ProductSupplierRepository;

@Local
@Stateless
public class ProductQuery implements IProductQuery{

	@EJB
	private ProductRepository productRepository;
	
	@EJB
	private ProductSupplierRepository productSupplierRepository;
	
	private Logger LOG = Logger.getLogger(ProductQuery.class);
	
	
	@Override
	public Product findProductByid(long id) {
		return productRepository.find(id);
	}

	

}
