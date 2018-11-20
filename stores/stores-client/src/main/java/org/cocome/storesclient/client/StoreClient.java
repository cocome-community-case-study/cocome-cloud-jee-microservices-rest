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
import org.cocome.storesclient.domain.StoreTO;
import org.cocome.storesclient.exception.MicroserviceException;

public class StoreClient {
	private final WebTarget webTarget;

	public StoreClient() {
		Client client = ClientBuilder.newClient();
		this.webTarget = client.target(Config.getBaseUri());
	}

	public Collection<StoreTO> findAll() throws MicroserviceException {
		try {
			return this.webTarget.path("stores").request().accept(MediaType.APPLICATION_XML_TYPE)
					.get(new GenericType<Collection<StoreTO>>() {
					});

		} catch (NotFoundException e) {
			throw new MicroserviceException(e.getResponse().readEntity(String.class)); 
		} catch (ProcessingException e) {
			throw new MicroserviceException("Connection to storesservice refused!");
		}

	}

	public StoreTO find(long id) throws MicroserviceException {
		try {
			return this.webTarget.path("stores").path(Long.toString(id)).request()
					.accept(MediaType.APPLICATION_XML_TYPE).get(StoreTO.class);

		} catch (NotFoundException e) {
			throw new MicroserviceException(e.getResponse().readEntity(String.class));
		} catch (ProcessingException e) {
			throw new MicroserviceException("Connection to storesservice refused!");
		}

	}

	public long create(StoreTO store, long enterpriseId) throws MicroserviceException {
		try {
			Response response = this.webTarget.path("trading-enterprises").path(Long.toString(enterpriseId))
					.path("stores").request(MediaType.APPLICATION_XML_TYPE).post(Entity.xml(store));
			URI uri = URI.create(response.getHeaderString("Location"));
			Long id = Long.valueOf(uri.getPath().substring(uri.getPath().lastIndexOf('/') + 1));
			return id;
		} catch (NotFoundException e) {
			throw new MicroserviceException(e.getResponse().readEntity(String.class));
		} catch (ProcessingException e) {
			throw new MicroserviceException("Connection to storesservice refused!");
		}

	}

	public void update(StoreTO store) throws MicroserviceException {
		try {
			this.webTarget.path("stores").path(Long.toString(store.getId())).request(MediaType.APPLICATION_XML_TYPE)
					.put(Entity.xml(store));

		} catch (NotFoundException e) {
			throw new MicroserviceException(e.getResponse().readEntity(String.class));
		} catch (ProcessingException e) {
			throw new MicroserviceException("Connection to storesservice refused!");
		} 

	}

	public void delete(StoreTO store) throws MicroserviceException {
		try {
			this.webTarget.path("stores").path(Long.toString(store.getId())).request().delete();
			
		} catch (NotFoundException e) {
			throw new MicroserviceException(e.getResponse().readEntity(String.class));
		} catch (ProcessingException e) {
			throw new MicroserviceException("Connection to storesservice refused!");
		}

	}

	public Collection<StoreTO> findByEnterprise(long enterpriseId) throws MicroserviceException {
		try {
			return this.webTarget.path("trading-enterprises").path(Long.toString(enterpriseId)).path("stores").request()
					.accept(MediaType.APPLICATION_XML_TYPE).get(new GenericType<Collection<StoreTO>>() {
					});
		} catch (NotFoundException e) {
			throw new MicroserviceException(e.getResponse().readEntity(String.class));
		} catch (ProcessingException e) {
			throw new MicroserviceException("Connection to storesservice refused!");
		}
		
	}
}
