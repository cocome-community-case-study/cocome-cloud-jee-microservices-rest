package org.cocome.storesclient.domain;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Store Object as Transfer Object. It only has a reference to its enterprise
 * @author Niko Benkler
 * @author Robert Heinrich
 *
 */
@XmlRootElement(name = "Store")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Store", propOrder = { "id" ,"name", "location","enterpriseId" })
public class StoreTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@XmlElement(name="id")
	private long id;

	@XmlElement(name = "Name")
	private String name;

	@XmlElement(name = "Location")
	private String location;
	
	@XmlElement(name="EnterpriseId")
	private long enterpriseId;

	/** Empty constructor. */
	public StoreTO() {}

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

	public void setEnterpriseId(long enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	public long getEnterpriseId() {
		return enterpriseId;
	}

	@Override
	public String toString() {
		return "[Id:" + this.getId() + ",Name:" + this.getName() + ",Location:" + this.getLocation() + "]";
	}
}

