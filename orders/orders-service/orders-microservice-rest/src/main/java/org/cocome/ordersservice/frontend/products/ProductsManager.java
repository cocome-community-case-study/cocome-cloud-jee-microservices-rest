package org.cocome.ordersservice.frontend.products;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import org.apache.log4j.Logger;
import org.cocome.ordersservice.frontend.viewdata.ProductViewData;
import org.cocome.ordersservice.productquery.IProductQuery;


/**
 * This class represents the connection between server side Product
 * functionality and client side Sessions
 * 
 * @author Niko Benkler
 * @author Robert Heinrich
 *
 */
@Named
@SessionScoped
public class ProductsManager implements IProductsManager, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4378791480143435358L;




	/**
	 * We might want to do Caching here
	 */
	private Collection<ProductViewData> products;
	
	


	@EJB
	IProductQuery productQuery;

	/**
	 * Create Product. All Parameters are required, especially supplierId
	 *
	 */
	
	@PostConstruct
	private void postConstruct() {
		products = ProductViewData.fromProductCollection(productQuery.getAllProducts());
	}


	/**
	 * Return all available products from all supplierss
	 */
	@Override
	public Collection<ProductViewData> getAllProducts() {
		
		return products;

	}

	
}
