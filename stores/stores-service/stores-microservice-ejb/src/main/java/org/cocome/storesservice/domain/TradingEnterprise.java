package org.cocome.storesservice.domain;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Represents a TradingEnterprise in the database. <br>
 * @OneToMany defines, that a Database can have [0..] stores. <br>
 * The mappedBy-attribute 
 * @author Niko Benkler
 * @author Robert Heinrich
 *
 */
@Entity(name = "TradingEnterprise")
@Table(name = "tradingenterprise")
public class TradingEnterprise implements Serializable {

	

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3795648096852898263L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private long id;

	@Column(name="name")
	private String name;


	@OneToMany(mappedBy = "enterprise", cascade = CascadeType.ALL, orphanRemoval = true)
	private Collection<Store> stores;

	/**
	 * @return id a unique identifier of this TradingEnterprise
	 */

	public long getId() {
		return id;
	}

	/**
	 * @param id a unique identifier of this TradingEnterprise
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
	 * @param name Name of this TradingEnterprise
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return Collection of Stores related to the TradingEnterprise
	 */
	public Collection<Store> getStores() {
		return stores;
	}

	/**
	 * @param stores Collection of Stores related to the TradingEnterprise
	 */
	public void setStores(Collection<Store> stores) {
		this.stores = stores;
	}
	
	public void addStore(Store store) {
		this.stores.add(store);
	}

	@Override
	public String toString() {
		return "[Class:" + getClass().getSimpleName() + ",Id" + getId() + ",Name:" + getName() + "]";
	}

}
