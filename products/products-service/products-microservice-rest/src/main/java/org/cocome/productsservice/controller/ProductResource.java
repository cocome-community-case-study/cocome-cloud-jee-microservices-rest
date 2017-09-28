package org.cocome.productsservice.controller;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.cocome.productsservice.domain.Product;
import org.cocome.productsservice.repository.ProductRepository;

@RequestScoped
@Path("/products")
public class ProductResource {
	@EJB
	private ProductRepository productRepository;
	
	@GET
	@Path("/{id}")
	public Product find(@PathParam("id") Long id) {
		return productRepository.find(id);
	}
	
	@PUT
	@Path("/{id}")
	public Response update(@PathParam("id") Long id) {
		Product product = productRepository.find(id);
		productRepository.update(product);
		return Response.noContent().build();
	}
	
	@DELETE
	@Path("/{id}")
	public Response delete(@PathParam("id") Long id) {
		Product product = productRepository.find(id);
		productRepository.delete(product);
		return Response.noContent().build();
	}
}
