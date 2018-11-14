package org.cocome.productsservice.controller;

import java.util.Collection;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.cocome.productsclient.domain.ProductSupplierTO;
import org.cocome.productsservice.domain.Product;
import org.cocome.productsservice.domain.ProductSupplier;
import org.cocome.productsservice.repository.ProductRepository;
import org.cocome.productsservice.repository.ProductSupplierRepository;
import org.cocome.productsservice.supplierquery.ISupplierQuery;
import org.cocome.productsservice.supplierquery.SupplierQuery;

@RequestScoped
@Path("/product-suppliers")
public class ProductSupplierResource {
	@EJB
	private ProductSupplierRepository supplierRepo;
	
	@EJB
	private ProductRepository productRepository;
	
	@EJB
	private ISupplierQuery supplierQuery;
	
	//TODO Schnittstelle soll Query sein
	@GET
	@Path("/{id}")
	public ProductSupplierTO find(@PathParam("id") Long id) {
		ProductSupplier supplier = supplierQuery.findSupplierById(id);
		return toSupplierTO(supplier);
	}
	
	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_XML)
	public Response update(@PathParam("id") Long id, ProductSupplierTO supplierTO) {
		ProductSupplier supplier = fromSupplierTO(supplierTO);
		supplierQuery.updateSupplier(supplier);
		return Response.noContent().build();
	}
	
	//TODO use query
	@DELETE
	@Path("/{id}")
	public Response delete(@PathParam("id") Long id) {
		supplierRepo.delete(id);
		return Response.noContent().build();
	}
	
	// Creating and fetching nested product resources
	
	@GET
	@Path("/{id}/products")
	public Collection<Product> getProducts(@PathParam("id") Long supplierId) {
		return supplierRepo.find(supplierId).getProducts();
	}
	
	@POST
	@Path("/{id}/products")
	@Consumes(MediaType.APPLICATION_XML)
	public Response createProduct(@Context UriInfo uriInfo, @PathParam("id") Long supplierId, Product product) {
		ProductSupplier supplier = supplierRepo.find(supplierId);
		product.setSupplier(supplier);
		Long productId = productRepository.create(product);
		UriBuilder builder = UriBuilder.fromUri(uriInfo.getBaseUri()).path(ProductResource.class).path(productId.toString());
		return Response.created(builder.build()).build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_XML)
	public Response createSupplier(@Context UriInfo uriInfo,  ProductSupplierTO supplierTO) {
		ProductSupplier supplier = fromSupplierTO(supplierTO);
		Long supplierId = supplierRepo.create(supplier);
		UriBuilder builder = UriBuilder.fromUri(uriInfo.getBaseUri()).path(ProductSupplierResource.class).path(supplierId.toString());
		return Response.created(builder.build()).build();
	}
	
	
	private ProductSupplierTO toSupplierTO(ProductSupplier supplier) {
		ProductSupplierTO supplierTO = new ProductSupplierTO();
		supplierTO.setId(supplier.getId());
		supplierTO.setName(supplier.getName());
		return supplierTO;
	}
	
	private  ProductSupplier fromSupplierTO(ProductSupplierTO supplierTO) {
		ProductSupplier supplier = supplierQuery.findSupplierById(supplierTO.getId());
		supplier.setName(supplierTO.getName());
        return supplier;
		
		
	}
	
	
}
