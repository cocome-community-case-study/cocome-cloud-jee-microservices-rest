package org.cocome.productsservice.controller;

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
import org.cocome.productsservice.productquery.IProductQuery;
import org.cocome.productsservice.supplierquery.ISupplierQuery;

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
	private IProductQuery productQuery;
	
	@EJB
	private ISupplierQuery supplierQuery;
	private static final Logger LOG = Logger.getLogger(ProductResource.class);
	
	
	@GET
	@Path("/{id}")
	public ProductTO find(@PathParam("id") Long id) {
		
		LOG.debug("REST: Trying to find Product with id: " + id);
		Product product = productQuery.findProductByid(id);
		if (product != null) {
			return toProductTO(product);
		}
		LOG.debug("REST: Did not find Product with id: " + id);
		throw new NotFoundException("Could not find Product with Id: " + id);	
		
		
	}
	
	
	
	//TODO  Schnittstelle soll Query sein!
	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_XML)
	public Response update(@PathParam("id") Long id, ProductTO productTO) {
		LOG.debug("REST: Try to update Product with Id: " + id);
		productTO.setId(id);
		Product product = fromProductTO(productTO);

		if (productQuery.updateProduct(product)) {
			return Response.noContent().build();
		}
		LOG.debug("REST: Could not update Product with id: " + id);
		throw new NotFoundException("Could not update product with Id: " + id);
		
		
	}
	
	
	@DELETE
	@Path("/{id}")
	public Response delete(@PathParam("id") Long id) {
		
		
		LOG.debug("REST: Try to delete product with id: " + id);
		if (productQuery.deleteProduct(id)) {
			return Response.noContent().build();
		}
		LOG.debug("REST: Could not delete product with id: " + id);
		throw new NotFoundException("Could not delete product with Id: " + id);
		
	
	}
	
	public static Product fromProductTO(ProductTO productTO) {
		Product product = new Product();
		product.setId(productTO.getId());
		product.setName(productTO.getName());
		product.setPurchasePrice(productTO.getPurchasePrice());
		product.setBarcode(productTO.getBarcode());
		product.setSupplier(ProductSupplierResource.fromSupplierTO(productTO.getSupplier()));
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
