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

import org.cocome.productsservice.domain.Product;
import org.cocome.productsservice.domain.ProductSupplier;
import org.cocome.productsservice.repository.ProductRepository;
import org.cocome.productsservice.repository.ProductSupplierRepository;

@RequestScoped
@Path("/product-suppliers")
public class ProductSupplierResource {
	@EJB
	private ProductSupplierRepository productSupplierRepository;
	
	@EJB
	private ProductRepository productRepository;
	
	@GET
	@Path("/{id}")
	public ProductSupplier find(@PathParam("id") Long id) {
		return productSupplierRepository.find(id);
	}
	
	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_XML)
	public Response update(@PathParam("id") Long id, ProductSupplier supplier) {
		productSupplierRepository.update(supplier);
		return Response.noContent().build();
	}
	
	@DELETE
	@Path("/{id}")
	public Response delete(@PathParam("id") Long id) {
		ProductSupplier supplier = productSupplierRepository.find(id);
		productSupplierRepository.delete(supplier);
		return Response.noContent().build();
	}
	
	// Creating and fetching nested product resources
	
	@GET
	@Path("/{id}/products")
	public Collection<Product> getProducts(@PathParam("id") Long supplierId) {
		return productSupplierRepository.find(supplierId).getProducts();
	}
	
	@POST
	@Path("/{id}/products")
	@Consumes(MediaType.APPLICATION_XML)
	public Response createProduct(@Context UriInfo uriInfo, @PathParam("id") Long supplierId, Product product) {
		ProductSupplier supplier = productSupplierRepository.find(supplierId);
		product.setSupplier(supplier);
		Long productId = productRepository.create(product);
		UriBuilder builder = UriBuilder.fromUri(uriInfo.getBaseUri()).path(ProductResource.class).path(productId.toString());
		return Response.created(builder.build()).build();
	}
}
