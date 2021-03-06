package org.cocome.storesservice.domain;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Represents a store in the database. <br>
 * The @ManyToOne relationship defines a bidirectional @OneToMany relationship
 * with the enterprise as on store belong to exactly on enterprise
 * @author Niko Benkler
 * @author Robert Heinrich
 *
 */
@Entity(name = "Store")
@Table(name = "store")
public class Store implements Serializable, Comparable<Store> {


	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5713187846460131843L;


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private long id;


	private String name;

	
	private String location;

	/*
	 * The @JoinColumn states, that the Tables enterprise and store are joined using
	 * the enterprise_if field in the store
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tradingenterprise_id")
	private TradingEnterprise enterprise;

	@OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
	private Collection<StockItem> stockItems;

	/** Empty constructor. */
	public Store() {
	}

	/**
	 * @return A unique identifier for Store objects
	 */

	public long getId() {
		return this.id;
	}

	/**
	 * @param id A unique identifier for Store objects
	 */
	public void setId(final long id) {
		this.id = id;
	}

	/**
	 * Returns the name of the store.
	 *
	 * @return Store name.
	 */
	@Basic
	public String getName() {
		return this.name;
	}

	/**
	 * @param name the name of the Store
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * Returns the location of the store.
	 *
	 * @return Store location.
	 */
	@Basic
	public String getLocation() {
		return this.location;
	}

	/**
	 * Sets the location of the store.
	 *
	 * @param location store location
	 */
	public void setLocation(final String location) {
		this.location = location;
	}

	/**
	 * @return The enterprise which the Store belongs to
	 */
	public TradingEnterprise getEnterprise() {
		return this.enterprise;
	}

	/**
	 * @param enterprise The enterprise which the Store belongs to
	 */
	public void setEnterprise(final TradingEnterprise enterprise) {
		this.enterprise = enterprise;
	}

	/**
	 * @return A list of StockItem objects. A StockItem represents a concrete
	 *         product in the store including sales price, ...
	 */

	public Collection<StockItem> getStockItems() {
		return this.stockItems;
	}

	/**
	 * @param stockItems A list of StockItem objects. A StockItem represents a
	 *                   concrete product in the store including sales price, ...
	 */
	public void setStockItems(Collection<StockItem> stockItems) {
		this.stockItems = stockItems;
	}
	
	public void addStockItems(StockItem item) {
		if(stockItems == null) {
			stockItems = new LinkedList<StockItem>();
		}
		stockItems.add(item);
	}

	@Override
	public String toString() {
		return "[Id:" + this.getId() + ",Name:" + this.getName() + ",Location:" + this.getLocation() + "]";
	}

	@Override
	public int compareTo(final Store o) {
		if (this.getEnterprise().getName().equals(o.getEnterprise().getName()) && this.getName().equals(o.getName())
				&& this.getLocation().equals(o.getLocation()))
			return 0;
		return 1;
	}
}
