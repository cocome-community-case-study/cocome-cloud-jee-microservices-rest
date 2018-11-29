package org.cocome.productsservice.supplierquery;

import java.util.Collection;

import javax.validation.constraints.NotNull;

import org.cocome.productsservice.domain.ProductSupplier;
import org.cocome.productsservice.exceptions.CreateException;
import org.cocome.productsservice.exceptions.QueryException;

public interface ISupplierQuery {

	public long createSupplier(@NotNull String name) throws CreateException;
	
	public ProductSupplier findSupplierById(@NotNull long id) throws QueryException;
	
	public Collection<ProductSupplier> getAllSuppliers();
	
	public void updateSupplier(@NotNull ProductSupplier supplier) throws QueryException;
	
	public void deleteSupplier(@NotNull long id) throws QueryException;
	
	
	
}
