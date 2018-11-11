package org.cocome.productsservice.domain;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 * This class represents a product supplier. It has a @OneToMany relationship
 * with a product. Products and their supplier are independent of any
 * enterprises, which means, that a product can be sold in any enterprise
 * 
 * @author Niko Benkler
 * @author Robert Heinrich
 *
 */
@Entity(name = "ProductSupplier")
@Table(name = "productsupplier")
@XmlRootElement(name = "Supplier")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Supplier", propOrder = { "id", "name", "products" })
public class ProductSupplier implements Serializable {

	private static final long serialVersionUID = 1L;

	@XmlElement(name = "Id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private long id;

	@XmlElement(name = "Name")
	private String name;

	@XmlElementWrapper(name = "Stores")
	@XmlElement(name = "Store")
	@OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL, orphanRemoval = true)
	private Collection<Product> products;

	/**
	 * @return A unique identifier for ProductSupplier objects
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id A unique identifier for ProductSupplier objects
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
	 * @param name The name of the ProductSupplier
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return The list of Products provided by the ProductSupplier
	 */

	public Collection<Product> getProducts() {
		return products;
	}

	public void addProduct(Product product) {
		products.add(product);
	}

	/**
	 * @param products The list of Products provided by the ProductSupplier
	 */
	public void setProducts(Collection<Product> products) {
		this.products = products;
	}

	@Override
	public String toString() {
		return "[Class:" + getClass().getSimpleName() + ",Id" + getId() + ",Name:" + this.getName() + "]";
	}
}
