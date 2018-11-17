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
import org.cocome.ordersclient.domain.OrderEntryTO;

public class OrderEntryClient {
	private final WebTarget webTarget;
	
	public OrderEntryClient() {
		Client client = ClientBuilder.newClient();
		this.webTarget = client.target(Config.getBaseUri());
	}
	
	public Collection<OrderEntryTO> findAll() {
		return this.webTarget.path("order-entries")
							 .request()
							 .accept(MediaType.APPLICATION_XML_TYPE)
							 .get(new GenericType<Collection<OrderEntryTO>>() {});
	}
	
	public OrderEntryTO find(long id) {
		return this.webTarget.path("order-entries")
							 .path(Long.toString(id))
							 .request()
							 .accept(MediaType.APPLICATION_XML_TYPE)
							 .get(OrderEntryTO.class);
	}
	
	public long create(OrderEntryTO entry, long orderId) {
		Response response = this.webTarget.path("product-orders")
										 .path(Long.toString(orderId))
										 .path("order-entries")
										 .request(MediaType.APPLICATION_XML_TYPE)
										 .post(Entity.xml(entry));
		URI uri = URI.create(response.getHeaderString("Location"));
		Long id = Long.valueOf(uri.getPath().substring(uri.getPath().lastIndexOf('/') + 1));
		return id;
	}
	
	public boolean update(OrderEntryTO entry) {
		Response response = this.webTarget.path("order-entries")
			  .path(Long.toString(entry.getId()))
			  .request(MediaType.APPLICATION_XML_TYPE)
			  .put(Entity.xml(entry));
		return response.getStatus() == Response.Status.NO_CONTENT.getStatusCode();
	}
	
	public boolean delete(OrderEntryTO entry) {
		Response response = this.webTarget.path("order-entries")
			  .path(Long.toString(entry.getId()))
			  .request()
			  .delete();
		return response.getStatus() == Response.Status.NO_CONTENT.getStatusCode();
	}
	
	public Collection<OrderEntryTO> findByOrder(long orderId) {
		return this.webTarget.path("product-orders")
									.path(Long.toString(orderId))
									.path("order-entries")
									.request()
									.accept(MediaType.APPLICATION_XML_TYPE)
									.get(new GenericType<Collection<OrderEntryTO>> () {});
	}
}
