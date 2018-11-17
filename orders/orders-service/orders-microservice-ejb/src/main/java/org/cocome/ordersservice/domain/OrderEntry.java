package org.cocome.ordersservice.domain;


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


/**
 * Represents a single product order entry in the database.
 *
 * @author Yannick Welsch
 * @author Nils Sommer
 */
@Entity(name="OrderEntry")
@Table(name="orderentry")
public class OrderEntry implements Serializable {

	
	private static final long serialVersionUID = -7683436740437770058L;


	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	@Column(name = "id", updatable = false, nullable = false)
	private long id;

	
	private long amount;

	
	private long productId;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "productorder_id")
	private ProductOrder order;

	/** Empty constructor. */
	public OrderEntry() {}


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
	@Basic
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
	
	public void addToOrder(OrderEntry oEntry) {
				
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
