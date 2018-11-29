package org.cocome.productsservice.controller;

import java.util.Collection;
import java.util.LinkedList;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.apache.log4j.Logger;
import org.cocome.productsclient.domain.ProductSupplierTO;
import org.cocome.productsclient.domain.ProductTO;
import org.cocome.productsservice.domain.Product;
import org.cocome.productsservice.domain.ProductSupplier;
import org.cocome.productsservice.exceptions.CreateException;
import org.cocome.productsservice.exceptions.QueryException;
import org.cocome.productsservice.productquery.IProductQuery;
import org.cocome.productsservice.supplierquery.ISupplierQuery;
/**
 * REST-interface for productssupplier queries and product-queries
 * @author Niko Benkler
 * @author Robert Heinrich
 *
 */
@RequestScoped
@Path("/product-suppliers")
public class ProductSupplierResource {

	@EJB
	private ISupplierQuery supplierQuery;

	@EJB
	private IProductQuery productQuery;

	private static final Logger LOG = Logger.getLogger(ProductSupplierResource.class);

	/**
	 * Find all suppliers
	 * @return
	 */
	@GET
	public Collection<ProductSupplierTO> findAll() {
		LOG.debug("REST: Retrieve all ProductSuppliers");
		Collection<ProductSupplierTO> collection = new LinkedList<ProductSupplierTO>();
		for (ProductSupplier supplier : supplierQuery.getAllSuppliers()) {
			collection.add(toSupplierTO(supplier));
		}
		return collection;
	}

	/**
	 * Fiond supplier by id
	 * @param id
	 * @return
	 */
	@GET
	@Path("/{id}")
	public ProductSupplierTO find(@PathParam("id") Long id) {
		LOG.debug("REST: Trying to find supplier with id: " + id);
		ProductSupplier supplier;
		try {
			supplier = supplierQuery.findSupplierById(id);
			return toSupplierTO(supplier);
		} catch (QueryException e) {
			LOG.debug("REST: " + e.getMessage());
			throw new NotFoundException(e.getMessage());
		}

	}

	/**
	 * Update supplier with given id. 
	 * @param id
	 * @param supplierTO
	 * @return
	 */
	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_XML)
	public Response update(@PathParam("id") Long id, ProductSupplierTO supplierTO) {

		LOG.debug("REST: Try to update Supplier with Id: " + id);
		supplierTO.setId(id);

		try {
			ProductSupplier supplier = supplierQuery.findSupplierById(id);
			supplier = fromSupplierTO(supplierTO, supplier);
			supplierQuery.updateSupplier(supplier);
			return Response.noContent().build();
		} catch (QueryException e) {
			LOG.debug("REST: Could not update supplier with id: " + id);
			throw new NotFoundException(e.getMessage());
		}

	}

	/**
	 * Delete supplier with id
	 * @param id
	 * @return
	 */
	@DELETE
	@Path("/{id}")
	public Response delete(@PathParam("id") Long id) {
		LOG.debug("REST: Try to delete supplier with id: " + id);

		try {
			supplierQuery.deleteSupplier(id);
			return Response.noContent().build();

		} catch (QueryException e) {
			LOG.debug("REST: " + e.getMessage());
			throw new NotFoundException(e.getMessage());
		}

	}

	/**
	 * Get all products from supplier with given id
	 * @param supplierId
	 * @return
	 */
	@GET
	@Path("/{id}/products")
	public Collection<ProductTO> getProductsBySupplierId(@PathParam("id") Long supplierId) {
		LOG.debug("REST: Trying to find Products from supplier with id: " + supplierId);

		ProductSupplier supplier;
		try {
			supplier = supplierQuery.findSupplierById(supplierId);
		} catch (QueryException e) {
			LOG.debug("REST: " + e.getMessage());
			throw new NotFoundException(e.getMessage());
		}

		Collection<ProductTO> collection = new LinkedList<ProductTO>();

		for (Product product : supplier.getProducts()) {
			collection.add(ProductResource.toProductTO(product));
		}

		return collection;
	}

	/**
	 * Create product for specific supplier. Cannot create product without supplier
	 * @param uriInfo
	 * @param supplierId
	 * @param productTO
	 * @return
	 */
	@POST
	@Path("/{id}/products")
	@Consumes(MediaType.APPLICATION_XML)
	public Response createProduct(@Context UriInfo uriInfo, @PathParam("id") Long supplierId, ProductTO productTO) {

		LOG.debug("REST: Trying to create Product for supplier with supplier id: " + supplierId);

		try {

			// find supplier for coresponsing product
			supplierQuery.findSupplierById(supplierId);

			// create Product
			Long productId = productQuery.createProduct(productTO.getName(), productTO.getBarcode(),
					productTO.getPurchasePrice(), supplierId);

			// return product id
			UriBuilder builder = UriBuilder.fromUri(uriInfo.getBaseUri()).path(ProductResource.class)
					.path(productId.toString());
			return Response.created(builder.build()).build();

		} catch (QueryException | CreateException e) {
			LOG.debug("REST: " + e.getMessage());
			throw new NotFoundException(e.getMessage());
		}

	}

	/**
	 * Create supplier (Only name! no Products)
	 * @param uriInfo
	 * @param supplierTO
	 * @return
	 */
	@POST
	@Consumes(MediaType.APPLICATION_XML)
	public Response createSupplier(@Context UriInfo uriInfo, ProductSupplierTO supplierTO) {
		LOG.debug("REST: Create supplier with name: " + supplierTO.getName());

		Long supplierId;
		try {
			supplierId = supplierQuery.createSupplier(supplierTO.getName());
			UriBuilder builder = UriBuilder.fromUri(uriInfo.getBaseUri()).path(ProductSupplierResource.class)
					.path(supplierId.toString());
			LOG.debug("Builder:" + builder.build());
			return Response.created(builder.build()).build();
		} catch (CreateException e) {

			LOG.debug("REST: " + e.getMessage());
			throw new NotFoundException("Could not create Supplier with name: " + supplierTO.getName());
		}

	}

	/**
	 * Helper method to change to supplier transfer object
	 * @param supplier
	 * @return
	 */
	public static ProductSupplierTO toSupplierTO(ProductSupplier supplier) {
		ProductSupplierTO supplierTO = new ProductSupplierTO();
		supplierTO.setId(supplier.getId());
		supplierTO.setName(supplier.getName());
		return supplierTO;
	}

	public static ProductSupplier fromSupplierTO(ProductSupplierTO supplierTO, ProductSupplier supplier) {
		supplier.setName(supplierTO.getName());
		return supplier;
	}

}
