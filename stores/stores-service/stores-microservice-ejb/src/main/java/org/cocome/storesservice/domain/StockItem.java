package org.cocome.storesservice.domain;

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
 * Represents a concrete product in the, store including sales price,
 * amount, ...
 *
 * @author Yannick Welsch
 */
@Entity(name="StockItem")
@Table(name="stockItem")
public class StockItem implements Serializable {

	private static final long serialVersionUID = -293179135307588628L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private long id;
	

	private double salesPrice;
	
	
	private long amount;
	
	
	private long minStock;


	private long maxStock;


	private long barcode;
	
	
	private long incomingAmount;
		
	
	private long productId;
	
	private String name;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "store_id")
	private Store store;

	/** Empty constructor. */
	public StockItem() {}


	public long getId() {
		return this.id;
	}

	/**
	 * @param id
	 *            a unique identifier of this StockItem
	 */
	public void setId(final long id) {
		this.id = id;
	}

	/**
	 * @return The currently available amount of items of a product
	 */
	@Basic
	public long getAmount() {
		return this.amount;
	}

	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	/**
	 * @param amount
	 *            the currently available amount of items of a product
	 */
	public void setAmount(final long amount) {
		this.amount = amount;
	}
	
	public long getBarcode() {
		return barcode;
	}
	
	/**
	 * @param amount
	 *            the Barcode of a product
	 */
	public void setBarcode(final long barcode) {
		this.barcode = barcode;
	}

	/**
	 * This method will be used while computing the low stock item list.
	 *
	 * @return The maximum capacity of a product in a store.
	 */
	@Basic
	public long getMaxStock() {
		return this.maxStock;
	}

	/**
	 * This method enables the definition of the maximum capacity of a product
	 * in a store.
	 *
	 * @param maxStock
	 *            the maximum capacity of a product in a store
	 */
	public void setMaxStock(final long maxStock) {
		this.maxStock = maxStock;
	}

	/**
	 * @return
	 * 		The minimum amount of products which has to be available in a
	 *         store.
	 */
	@Basic
	public long getMinStock() {
		return this.minStock;
	}

	/**
	 * @param minStock
	 *            the minimum amount of products which has to be available in a
	 *            store
	 */
	public void setMinStock(final long minStock) {
		this.minStock = minStock;
	}

	/**
	 * @return The sales price of the StockItem
	 */
	@Basic
	public double getSalesPrice() {
		return this.salesPrice;
	}

	/**
	 * @param salesPrice
	 *            the sales price of the StockItem
	 */
	public void setSalesPrice(final double salesPrice) {
		this.salesPrice = salesPrice;
	}

	/**
	 * Required for UC 8
	 *
	 * @return incomingAmount
	 *         the amount of products that will be delivered in the near future
	 */
	@Basic
	public long getIncomingAmount() {
		return this.incomingAmount;
	}

	/**
	 * Set the amount of products that will be delivered in the near future.
	 * <p>
	 * Required for UC 8
	 *
	 * @param incomingAmount
	 *            the absolute amount (no delta) of incoming products
	 */
	public void setIncomingAmount(final long incomingAmount) {
		this.incomingAmount = incomingAmount;
	}
	
	public void setProductId(long productId) {
		this.productId = productId;
	}
	
	public long getProductId() {
		return this.productId;
	}

	/**
	 * @return The store where the StockItem belongs to
	 */
	public Store getStore() {
		return this.store;
	}

	/**
	 * @param store
	 *            The store where the StockItem belongs to
	 */
	public void setStore(final Store store) {
		this.store = store;
	}

	@Override
	public String toString() {
//		return "[Class:" + this.getClass().getSimpleName() + ",Id" + this.getId() + ",Store:" + this.getStore() + ",Product:" + this.getProduct() + "]";
		return "[Class:" + this.getClass().getSimpleName() + ",Id" + this.getId() + ",Store:" + this.getStore().getId() + "]";
	}

}

