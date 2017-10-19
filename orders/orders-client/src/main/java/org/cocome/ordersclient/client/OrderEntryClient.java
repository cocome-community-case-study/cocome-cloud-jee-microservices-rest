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
import org.cocome.ordersclient.domain.OrderEntry;

public class OrderEntryClient {
	private final WebTarget webTarget;
	
	public OrderEntryClient() {
		Client client = ClientBuilder.newClient();
		this.webTarget = client.target(Config.getBaseUri());
	}
	
	public Collection<OrderEntry> findAll() {
		return this.webTarget.path("order-entries")
							 .request()
							 .accept(MediaType.APPLICATION_XML_TYPE)
							 .get(new GenericType<Collection<OrderEntry>>() {});
	}
	
	public OrderEntry find(long id) {
		return this.webTarget.path("order-entries")
							 .path(Long.toString(id))
							 .request()
							 .accept(MediaType.APPLICATION_XML_TYPE)
							 .get(OrderEntry.class);
	}
	
	public long create(OrderEntry entry, long orderId) {
		Response response = this.webTarget.path("product-orders")
										 .path(Long.toString(orderId))
										 .path("order-entries")
										 .request(MediaType.APPLICATION_XML_TYPE)
										 .post(Entity.xml(entry));
		URI uri = URI.create(response.getHeaderString("Location"));
		Long id = Long.valueOf(uri.getPath().substring(uri.getPath().lastIndexOf('/') + 1));
		return id;
	}
	
	public void update(OrderEntry entry) {
		this.webTarget.path("order-entries")
					  .path(Long.toString(entry.getId()))
					  .request(MediaType.APPLICATION_XML_TYPE)
					  .put(Entity.xml(entry));
	}
	
	public void delete(OrderEntry entry) {
		this.webTarget.path("order-entries")
					  .path(Long.toString(entry.getId()))
					  .request()
					  .delete();
	}
	
	public Collection<OrderEntry> findByOrder(long orderId) {
		return this.webTarget.path("product-orders")
									.path(Long.toString(orderId))
									.path("order-entries")
									.request()
									.accept(MediaType.APPLICATION_XML_TYPE)
									.get(new GenericType<Collection<OrderEntry>> () {});
	}
}
