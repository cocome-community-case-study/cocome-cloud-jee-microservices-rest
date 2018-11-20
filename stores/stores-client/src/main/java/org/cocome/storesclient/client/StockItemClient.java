package org.cocome.storesclient.client;

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

import org.cocome.storesclient.config.Config;
import org.cocome.storesclient.domain.StockItemTO;
import org.cocome.storesclient.exception.MicroserviceException;

public class StockItemClient {
	private final WebTarget webTarget;

	public StockItemClient() {
		Client client = ClientBuilder.newClient();
		this.webTarget = client.target(Config.getBaseUri());
	}

	public Collection<StockItemTO> findAll() throws MicroserviceException {
		try {
			return this.webTarget.path("stock-items").request().accept(MediaType.APPLICATION_XML_TYPE)
					.get(new GenericType<Collection<StockItemTO>>() {
					});
		} catch (NotFoundException e) {
			throw new MicroserviceException(e.getResponse().readEntity(String.class));
		} catch (ProcessingException e) {
			throw new MicroserviceException("Connection to storesservice refused!");
		}

	}

	public StockItemTO find(long id) throws MicroserviceException {
		try {

			return this.webTarget.path("stock-items").path(Long.toString(id)).request()
					.accept(MediaType.APPLICATION_XML_TYPE).get(StockItemTO.class);
		} catch (NotFoundException e) {
			throw new MicroserviceException(e.getResponse().readEntity(String.class));
		} catch (ProcessingException e) {
			throw new MicroserviceException("Connection to storesservice refused!");
		}

	}

	public long create(StockItemTO stockItem, long storeId) throws MicroserviceException {
		try {

			Response response = this.webTarget.path("stores").path(Long.toString(storeId)).path("stock-items")
					.request(MediaType.APPLICATION_XML_TYPE).post(Entity.xml(stockItem));
			URI uri = URI.create(response.getHeaderString("Location"));
			Long id = Long.valueOf(uri.getPath().substring(uri.getPath().lastIndexOf('/') + 1));
			return id;

		} catch (NotFoundException e) {
			throw new MicroserviceException(e.getResponse().readEntity(String.class));
		} catch (ProcessingException e) {
			throw new MicroserviceException("Connection to storesservice refused!");
		}

	}

	public void update(StockItemTO stockItem) throws MicroserviceException {
		try {
			this.webTarget.path("stock-items").path(Long.toString(stockItem.getId()))
					.request(MediaType.APPLICATION_XML_TYPE).put(Entity.xml(stockItem));

		} catch (NotFoundException e) {
			throw new MicroserviceException(e.getResponse().readEntity(String.class));
		} catch (ProcessingException e) {
			throw new MicroserviceException("Connection to storesservice refused!");
		}

	}

	public void delete(StockItemTO stockItem) throws MicroserviceException {
		try {

			this.webTarget.path("stock-items").path(Long.toString(stockItem.getId())).request().delete();

		} catch (NotFoundException e) {
			throw new MicroserviceException(e.getResponse().readEntity(String.class));
		} catch (ProcessingException e) {
			throw new MicroserviceException("Connection to storesservice refused!");
		}

	}

	public Collection<StockItemTO> findByStore(long storeId) throws MicroserviceException {
		try {
			return this.webTarget.path("stores").path(Long.toString(storeId)).path("stock-items").request()
					.accept(MediaType.APPLICATION_XML_TYPE).get(new GenericType<Collection<StockItemTO>>() {
					});

		} catch (NotFoundException e) {

			throw new MicroserviceException(e.getResponse().readEntity(String.class)); // TODO

		} catch (ProcessingException e) {
			throw new MicroserviceException("Connection to storesservice refused!");
		}
	}
}
