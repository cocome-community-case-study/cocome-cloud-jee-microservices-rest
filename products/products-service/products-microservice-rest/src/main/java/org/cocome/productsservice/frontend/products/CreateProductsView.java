package org.cocome.productsservice.frontend.products;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.cocome.productsservice.frontend.supplier.ISupplierManager;
import org.cocome.productsservice.frontendViewData.ProductViewData;
import org.cocome.productsservice.frontendViewData.SupplierViewData;

/**
 * This class is used while creating a Product. It provides a list of the suppliers that
 * are available, as a product can only be created when adding a supplier
 * 
 * @author Niko Benkler
 * @author Robert Heinrich
 *
 */
@Named
@ViewScoped
public class CreateProductsView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2219373026919017158L;

	@Inject
	IProductsManager productsManager;

	@Inject
	ISupplierManager supplierManager;

	private List<SupplierViewData> suppliers;
	private Collection<ProductViewData> products;
	private Set<Long> barcodes;
	private final static Logger LOG = Logger.getLogger(CreateProductsView.class);

	private boolean init = false;

	/*
	 * Retrieve suppliers and available barcodes from backend
	 */
	private void init() {
		suppliers = new ArrayList<SupplierViewData>(supplierManager.getSuppliers());
		products = productsManager.getAllProducts();
		barcodes = products.stream().map(ProductViewData::getBarcode).collect(Collectors.toCollection(HashSet::new));
		LOG.debug("init Executed with supplier ");

	}

	/**
	 * Return all available suppliers
	 * @return
	 */
	public Collection<SupplierViewData> getSuppliers() {
		if (!init)
			init();
		return suppliers;
	}

	/**
	 * Check if barcode already exists
	 * @param barcode
	 */
	//TODO  diese methode noch debuggen
	public void checkBarcode(long barcode) {
		if (!init)
			init();
		LOG.debug("Check barcode");
		if (checkIfBarcodeExists(barcode)) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error creating the new enterprise!", null));
		}

	}

//	/**
//	 * 
//	 * @return
//	 */
//	public Collection<ProductViewData> getProducts() {
	//  if(!init) init();
//		return products;
//	} TODO  Is it needed? 

	private boolean checkIfBarcodeExists(long barcode) {
		return barcodes.contains(barcode);

	}

}
