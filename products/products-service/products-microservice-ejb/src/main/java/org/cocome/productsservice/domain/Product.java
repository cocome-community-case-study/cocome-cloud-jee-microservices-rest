package org.cocome.productsservice.domain;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 * This Class represents a product in the database. It has a ManyToOne
 * Relationship with the Entity ProductSupplier. The Join Column is the
 * supplier_id
 * 
 * @author Niko Benkler
 * @author Robert Heinrich
 *
 */
@Entity(name = "Product")
@Table(name = "product", uniqueConstraints = @UniqueConstraint(columnNames = { "barcode" }))
public class Product implements Serializable {

	private static final long serialVersionUID = -2577328715744776645L;

	
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	@Column(name = "id", updatable = false, nullable = false)
	private long id;

	
	private long barcode;

	
	private double purchasePrice;

	
	private String name;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "productsupplier_id")
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
	 * @param id Identifier value.
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return The barcode of the product
	 */
	@Basic
	public long getBarcode() {
		return barcode;
	}

	/**
	 * @param barcode The barcode of the product
	 */
	public void setBarcode(long barcode) {
		this.barcode = barcode;
	}

	/**
	 * @return The name of the product
	 */
	@Basic
	public String getName() {
		return name;
	}

	/**
	 * @param name The name of the product
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
	 * @param supplier The ProductSupplier of this product
	 */
	public void setSupplier(ProductSupplier supplier) {
		this.supplier = supplier;
	}

	/**
	 * @return The purchase price of this product
	 */
	@Basic
	public double getPurchasePrice() {
		return purchasePrice;
	}

	/**
	 * @param purchasePrice The purchase price of this product
	 */
	public void setPurchasePrice(double purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	@Override
	public String toString() {
		return "[Class:" + getClass().getSimpleName() + ",Barcode:" + this.getBarcode() + ",Name:" + this.getName()
				+ ",Supplier:" + this.getSupplier() + "]";
	}

}
