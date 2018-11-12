package org.cocome.productsservice.controller;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.cocome.productsservice.domain.Product;
import org.cocome.productsservice.microservice.IProductQuery;
import org.cocome.productsservice.repository.ProductRepository;

/**
 * REST-Controller to access Backend
 * @author Niko Benkler
 * @author Robert Heinrich
 *
 */
@RequestScoped
@Path("/products")
public class ProductResource {
	
	@EJB
	private ProductRepository productRepository;
	
	@EJB 
	private IProductQuery productQuery;
	
	@GET
	@Path("/{id}")
	public Product find(@PathParam("id") Long id) {
		return productQuery.findProductByid(id);
		
	}
	//TODO  Schnittstelle soll Query sein!
	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_XML)
	public Response update(@PathParam("id") Long id, Product product) {
		product.setId(id);
		productRepository.update(product);
		return Response.noContent().build();
	}
	
	@DELETE
	@Path("/{id}")
	public Response delete(@PathParam("id") Long id) {
		productRepository.delete(id);
		return Response.noContent().build();
	}
}
