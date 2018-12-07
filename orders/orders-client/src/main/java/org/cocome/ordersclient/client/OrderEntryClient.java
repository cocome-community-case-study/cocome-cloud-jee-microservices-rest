package org.cocome.ordersclient.client;

import java.net.URI;
import java.util.Collection;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.cocome.ordersclient.config.OrdersConfig;
import org.cocome.ordersclient.domain.OrderEntryTO;
import org.cocome.ordersclient.exception.OrdersRestException;

public class OrderEntryClient {
	private final WebTarget webTarget;

	public OrderEntryClient() {
		Client client = ClientBuilder.newClient();
		this.webTarget = client.target(OrdersConfig.getBaseUri());
	}

	public Collection<OrderEntryTO> findAll() throws OrdersRestException {
		try {
			return this.webTarget.path("order-entries").request().accept(MediaType.APPLICATION_XML_TYPE)
					.get(new GenericType<Collection<OrderEntryTO>>() {
					});

		} catch (NotFoundException e) {
			throw new OrdersRestException(e.getResponse().readEntity(String.class));
		} catch (ProcessingException e) {
			throw new OrdersRestException("Connection to ordersrestservice refused!");
		}

	}

	public OrderEntryTO find(long id) throws OrdersRestException {
		try {
			return this.webTarget.path("order-entries").path(Long.toString(id)).request()
					.accept(MediaType.APPLICATION_XML_TYPE).get(OrderEntryTO.class);
		} catch (NotFoundException e) {
			throw new OrdersRestException(e.getResponse().readEntity(String.class));
		} catch (ProcessingException e) {
			throw new OrdersRestException("Connection to ordersrestservice refused!");
		}

	}

	public long create(OrderEntryTO entry, long orderId) throws OrdersRestException {
		
		try {
			Response response = this.webTarget.path("product-orders").path(Long.toString(orderId)).path("order-entries")
					.request(MediaType.APPLICATION_XML_TYPE).post(Entity.xml(entry));
			URI uri = URI.create(response.getHeaderString("Location"));
			Long id = Long.valueOf(uri.getPath().substring(uri.getPath().lastIndexOf('/') + 1));
			return id;
		} catch (NotFoundException e) {
			throw new OrdersRestException(e.getResponse().readEntity(String.class));
		} catch (ProcessingException e) {
			throw new OrdersRestException("Connection to ordersrestservice refused!");
		}
		
		
		
		
	}

	public boolean update(OrderEntryTO entry) throws OrdersRestException {
		
		try {
			Response response = this.webTarget.path("order-entries").path(Long.toString(entry.getId()))
					.request(MediaType.APPLICATION_XML_TYPE).put(Entity.xml(entry));
			return response.getStatus() == Response.Status.NO_CONTENT.getStatusCode();
		} catch (NotFoundException e) {
			throw new OrdersRestException(e.getResponse().readEntity(String.class));
		} catch (ProcessingException e) {
			throw new OrdersRestException("Connection to ordersrestservice refused!");
		}
		
	}

	public boolean delete(OrderEntryTO entry) throws OrdersRestException {
		try {
			Response response = this.webTarget.path("order-entries").path(Long.toString(entry.getId())).request().delete();
			return response.getStatus() == Response.Status.NO_CONTENT.getStatusCode();
		} catch (NotFoundException e) {
			throw new OrdersRestException(e.getResponse().readEntity(String.class));
		} catch (ProcessingException e) {
			throw new OrdersRestException("Connection to ordersrestservice refused!"); 
		}
		
	}

	public Collection<OrderEntryTO> findByOrder(long orderId) throws OrdersRestException {
		try {
			return this.webTarget.path("product-orders").path(Long.toString(orderId)).path("order-entries").request()
					.accept(MediaType.APPLICATION_XML_TYPE).get(new GenericType<Collection<OrderEntryTO>>() {
					});
		} catch (NotFoundException e) {
			throw new OrdersRestException(e.getResponse().readEntity(String.class));
		} catch (ProcessingException e) {
			throw new OrdersRestException("Connection to ordersrestservice refused!");
		}
		
	}
}
