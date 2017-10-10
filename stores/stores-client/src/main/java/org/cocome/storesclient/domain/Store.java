package org.cocome.storesclient.domain;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Represents a store in the database.
 *
 * @author Yannick Welsch
 */
@XmlRootElement(name = "Store")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Store", propOrder = { "id", "name", "location" })
public class Store implements Serializable {

	private static final long serialVersionUID = 1L;

	@XmlElement(name = "Id")
	private long id;

	@XmlElement(name = "Name")
	private String name;

	@XmlElement(name = "Location")
	private String location;

	/** Empty constructor. */
	public Store() {}

	/**
	 * @return A unique identifier for Store objects
	 */
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

	@Override
	public String toString() {
		return "[Id:" + this.getId() + ",Name:" + this.getName() + ",Location:" + this.getLocation() + "]";
	}
}

