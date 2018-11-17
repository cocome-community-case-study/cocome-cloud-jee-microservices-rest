package org.cocome.productsservice.domain;

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
public class ProductSupplier implements Serializable {

	private static final long serialVersionUID = 1L;

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private long id;

	
	private String name;

	
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
