package org.cocome.productsservice.controller;

import java.util.Collection;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.cocome.productsservice.domain.ProductSupplier;
import org.cocome.productsservice.repository.ProductSupplierRepository;

/**
 * This helper resource is needed to fetch and create suppliers. To interact with enterprises, refer to the equally named resource
 * class of the storesservice.
 * 
 * @author Nils Sommer
 *
 */
@RequestScoped
@Path("/trading-enterprises")
class TradingEnterpriseResource {
	@EJB
	private ProductSupplierRepository supplierRepository;
	
	@GET
	@Path("/{id}/product-suppliers")
	public Collection<ProductSupplier> getProductSuppliers(@PathParam("id") Long enterpriseId) {
		return null; // TODO: Implement me!
	}
	
	@POST
	@Path("/{id}/product-suppliers")
	public Response createSupplier(@Context UriInfo uriInfo, @PathParam("id") Long enterpriseId, ProductSupplier supplier) {
		supplier.setEnterpriseId(enterpriseId);
		Long supplierId = supplierRepository.create(supplier);
		UriBuilder builder = UriBuilder.fromUri(uriInfo.getBaseUri()).path(ProductSupplierResource.class).path(supplierId.toString());
		return Response.created(builder.build()).build();
	}
 }
