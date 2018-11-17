package org.cocome.productsservice.frontend.supplier;

import java.util.Collection;

import javax.validation.constraints.NotNull;

import org.cocome.productsservice.frontendViewData.SupplierViewData;


public interface ISupplierManager {
	
	public String createSupplier(@NotNull String supplierName);
	public Collection<SupplierViewData> getSuppliers();
	
}
