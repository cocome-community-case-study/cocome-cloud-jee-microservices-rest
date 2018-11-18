package org.cocome.productsservice.frontend.products;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.cocome.productsservice.frontend.supplier.ISupplierManager;
import org.cocome.productsservice.frontend.viewdata.ProductViewData;
import org.cocome.productsservice.frontend.viewdata.SupplierViewData;

/**
 * This class is used while creating a Product. It provides a list of the
 * suppliers that are available, as a product can only be created when adding a
 * supplier
 * 
 * @author Niko Benkler
 * @author Robert Heinrich
 *
 */
@Named
@SessionScoped
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

	private long barcode;
	private boolean init = false;

	private boolean barcodeUnique = true;

	/*
	 * Retrieve suppliers and available barcodes from backend
	 */
	private void init() {
		if (init) {
			return;
		}
		suppliers = new ArrayList<SupplierViewData>(supplierManager.getSuppliers());
		products = productsManager.getAllProducts();
		barcodes = products.stream().map(ProductViewData::getBarcode).collect(Collectors.toCollection(HashSet::new));
		barcode = 0;
		LOG.debug("init Executed with supplier ");

	}

	/**
	 * Return all available suppliers
	 * 
	 * @return
	 */
	public Collection<SupplierViewData> getSuppliers() {
		init();
		return suppliers;
	}

	/**
	 * Check if barcode already exists
	 * 
	 * @param barcode
	 */
	// TODO diese methode noch debuggen
	public boolean checkBarcode(long barcode) {
		init();
		LOG.debug("Check barcode");
		if (barcodes.contains(barcode)) {

			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error creating the new enterprise!", null));
			return false;
		}
		return true;
	}



	public long getBarcode() {
		return barcode;
	}

	public void setBarcode(long barcode) {
		if(!checkBarcode(barcode)) {
			barcodeUnique = false;
		}
		barcodeUnique = true;
		this.barcode = barcode;
	}

	public boolean isBarcodeUnique() {
		return barcodeUnique;
	}

	

	
	

}
