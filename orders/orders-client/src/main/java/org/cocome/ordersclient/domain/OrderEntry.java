package org.cocome.ordersclient.domain;


import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;


/**
 * Represents a single product order entry in the database.
 *
 * @author Yannick Welsch
 * @author Nils Sommer
 */
@XmlRootElement(name = "OrderEntry")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrderEntry", propOrder = { "amount", "productId" })
public class OrderEntry implements Serializable {

	@XmlTransient
	private static final long serialVersionUID = -7683436740437770058L;

	@XmlTransient
	private long id;

	@XmlElement(name = "Amount")
	private long amount;

	@XmlElement(name = "ProductId")
	private long productId;

	@XmlTransient
	private ProductOrder order;

	/** Empty constructor. */
	public OrderEntry() {}

	/**
	 * Gets identifier value
	 *
	 * @return The id.
	 */
	public long getId() {
		return this.id;
	}

	/**
	 * Sets identifier.
	 *
	 * @param id
	 *            Identifier value.
	 */
	public void setId(final long id) {
		this.id = id;
	}

	/**
	 * @return The amount of ordered products
	 */
	public long getAmount() {
		return this.amount;
	}

	/**
	 * @param amount
	 *            The amount of ordered products
	 */
	public void setAmount(final long amount) {
		this.amount = amount;
	}

	/**
	 * @return The ProductOrder where the OrderEntry belongs to
	 */
	public ProductOrder getOrder() {
		return this.order;
	}

	/**
	 * @param productOrder
	 *            The ProductOrder where the OrderEntry belongs to
	 */
	public void setOrder(final ProductOrder productOrder) {
		this.order = productOrder;
	}

	/**
	 * @return The product which is ordered
	 */
	public long getProductId() {
		return this.productId;
	}

	/**
	 * @param product
	 *            The product which is ordered
	 */
	public void setProductId(final long productId) {
		this.productId = productId;
	}

	@Override
	public String toString() {
		return "[Class:" + this.getClass().getSimpleName() + ",Id" + this.getId()
				+ ",ProductId:" + this.productId + ",ProductOrder:" + this.order + "]";
	}

}

