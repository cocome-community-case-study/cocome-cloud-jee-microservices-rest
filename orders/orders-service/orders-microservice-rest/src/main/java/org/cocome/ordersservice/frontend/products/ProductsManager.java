package org.cocome.ordersservice.frontend.products;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
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
@ApplicationScoped
public class ProductsManager implements IProductsManager {

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


	/**
	 * Return all available products from all supplierss
	 */
	@Override
	public Collection<ProductViewData> getAllProducts() {
		products = ProductViewData.fromProductCollection(productQuery.getAllProducts());
		return products;

	}

	
}
