package org.cocome.storesclient.domain;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlTransient;


/**
 * Represents a TradingEnterprise in the database.
 */
@XmlRootElement(name = "Enterprise")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Enterprise", propOrder = { "name" })
public class TradingEnterprise implements Serializable{

	private static final long serialVersionUID = 1L;

	@XmlTransient
	private long id;

	@XmlElement(name="Name")
	private String name;

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
	public String getName() {
		return name;
	}

	/**
	 * @param name Name of this TradingEnterprise
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "[Class:" + getClass().getSimpleName() + ",Id" + getId()
				+ ",Name:" + getName() + "]";
	}
}
