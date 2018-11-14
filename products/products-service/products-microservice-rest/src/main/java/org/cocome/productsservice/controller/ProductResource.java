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

import org.cocome.productsclient.domain.ProductSupplierTO;
import org.cocome.productsclient.domain.ProductTO;
import org.cocome.productsservice.domain.Product;
import org.cocome.productsservice.domain.ProductSupplier;
import org.cocome.productsservice.productquery.IProductQuery;
import org.cocome.productsservice.repository.ProductRepository;
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
	private ProductRepository productRepository;
	
	@EJB 
	private IProductQuery productQuery;
	
	@EJB
	private ISupplierQuery supplierQuery;
	
	
	@GET
	@Path("/{id}")
	public ProductTO find(@PathParam("id") Long id) {
		Product product = productQuery.findProductByid(id);
		return toProductTO(product);
		
	}
	//TODO  Schnittstelle soll Query sein!
	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_XML)
	public Response update(@PathParam("id") Long id, ProductTO productTO) {
		Product product = fromProductTO(productTO);
		productQuery.updateProduct(product);
		return Response.noContent().build();
	}
	
	//TODO Schnittstelle query
	@DELETE
	@Path("/{id}")
	public Response delete(@PathParam("id") Long id) {
		productRepository.delete(id);
		return Response.noContent().build();
	}
	
	private  Product fromProductTO(ProductTO productTO) {
		Product product = productQuery.findProductByid(productTO.getId());
		product.setName(productTO.getName());
		product.setBarcode(productTO.getBarcode());
		product.setPurchasePrice(productTO.getPurchasePrice());
		product.setSupplier(fromSupplierTO(productTO.getSupplier()));
		return product;
	}
	
	private  ProductSupplier fromSupplierTO(ProductSupplierTO supplierTO) {
		ProductSupplier supplier = supplierQuery.findSupplierById(supplierTO.getId());
		supplier.setName(supplierTO.getName());
        return supplier;
		
		
	}
	
	private ProductTO toProductTO(Product product) {
		ProductTO productTO = new ProductTO();
		productTO.setId(product.getId());
		productTO.setName(product.getName());
		productTO.setBarcode(product.getBarcode());
		productTO.setPurchasePrice(product.getPurchasePrice());
		productTO.setSupplier(toSupplierTO(product.getSupplier()));
		
		return productTO;
		
	}
	
	private ProductSupplierTO toSupplierTO(ProductSupplier supplier) {
		ProductSupplierTO supplierTO = new ProductSupplierTO();
		supplierTO.setId(supplier.getId());
		supplierTO.setName(supplier.getName());
		return supplierTO;
	}
}
