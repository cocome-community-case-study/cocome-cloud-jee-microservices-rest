package org.cocome.productsservice.frontend.products;

import java.util.Collection;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.cocome.productsservice.frontendViewData.ProductViewData;
import org.cocome.productsservice.microservice.IProductQuery;
import org.cocome.productsservice.navigation.NavigationElements;

@Named
@ApplicationScoped
public class ProductsManager implements IProductsManager{
	
	/**
	 * We might want to do Caching here
	 */
	private Collection<ProductViewData> products;
    
	@EJB
	IProductQuery productQuery;
	
	@Override
	public String createProduct(String name, long barcode, double purchasePrice, long supplierId) {
		if (productQuery.createProduct(name, barcode, purchasePrice, supplierId)) {

			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Successfully created the new supplier!", null));

			return NavigationElements.SHOW_PRODUCTS.getNavigationOutcome();
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error creating the new enterprise!", null));

			return NavigationElements.PRODUCTS_MAIN.getNavigationOutcome();
		}
		
	}

	@Override
	public Collection<ProductViewData> getAllProducts() {
		products = ProductViewData.fromProductCollection(productQuery.getAllProducts());
		return products;
		
	}

}
