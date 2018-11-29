package org.cocome.productsservice.controller;

import java.util.Collection;
import java.util.LinkedList;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.cocome.productsclient.domain.ProductTO;
import org.cocome.productsservice.domain.Product;
import org.cocome.productsservice.exceptions.QueryException;
import org.cocome.productsservice.productquery.IProductQuery;
import org.cocome.productsservice.supplierquery.ISupplierQuery;

/**
 * REST-Controller to access Backend
 * 
 * @author Niko Benkler
 * @author Robert Heinrich
 *
 */
@RequestScoped
@Path("/products")
public class ProductResource {

	@EJB
	private IProductQuery productQuery;

	@EJB
	private ISupplierQuery supplierQuery;
	private static final Logger LOG = Logger.getLogger(ProductResource.class);

	/**
	 * Get all Products
	 * @return
	 */
	@GET
	public Collection<ProductTO> findAll() {
		LOG.debug("REST: Retrieve all Products");
		Collection<ProductTO> collection = new LinkedList<ProductTO>();
		for (Product product : productQuery.getAllProducts()) {
			collection.add(toProductTO(product));
		}
		return collection;
	}

	/**
	 * Find product with given Id
	 * @param id
	 * @return
	 */
	@GET
	@Path("/{id}")
	public ProductTO find(@PathParam("id") Long id) {

		LOG.debug("REST: Trying to find Product with id: " + id);

		try {
			Product product = productQuery.findProductByid(id);
			return toProductTO(product);
		} catch (QueryException e) {
			LOG.debug("REST: " + e.getMessage());
			throw new NotFoundException(e.getMessage());
		}

	}

	/**
	 * Update product with given id
	 * @param id
	 * @param productTO
	 * @return
	 */
	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_XML)
	public Response update(@PathParam("id") Long id, ProductTO productTO) {
		LOG.debug("REST: Try to update Product with Id: " + id);

		try {
			productQuery.updateProduct(id, productTO.getName(), productTO.getPurchasePrice(), productTO.getBarcode());
			return Response.noContent().build();
		} catch (QueryException e) {
			LOG.debug("REST: " + e.getMessage());
			throw new NotFoundException(e.getMessage());
		}

	}

	/**
	 * Delete product with given id
	 * @param id
	 * @return
	 */
	@DELETE
	@Path("/{id}")
	public Response delete(@PathParam("id") Long id) {
		LOG.debug("REST: Try to delete product with id: " + id);
		
		try {
			productQuery.deleteProduct(id);
			return Response.noContent().build();
		} catch (QueryException e) {
			LOG.debug("REST: " + e.getMessage());
			throw new NotFoundException(e.getMessage());

		}

		
	
	
	
	}

	public static Product fromProductTO(ProductTO productTO, Product product) {
		product.setName(productTO.getName());
		product.setPurchasePrice(productTO.getPurchasePrice());
		product.setBarcode(productTO.getBarcode());
		return product;

	}

	public static ProductTO toProductTO(Product product) {
		ProductTO productTO = new ProductTO();
		productTO.setId(product.getId());
		productTO.setName(product.getName());
		productTO.setBarcode(product.getBarcode());
		productTO.setPurchasePrice(product.getPurchasePrice());
		productTO.setSupplier(ProductSupplierResource.toSupplierTO(product.getSupplier()));

		return productTO;

	}

}
