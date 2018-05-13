package org.cocome.productsclient.domain;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 * This class represents a Product in the database
 * 
 * @author Yannick Welsch
 * @author Nils Sommer
 */
@XmlRootElement(name = "Product")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Product", propOrder = { "name", "barcode", "purchasePrice", "supplier" })
public class Product implements Serializable{

	@XmlTransient
	private static final long serialVersionUID = -2577328715744776645L;

	@XmlTransient
	private long id;

	@XmlElement(name = "Barcode")
	private long barcode;

	@XmlElement(name = "PurchasePrice")
	private double purchasePrice;

	@XmlElement(name = "Name")
	private String name;

	@XmlElement(name = "Supplier")
	private ProductSupplier supplier;

	//

	/**
	 * Gets identifier value
	 * 
	 * @return The id.
	 */
	public long getId() {
		return id;
	}

	/**
	 * Sets identifier.
	 * 
	 * @param id
	 *            Identifier value.
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return The barcode of the product
	 */
	public long getBarcode() {
		return barcode;
	}

	/**
	 * @param barcode
	 *            The barcode of the product
	 */
	public void setBarcode(long barcode) {
		this.barcode = barcode;
	}

	/**
	 * @return The name of the product
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            The name of the product
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return The ProductSupplier of this product
	 */
	public ProductSupplier getSupplier() {
		return supplier;
	}

	/**
	 * @param supplier
	 *            The ProductSupplier of this product
	 */
	public void setSupplier(ProductSupplier supplier) {
		this.supplier = supplier;
	}

	/**
	 * @return The purchase price of this product
	 */
	public double getPurchasePrice() {
		return purchasePrice;
	}

	/**
	 * @param purchasePrice
	 *            The purchase price of this product
	 */
	public void setPurchasePrice(double purchasePrice) {
		this.purchasePrice = purchasePrice;
	}
	
	@Override
	public String toString() {
		return "[Class:" + getClass().getSimpleName() + ",Barcode:"
				+ this.getBarcode() + ",Name:" + this.getName() + ",Supplier:"
				+ this.getSupplier() + "]";
	}

}

