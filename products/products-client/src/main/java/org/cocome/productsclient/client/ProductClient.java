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
import org.cocome.productsclient.domain.ProductTO;

public class ProductClient {
	private final WebTarget webTarget;
	
	public ProductClient() {
		Client client = ClientBuilder.newClient();
		this.webTarget = client.target(Config.getBaseUri());
	}
	
	public Collection<ProductTO> findAll() {
		return this.webTarget.path("products")
							 .request()
							 .accept(MediaType.APPLICATION_XML_TYPE)
							 .get(new GenericType<Collection<ProductTO>>() {});
	}
	
	public ProductTO find(long id) {
		return this.webTarget.path("products")
							 .path(Long.toString(id))
							 .request()
							 .accept(MediaType.APPLICATION_XML_TYPE)
							 .get(ProductTO.class);
	}
	
	public long create(ProductTO product, long supplierId) {
		Response response = this.webTarget.path("product-suppliers")
										 .path(Long.toString(supplierId))
										 .path("products")
										 .request(MediaType.APPLICATION_XML_TYPE)
										 .post(Entity.xml(product));
		URI uri = URI.create(response.getHeaderString("Location"));
		Long id = Long.valueOf(uri.getPath().substring(uri.getPath().lastIndexOf('/') + 1));
		return id;
	}
	
	public boolean update(ProductTO productTO) {
		Response response = this.webTarget.path("products")
			  .path(Long.toString(productTO.getId()))
			  .request(MediaType.APPLICATION_XML_TYPE)
			  .put(Entity.xml(productTO));
		return response.getStatus() == Response.Status.NO_CONTENT.getStatusCode();
	}
	
	public boolean delete(ProductTO productTO) {
		Response response = this.webTarget.path("pro"
				+ "ducts")
			  .path(Long.toString(productTO.getId()))
			  .request()
			  .delete();
		return response.getStatus() == Response.Status.NO_CONTENT.getStatusCode();
	}
	
	public Collection<ProductTO> findBySupplier(long supplierId) {
		return this.webTarget.path("product-suppliers")
									.path(Long.toString(supplierId))
									.path("products")
									.request()
									.accept(MediaType.APPLICATION_XML_TYPE)
									.get(new GenericType<Collection<ProductTO>> () {});
	}
}
