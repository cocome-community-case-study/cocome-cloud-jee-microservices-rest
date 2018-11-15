package org.cocome.productsservice.supplierquery;

import org.cocome.productsservice.domain.ProductSupplier;
import java.util.Collection;

import javax.validation.constraints.NotNull;

import org.cocome.productsservice.domain.Product;

public interface ISupplierQuery {

	public long createSupplier(@NotNull String name);
	
	public ProductSupplier findSupplierById(@NotNull long id);
	
	public Collection<ProductSupplier> getAllSuppliers();
	
	public boolean updateSupplier(@NotNull ProductSupplier supplier);
	
	public boolean deleteSupplier(@NotNull long id);
	
	
	
}
