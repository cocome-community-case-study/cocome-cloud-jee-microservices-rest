package org.cocome.productsclient.domain;

import java.io.Serializable;
import java.util.Collection;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 * This class represents a ProductSupplier in the database.
 * 
 * @author Yannick Welsch
 * @author Nils Sommer
 */
@XmlRootElement(name = "Supplier")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Supplier", propOrder = { "name" })
public class ProductSupplierTO implements Serializable{

	@XmlTransient
	private static final long serialVersionUID = 1L;

	@XmlTransient
	private long id;

	@XmlElement(name = "Name")
	private String name;
	
	

	/**
	 * @return A unique identifier for ProductSupplier objects
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id
	 *            A unique identifier for ProductSupplier objects
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return The name of the ProductSupplier
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            The name of the ProductSupplier
	 */
	public void setName(String name) {
		this.name = name;
	}
	

	
	@Override
	public String toString() {
		return "[Class:" + getClass().getSimpleName() + ",Id" + getId()
				+ ",Name:" + this.getName() + "]";
	}
}

