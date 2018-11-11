package org.cocome.productsservice.frontendViewData;

import java.util.Collection;
import java.util.LinkedList;

import org.cocome.productsservice.domain.Product;

/**
 * Product Entity for Frontend. Static Functionality provided to change in/from Product Entity in Backend format
 * @author Niko Benkler
 * @author Robert Heinrich
 *
 */
public class ProductViewData {

	private long id;
	private long barcode;
	private double purchasePrice;
	private String name;
	private long supplierId;

	public ProductViewData(long id, long barcode, double purchasePrice, String name, long supplierId) {
		super();
		this.id = id;
		this.barcode = barcode;
		this.purchasePrice = purchasePrice;
		this.name = name;
		this.supplierId = supplierId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getBarcode() {
		return barcode;
	}

	public void setBarcode(long barcode) {
		this.barcode = barcode;
	}

	public double getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(double purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(long supplierId) {
		this.supplierId = supplierId;
	}

	public static ProductViewData fromProduct(Product product) {
		return new ProductViewData(product.getId(), product.getBarcode(), product.getPurchasePrice(), product.getName(),
				product.getSupplier().getId());
	}
	
	public static Collection<ProductViewData> fromProductCollection(Collection<Product> products){
		Collection<ProductViewData>  productViewDataColection = new LinkedList<>();
		
		for(Product product : products) {
			productViewDataColection.add(ProductViewData.fromProduct(product));
		}
		return productViewDataColection;
	}

}
