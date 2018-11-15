package org.cocome.storesclient.domain;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlTransient;



/**
 * Represents a concrete product in the, store including sales price,
 * amount, ...
 *
 * @author Yannick Welsch
 */
@XmlRootElement(name = "StockItem")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StockItem", propOrder = { "productId", "store", "salesPrice", "amount", "minStock", "maxStock", "incomingAmount" })
public class StockItemTO implements Serializable {

	private static final long serialVersionUID = -293179135307588628L;
	
	@XmlTransient
	private long id;
	
	@XmlElement(name = "SalesPrice")
	private double salesPrice;
	
	@XmlElement(name = "Amount")
	private long amount;
	
	@XmlElement(name = "MinStock")
	private long minStock;
	
	@XmlElement(name = "MaxStock")
	private long maxStock;
	
	@XmlElement(name = "IncomingAmount")
	private long incomingAmount;
		
	@XmlElement(name = "ProductId")
	private long productId;
	
	@XmlElement(name = "Store")
	private StoreTO store;

	/** Empty constructor. */
	public StockItemTO() {}

	/**
	 * @return A unique identifier of this StockItem.
	 */
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
	public long getAmount() {
		return this.amount;
	}

	/**
	 * @param amount the currently available amount of items of a product
	 */
	public void setAmount(final long amount) {
		this.amount = amount;
	}

	/**
	 * This method will be used while computing the low stock item list.
	 *
	 * @return The maximum capacity of a product in a store.
	 */
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
	public long getMinStock() {
		return this.minStock;
	}

	/**
	 * @param minStock the minimum amount of products which has to be available in a store
	 */
	public void setMinStock(final long minStock) {
		this.minStock = minStock;
	}

	/**
	 * @return The sales price of the StockItem
	 */
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
	public StoreTO getStore() {
		return this.store;
	}

	/**
	 * @param store
	 *            The store where the StockItem belongs to
	 */
	public void setStore(final StoreTO store) {
		this.store = store;
	}

	@Override
	public String toString() {
		return "[Class:" + this.getClass().getSimpleName() + ",Id" + this.getId() + "]";
	}
}
