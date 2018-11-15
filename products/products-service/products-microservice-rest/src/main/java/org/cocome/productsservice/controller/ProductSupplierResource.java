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
import org.cocome.productsservice.productquery.IProductQuery;
import org.cocome.productsservice.supplierquery.ISupplierQuery;

@RequestScoped
@Path("/product-suppliers")
public class ProductSupplierResource {

	@EJB
	private ISupplierQuery supplierQuery;

	@EJB
	private IProductQuery productQuery;

	private static final Logger LOG = Logger.getLogger(ProductSupplierResource.class);
	
	private final long COULD_NOT_CREATE_ENTITY = -1;

	@GET
	public Collection<ProductSupplierTO> findAll() {
		LOG.debug("REST: Retrieve all ProductSuppliers");
		Collection<ProductSupplierTO> collection = new LinkedList<ProductSupplierTO>();
		for (ProductSupplier supplier : supplierQuery.getAllSuppliers()) {
			collection.add(toSupplierTO(supplier));
		}
		return collection;
	}

	@GET
	@Path("/{id}")
	public ProductSupplierTO find(@PathParam("id") Long id) {
		LOG.debug("REST: Trying to find supplier with id: " + id);
		ProductSupplier supplier = supplierQuery.findSupplierById(id);
		if (supplier != null) {
			return toSupplierTO(supplier);
		}
		LOG.debug("REST: Did not find Supplier with id: " + id);
		throw new NotFoundException("Could not find supplier with Id: " + id);

	}

	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_XML)
	public Response update(@PathParam("id") Long id, ProductSupplierTO supplierTO) {
		LOG.debug("REST: Try to update Supplier with Id: " + id);
		supplierTO.setId(id);
		ProductSupplier supplier = supplierQuery.findSupplierById(id);
		if(supplier == null) {
			LOG.debug("REST: Could not update supplier with id: " + id + ".  SUpplier not found!");
			throw new NotFoundException("Could not update supplier with Id: " + id);
		}
		
		// We need to preserver ProductCollection of supplier
		supplier = fromSupplierTO(supplierTO, supplier);
		
		

		if (supplierQuery.updateSupplier(supplier)) {
			return Response.noContent().build();
		}
		LOG.debug("REST: Could not update supplier with id: " + id);
		throw new NotFoundException("Could not update supplier with Id: " + id);
	}

	@DELETE
	@Path("/{id}")
	public Response delete(@PathParam("id") Long id) {
		LOG.debug("REST: Try to delete supplier with id: " + id);
		if (supplierQuery.deleteSupplier(id)) {
			return Response.noContent().build();
		}
		LOG.debug("REST: Could not delete supplier with id: " + id);
		throw new NotFoundException("Could not delete supplier with Id: " + id);

	}

	@GET
	@Path("/{id}/products")
	public Collection<ProductTO> getProductsBySupplierId(@PathParam("id") Long supplierId) {
		LOG.debug("REST: Trying to find Products from supplier with id: " + supplierId);
		ProductSupplier supplier = supplierQuery.findSupplierById(supplierId);
		if (supplier == null) {
			LOG.debug("REST: Did not find Supplier with id: " + supplierId);
			throw new NotFoundException("Could not find supplier with Id: " + supplierId);
		}

		Collection<ProductTO> collection = new LinkedList<ProductTO>();

		for (Product product : supplier.getProducts()) {
			collection.add(ProductResource.toProductTO(product));
		}

		return collection;
	}

	@POST
	@Path("/{id}/products")
	@Consumes(MediaType.APPLICATION_XML)
	public Response createProduct(@Context UriInfo uriInfo, @PathParam("id") Long supplierId, ProductTO productTO) {

		LOG.debug("REST: Trying to create Product for supplier with supplier id: " + supplierId);
		if (supplierQuery.findSupplierById(supplierId) == null) {
			LOG.debug("REST: Could not create Product. Supplier not found with id: " + supplierId);
			throw new NotFoundException("Could not find supplier with Id: " + supplierId);
		}

		Long productId = productQuery.createProduct(productTO.getName(), productTO.getBarcode(), productTO.getPurchasePrice(),
				supplierId);
        if(productId == COULD_NOT_CREATE_ENTITY) {
        	LOG.debug("REST: Coudl not create Product with name: " + productTO.getName());
        	throw new NotFoundException("Could not create Product with name: " + productTO.getName());
        }
		
		UriBuilder builder = UriBuilder.fromUri(uriInfo.getBaseUri()).path(ProductResource.class)
				.path(productId.toString());
		return Response.created(builder.build()).build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_XML)
	public Response createSupplier(@Context UriInfo uriInfo, ProductSupplierTO supplierTO) {
		LOG.debug("REST: Create supplier with name: " + supplierTO.getName());
		Long supplierId = supplierQuery.createSupplier(supplierTO.getName());
		
		if(supplierId == COULD_NOT_CREATE_ENTITY) {
			LOG.debug("REST: Could not create Supplier with name: " + supplierTO.getName());
			throw new NotFoundException("Could not create Supplier with name: " + supplierTO.getName());
			
		}
		
		UriBuilder builder = UriBuilder.fromUri(uriInfo.getBaseUri()).path(ProductSupplierResource.class)
				.path(supplierId.toString());
		LOG.debug("Builder:" + builder.build());
		return Response.created(builder.build()).build();
	}

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
