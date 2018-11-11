package org.cocome.productsservice.frontend.supplier;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.cocome.productsservice.domain.ProductSupplier;
import org.cocome.productsservice.frontendViewData.SupplierViewData;
import org.cocome.productsservice.microservice.ISupplierQuery;
import org.cocome.productsservice.navigation.NavigationElements;



@Named
@ApplicationScoped
public class SupplierManager implements ISupplierManager{
	
	@EJB
	ISupplierQuery supplierQuery;
	/*
	 * We might want to use cacheing here!
	 */
	private Map<Long, SupplierViewData> suppliers;
	

	@Override
	public String createSupplier(String supplierName) {
		if (supplierQuery.createSupplier(supplierName)) {

			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Successfully created the new supplier!", null));

			return NavigationElements.SHOW_SUPPLIERS.getNavigationOutcome();
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error creating the new enterprise!", null));

			return NavigationElements.PRODUCTS_MAIN.getNavigationOutcome();
		}
	}


	@Override
	public Collection<SupplierViewData> getSuppliers() {
		this.suppliers = new HashMap<Long, SupplierViewData>();
		
		for(ProductSupplier supplier : supplierQuery.getAllSuppliers()) {
			suppliers.put(supplier.getId(), SupplierViewData.fromSupplier(supplier));
		}
		return suppliers.values();
	}

}
