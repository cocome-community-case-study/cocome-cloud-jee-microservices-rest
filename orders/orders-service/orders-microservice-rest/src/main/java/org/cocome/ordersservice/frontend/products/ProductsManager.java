package org.cocome.ordersservice.frontend.products;

import java.io.Serializable;
import java.util.Collection;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.cocome.ordersservice.frontend.viewdata.ProductViewData;
import org.cocome.ordersservice.productquery.IProductQuery;
import org.cocome.productsclient.exception.ProductsRestException;

/**
 * This class represents the connection between server side Product
 * functionality and client side Sessions. We need to display all available
 * products when creating an order
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
	 * Return all available products from all supplierss
	 */
	@Override
	public Collection<ProductViewData> getAllProducts() {

		return products;

	}

	/**
	 * Backend Query that causes a REST-Call to Products service
	 */
	public void loadProducts() {
		try {
			products = ProductViewData.fromProductCollection(productQuery.getAllProducts());
		} catch (ProductsRestException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
		}
	}

}
