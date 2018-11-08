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
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Represents a TradingEnterprise in the database.
 */
@Entity
@XmlRootElement(name = "Enterprise")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Enterprise", propOrder = {"id", "name","stores"})
public class TradingEnterprise implements Serializable{

	private static final long serialVersionUID = 1L;

	@XmlElement(name="Id")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@XmlElement(name="Name")
	private String name;

	@XmlElementWrapper(name="Stores")
	@XmlElement(name="Store")
	private Collection<Store> stores;

	/**
	 * @return id a unique identifier of this TradingEnterprise
	 */
	
	public long getId() {
		return id;
	}

	/**
	 * @param id
	 *            a unique identifier of this TradingEnterprise
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return Name of this TradingEnterprise
	 */
	@Basic
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            Name of this TradingEnterprise
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return Collection of Stores related to the TradingEnterprise
	 */
	@OneToMany(mappedBy = "enterprise", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	public Collection<Store> getStores() {
		return stores;
	}

	/**
	 * @param stores
	 *            Collection of Stores related to the TradingEnterprise
	 */
	public void setStores(Collection<Store> stores) {
		this.stores = stores;
	}
	
	@Override
	public String toString() {
		return "[Class:" + getClass().getSimpleName() + ",Id" + getId()
				+ ",Name:" + getName() + "]";
	}

}

