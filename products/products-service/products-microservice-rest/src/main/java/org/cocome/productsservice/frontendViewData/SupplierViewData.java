package org.cocome.productsservice.frontendViewData;

import java.util.Collection;

import org.cocome.productsservice.domain.ProductSupplier;

/**
 * ProductSupplier Entity for Frontend. Static Functionality provided to change
 * in/from ProductSupplier Entity in Backend format
 * 
 * @author Niko Benkler
 * @author Robert Heinrich
 *
 */
public class SupplierViewData {

	private long id;
	private String name;
	private Collection<ProductViewData> products;
	private long enterpriseId;

	public SupplierViewData(long id, String name, Collection<ProductViewData> products) {

		this.id = id;
		this.name = name;
		this.products = products;
		this.enterpriseId = enterpriseId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Collection<ProductViewData> getProducts() {
		return products;
	}

	public void setProducts(Collection<ProductViewData> products) {
		this.products = products;
	}

	public static SupplierViewData fromSupplier(ProductSupplier supplier) {
		return new SupplierViewData(supplier.getId(), supplier.getName(),
				ProductViewData.fromProductCollection(supplier.getProducts()));

	}

}
