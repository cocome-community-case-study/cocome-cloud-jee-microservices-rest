package org.cocome.productsservice.frontend.products;

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
import org.cocome.productsservice.frontend.supplier.ISupplierManager;
import org.cocome.productsservice.frontendViewData.ProductViewData;
import org.cocome.productsservice.microservice.IProductQuery;
import org.cocome.productsservice.navigation.NavigationElements;

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
	private Set<Long> barcodes;
	

	private final static Logger LOG = Logger.getLogger(CreateProductsView.class);

	@EJB
	IProductQuery productQuery;

	@Inject
	ISupplierManager supplierManager;

	/**
	 * Create Product. All Parameters are required, especially supplierId
	 *
	 */
	@Override
	public String createProduct(@NotNull String name, @NotNull long barcode, @NotNull double purchasePrice,
			@NotNull long supplierId) {

		// check if barcode exists already
		if (checkBarcode(barcode)) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Cannot create Product! Barcode already exists!", null));
			return NavigationElements.CREATE_PRODUCT.getNavigationOutcome();

		}

		if (productQuery.createProduct(name, barcode, purchasePrice, supplierId)) {

			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Successfully created the new Product!", null));

			return NavigationElements.SHOW_PRODUCTS.getNavigationOutcome();
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error creating the new Product!", null));

			return NavigationElements.PRODUCTS_MAIN.getNavigationOutcome();
		}

	}

	/**
	 * Return all available products from all supplierss
	 */
	@Override
	public Collection<ProductViewData> getAllProducts() {
		products = ProductViewData.fromProductCollection(productQuery.getAllProducts());
		return products;

	}

	/*
	 * This checks if a barcode already exists in database <br> For now, we retrieve
	 * all products each time we check it <br> Might be cached if performance is too
	 * slow <br> return true if already exists, false otherwise
	 */
	private boolean checkBarcode(long barcode) {
		LOG.debug("Check barcode");
		products = getAllProducts();
		barcodes = products.stream().map(ProductViewData::getBarcode).collect(Collectors.toCollection(HashSet::new));
		return barcodes.contains(barcode);

	}

}
