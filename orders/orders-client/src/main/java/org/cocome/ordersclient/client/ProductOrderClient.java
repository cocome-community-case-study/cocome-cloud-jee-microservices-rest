package org.cocome.ordersclient.client;

import java.net.URI;
import java.util.Collection;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.cocome.ordersclient.config.Config;
import org.cocome.ordersclient.domain.ProductOrder;

public class ProductOrderClient {
	private final WebTarget webTarget;
	
	public ProductOrderClient() {
		Client client = ClientBuilder.newClient();
		this.webTarget = client.target(Config.getBaseUri());
	}
	
	public Collection<ProductOrder> findAll() {
		return this.webTarget.path("product-orders")
							 .request()
							 .accept(MediaType.APPLICATION_XML_TYPE)
							 .get(new GenericType<Collection<ProductOrder>>() {});
	}
	
	public ProductOrder find(long id) {
		return this.webTarget.path("product-orders")
							 .path(Long.toString(id))
							 .request()
							 .accept(MediaType.APPLICATION_XML_TYPE)
							 .get(ProductOrder.class);
	}
	
	public long create(ProductOrder order, long storeId) {
		Response response = this.webTarget.path("stores")
										 .path(Long.toString(storeId))
										 .path("product-orders")
										 .request(MediaType.APPLICATION_XML_TYPE)
										 .post(Entity.xml(order));
		URI uri = URI.create(response.getHeaderString("Location"));
		Long id = Long.valueOf(uri.getPath().substring(uri.getPath().lastIndexOf('/') + 1));
		return id;
	}
	
	public boolean update(ProductOrder order) {
		Response response = this.webTarget.path("product-orders")
			  .path(Long.toString(order.getId()))
			  .request(MediaType.APPLICATION_XML_TYPE)
			  .put(Entity.xml(order));
		return response.getStatus() == Response.Status.NO_CONTENT.getStatusCode();
	}
	
	public boolean delete(ProductOrder order) {
		Response response = this.webTarget.path("product-orders")
			  .path(Long.toString(order.getId()))
			  .request()
			  .delete();
		return response.getStatus() == Response.Status.NO_CONTENT.getStatusCode();
	}
	
	public Collection<ProductOrder> findByStore(long storeId) {
		return this.webTarget.path("stores")
									.path(Long.toString(storeId))
									.path("product-orders")
									.request()
									.accept(MediaType.APPLICATION_XML_TYPE)
									.get(new GenericType<Collection<ProductOrder>> () {});
	}
}
