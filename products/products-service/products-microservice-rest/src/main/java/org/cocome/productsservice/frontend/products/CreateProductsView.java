package org.cocome.productsservice.frontend.products;


import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.cocome.productsservice.frontend.supplier.ISupplierManager;
import org.cocome.productsservice.frontend.supplier.SupplierManager;
import org.cocome.productsservice.frontendViewData.ProductViewData;
import org.cocome.productsservice.frontendViewData.SupplierViewData;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import java.util.stream.Collectors;

@Named
@ViewScoped
public class CreateProductsView implements Serializable{

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
	
	private void init() {
		suppliers = new ArrayList<SupplierViewData>(supplierManager.getSuppliers());
		products = productsManager.getAllProducts();
		barcodes = products.stream().map(ProductViewData::getBarcode).collect(Collectors.toCollection(HashSet::new));
		LOG.debug("init Executed with supplier " );
		
	}


	public Collection<SupplierViewData> getSuppliers() {
		if(!init) init();
		return suppliers;
	}

	
	public void checkBarcode(long barcode) {
		if(!init) init();
		LOG.debug("Check barcode");
		if (checkIfBarcodeExists(barcode)) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error creating the new enterprise!", null));
		}
		
	}

	public Collection<ProductViewData> getProducts() {
		return products;
	}

	private boolean checkIfBarcodeExists(long barcode) {
		return barcodes.contains(barcode);

	}

}
