package org.cocome.productsservice.frontend.supplier;

import javax.validation.constraints.NotNull;

import org.cocome.productsservice.frontendViewData.SupplierViewData;

import java.util.Collection;


public interface ISupplierManager {
	
	public String createSupplier(@NotNull String supplierName);
	public Collection<SupplierViewData> getSuppliers();
	
}
