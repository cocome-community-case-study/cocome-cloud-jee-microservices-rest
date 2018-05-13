package org.cocome.productsservice.domain;


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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 * This class represents a ProductSupplier in the database.
 * 
 * @author Yannick Welsch
 * @author Nils Sommer
 */
@Entity
@XmlRootElement(name = "Supplier")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Supplier", propOrder = { "name" })
public class ProductSupplier implements Serializable{

	@XmlTransient
	private static final long serialVersionUID = 1L;

	@XmlTransient
	private long id;

	@XmlElement(name = "Name")
	private String name;

	@XmlTransient
	private Collection<Product> products;
	
	@XmlTransient
	private long enterpriseId;

	/**
	 * @return A unique identifier for ProductSupplier objects
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
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
	@Basic
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

	/**
	 * @return The list of Products provided by the ProductSupplier
	 */
	@OneToMany(mappedBy = "supplier", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	public Collection<Product> getProducts() {
		return products;
	}
	
	public void addProduct(Product product) {
		products.add(product);
	}

	/**
	 * @param products
	 *            The list of Products provided by the ProductSupplier
	 */
	public void setProducts(Collection<Product> products) {
		this.products = products;
	}
	
	public long getEnterpriseId() {
		return enterpriseId;
	}
	
	public void setEnterpriseId(long enterpriseId) {
		this.enterpriseId = enterpriseId;
	}
	
	@Override
	public String toString() {
		return "[Class:" + getClass().getSimpleName() + ",Id" + getId()
				+ ",Name:" + this.getName() + "]";
	}
}
