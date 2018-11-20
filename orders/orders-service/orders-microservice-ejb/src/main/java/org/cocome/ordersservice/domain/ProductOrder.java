package org.cocome.ordersservice.domain;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The ProductOrder class represents an ProductOrder of a Store in the database.
 *
 * @author Yannick Welsch
 * @author Nils Sommer
 */
@Entity(name="ProductOrder")
@Table(name="productorder")
public class ProductOrder implements Serializable {


	private static final long serialVersionUID = -8340585715760459030L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private long id;
	
	@Basic
	@Temporal(TemporalType.TIMESTAMP)
	private Date deliveryDate;
	
	@Basic
	@Temporal(TemporalType.TIMESTAMP)
	private Date orderingDate;
	
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch=FetchType.EAGER)
	private Collection<OrderEntry> orderEntries;
	
	
	private long storeId;

	/** Cechkstyle basic constructor. */
	public ProductOrder() {}


	public long getId() {
		return this.id;
	}

	/**
	 * @param id
	 *            A unique identifier for ProductOrder objects
	 */
	public void setId(final long id) {
		this.id = id;
	}

	/**
	 * @return A list of OrderEntry objects (pairs of Product-Amount-pairs)
	 */
	
	public Collection<OrderEntry> getOrderEntries() {
		return this.orderEntries;
	}

	/**
	 * @param orderEntries
	 *            A list of OrderEntry objects (pairs of Product-Amount-pairs)
	 */
	public void setOrderEntries(final Collection<OrderEntry> orderEntries) {
		this.orderEntries = orderEntries;
	}
	
	public void addToOrder(OrderEntry oEntry) {
		orderEntries.add(oEntry);		
	}
	
	public void removeFromOrder(OrderEntry oEntry) {
		orderEntries.remove(oEntry);		
	}

	/**
	 * @return The date of ordering.
	 */
	public Date getOrderingDate() {
		return this.orderingDate;
	}

	/**
	 * @param orderingDate
	 *            the date of ordering
	 */
	public void setOrderingDate(final Date orderingDate) {
		this.orderingDate = orderingDate;
	}

	/**
	 * The delivery date is used for computing the mean time to delivery
	 *
	 * @return The date of order fulfillment.
	 */
	public Date getDeliveryDate() {
		return this.deliveryDate;
	}

	/**
	 * The delivery date is used for computing the mean time to delivery
	 *
	 * @param deliveryDate
	 *            the date of order fulfillment
	 */
	public void setDeliveryDate(final Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	/**
	 * @return The store where the order is placed.
	 */
	public long getStoreId() {
		return this.storeId;
	}

	/**
	 * @param store
	 *            the store where the order is placed
	 */
	public void setStoreId(final long storeId) {
		this.storeId = storeId;
	}

	@Override
	public String toString() {
		return "[Class:" + this.getClass().getSimpleName() + ",Id:" + this.getId() + ",StoreId:" + this.getStoreId() + " Entries: "  + this.getOrderEntries().toString() + "]";
	}

}

