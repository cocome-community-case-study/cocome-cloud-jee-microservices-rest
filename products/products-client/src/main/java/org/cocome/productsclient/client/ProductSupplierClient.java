package org.cocome.productsclient.client;

import java.net.URI;
import java.util.Collection;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.cocome.productsclient.config.Config;
import org.cocome.productsclient.domain.ProductSupplierTO;

public class ProductSupplierClient {
	private final WebTarget webTarget;
	
	public ProductSupplierClient() {
		Client client = ClientBuilder.newClient();
		this.webTarget = client.target(Config.getBaseUri());
	}
	
	public Collection<ProductSupplierTO> findAll() {
		return this.webTarget.path("product-suppliers")
							 .request()
							 .accept(MediaType.APPLICATION_XML_TYPE)
							 .get(new GenericType<Collection<ProductSupplierTO>> () {});
	}
	
	public ProductSupplierTO find(long id) {
		return this.webTarget.path("product-suppliers")
							 .path(Long.toString(id))
							 .request()
							 .accept(MediaType.APPLICATION_XML_TYPE)
							 .get(ProductSupplierTO.class);
	}
	
	public long create(ProductSupplierTO supplier) {
		Response response = this.webTarget.path("product-suppliers")
										 .request(MediaType.APPLICATION_XML_TYPE)
										 .post(Entity.xml(supplier));
		URI uri = URI.create(response.getHeaderString("Location"));
		Long id = Long.valueOf(uri.getPath().substring(uri.getPath().lastIndexOf('/') + 1));
		return id;
	}
	
	public boolean update(ProductSupplierTO supplier) {
		Response response = this.webTarget.path("product-suppliers")
			  .path(Long.toString(supplier.getId()))
			  .request(MediaType.APPLICATION_XML_TYPE)
			  .put(Entity.xml(supplier));
		return response.getStatus() == Response.Status.NO_CONTENT.getStatusCode();
	}
	
	public boolean delete(ProductSupplierTO supplier) {
		Response response = this.webTarget.path("product-suppliers")
			  .path(Long.toString(supplier.getId()))
			  .request()
			  .delete();
		return response.getStatus() == Response.Status.NO_CONTENT.getStatusCode();
	}
	
	
}
