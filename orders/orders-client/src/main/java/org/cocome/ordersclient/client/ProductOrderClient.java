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

import org.cocome.ordersclient.config.Config;
import org.cocome.ordersclient.domain.OrderEntryTO;
import org.cocome.ordersclient.domain.ProductOrderTO;
import org.cocome.ordersclient.exception.OrdersRestException;

public class ProductOrderClient {
	private final WebTarget webTarget;

	public ProductOrderClient() {
		Client client = ClientBuilder.newClient();
		this.webTarget = client.target(Config.getBaseUri());
	}

	public Collection<ProductOrderTO> findAll() throws OrdersRestException {

		try {
			return this.webTarget.path("product-orders").request().accept(MediaType.APPLICATION_XML_TYPE)
					.get(new GenericType<Collection<ProductOrderTO>>() {
					});
		} catch (NotFoundException e) {
			throw new OrdersRestException(e.getResponse().readEntity(String.class));
		} catch (ProcessingException e) {
			throw new OrdersRestException("Connection to ordersrestservice refused!");
		}

	}

	public ProductOrderTO find(long id) throws OrdersRestException {

		try {
			return this.webTarget.path("product-orders").path(Long.toString(id)).request()
					.accept(MediaType.APPLICATION_XML_TYPE).get(ProductOrderTO.class);
		} catch (NotFoundException e) {
			throw new OrdersRestException(e.getResponse().readEntity(String.class));
		} catch (ProcessingException e) {
			throw new OrdersRestException("Connection to ordersrestservice refused!");
		}

	}

	public long create(ProductOrderTO order, long storeId) throws OrdersRestException {

		try {
			Response response = this.webTarget.path("stores").path(Long.toString(storeId)).path("product-orders")
					.request(MediaType.APPLICATION_XML_TYPE).post(Entity.xml(order));
			URI uri = URI.create(response.getHeaderString("Location"));
			Long id = Long.valueOf(uri.getPath().substring(uri.getPath().lastIndexOf('/') + 1));
			return id;
		} catch (NotFoundException e) {
			throw new OrdersRestException(e.getResponse().readEntity(String.class));
		} catch (ProcessingException e) {
			throw new OrdersRestException("Connection to ordersrestservice refused!");
		}

	}

	public boolean update(ProductOrderTO order) throws OrdersRestException {
		
		
		try {
			Response response = this.webTarget.path("product-orders").path(Long.toString(order.getId()))
					.request(MediaType.APPLICATION_XML_TYPE).put(Entity.xml(order));
			return response.getStatus() == Response.Status.NO_CONTENT.getStatusCode();
		} catch (NotFoundException e) {
			throw new OrdersRestException(e.getResponse().readEntity(String.class));
		} catch (ProcessingException e) {
			throw new OrdersRestException("Connection to ordersrestservice refused!");
		}
		
		
	}

	public boolean delete(ProductOrderTO order) throws OrdersRestException {
		
		try {
			Response response = this.webTarget.path("product-orders").path(Long.toString(order.getId())).request().delete();
			return response.getStatus() == Response.Status.NO_CONTENT.getStatusCode();
		} catch (NotFoundException e) {
			throw new OrdersRestException(e.getResponse().readEntity(String.class));
		} catch (ProcessingException e) {
			throw new OrdersRestException("Connection to ordersrestservice refused!");
		}
		
	
	}

	public Collection<ProductOrderTO> findByStore(long storeId) throws OrdersRestException {
		try {
			return this.webTarget.path("stores").path(Long.toString(storeId)).path("product-orders").request()
					.accept(MediaType.APPLICATION_XML_TYPE).get(new GenericType<Collection<ProductOrderTO>>() {
					});
		} catch (NotFoundException e) {
			throw new OrdersRestException(e.getResponse().readEntity(String.class));
		} catch (ProcessingException e) {
			throw new OrdersRestException("Connection to ordersrestservice refused!");
		}
		
	}
}
