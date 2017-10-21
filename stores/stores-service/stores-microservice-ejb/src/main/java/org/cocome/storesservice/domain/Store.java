package org.cocome.storesservice.domain;


import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;


/**
 * Represents a store in the database.
 *
 * @author Yannick Welsch
 */
@Entity
@XmlRootElement(name = "Store")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Store", propOrder = { "id", "name", "location", "enterprise" })
public class Store implements Serializable, Comparable<Store> {

	private static final long serialVersionUID = 1L;

	@XmlElement(name = "Id")
	private long id;

	@XmlElement(name = "Name")
	private String name;

	@XmlElement(name = "Location")
	private String location;

	@XmlElement(name = "Enterprise")
	private TradingEnterprise enterprise;

	@XmlTransient
	private Collection<StockItem> stockItems;

	/** Empty constructor. */
	public Store() {}

	/**
	 * @return A unique identifier for Store objects
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long getId() {
		return this.id;
	}

	/**
	 * @param id
	 *            A unique identifier for Store objects
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
	 * @param name
	 *            the name of the Store
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
	 * @param location
	 *            store location
	 */
	public void setLocation(final String location) {
		this.location = location;
	}

	/**
	 * @return The enterprise which the Store belongs to
	 */
	@ManyToOne
	public TradingEnterprise getEnterprise() {
		return this.enterprise;
	}

	/**
	 * @param enterprise
	 *            The enterprise which the Store belongs to
	 */
	public void setEnterprise(final TradingEnterprise enterprise) {
		this.enterprise = enterprise;
	}

	/**
	 * @return
	 * 		A list of StockItem objects. A StockItem represents a concrete
	 *         product in the store including sales price, ...
	 */
	@OneToMany(mappedBy = "store", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	public Collection<StockItem> getStockItems() {
		return this.stockItems;
	}

	/**
	 * @param stockItems
	 *            A list of StockItem objects. A StockItem represents a concrete
	 *            product in the store including sales price, ...
	 */
	public void setStockItems(final Collection<StockItem> stockItems) {
		this.stockItems = stockItems;
	}

	@Override
	public String toString() {
		return "[Id:" + this.getId() + ",Name:" + this.getName() + ",Location:" + this.getLocation() + "]";
	}

	@Override
	public int compareTo(final Store o) {
		if (this.getEnterprise().getName().equals(o.getEnterprise().getName())
				&& this.getName().equals(o.getName())
				&& this.getLocation().equals(o.getLocation()))
			return 0;
		return 1;
	}
}
