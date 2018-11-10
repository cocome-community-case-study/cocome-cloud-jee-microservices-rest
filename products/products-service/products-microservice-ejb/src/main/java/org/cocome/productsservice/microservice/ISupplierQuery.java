package org.cocome.productsservice.microservice;

import org.cocome.productsservice.domain.ProductSupplier;
import java.util.Collection;

import javax.validation.constraints.NotNull;

import org.cocome.productsservice.domain.Product;

public interface ISupplierQuery {

	public boolean createSupplier(@NotNull String name);
	
	public ProductSupplier findSupplierById(@NotNull long id);
	
	public Collection<ProductSupplier> getAllSuppliers();
	
	
	
}
