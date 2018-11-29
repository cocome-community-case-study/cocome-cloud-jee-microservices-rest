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
import org.cocome.storesclient.exception.StoreRestException;

/**
 * Client to ease REST-Calls to Store Rest-Interface
 * @author Niko Benkler
 * @author Robert Heinrich
 *
 */
public class StoreClient {
	private final WebTarget webTarget;

	public StoreClient() {
		Client client = ClientBuilder.newClient();
		this.webTarget = client.target(Config.getBaseUri());
	}

	/**
	 * Get all stores
	 * @return
	 * @throws StoreRestException
	 */
	public Collection<StoreTO> findAll() throws StoreRestException {
		try {
			return this.webTarget.path("stores").request().accept(MediaType.APPLICATION_XML_TYPE)
					.get(new GenericType<Collection<StoreTO>>() {
					});

		} catch (NotFoundException e) {
			throw new StoreRestException(e.getResponse().readEntity(String.class)); 
		} catch (ProcessingException e) {
			throw new StoreRestException("Connection to storesservice refused!");
		}

	}

	/**
	 * Get sote with specific id
	 * @param id
	 * @return
	 * @throws StoreRestException
	 */
	public StoreTO find(long id) throws StoreRestException {
		try {
			return this.webTarget.path("stores").path(Long.toString(id)).request()
					.accept(MediaType.APPLICATION_XML_TYPE).get(StoreTO.class);

		} catch (NotFoundException e) {
			throw new StoreRestException(e.getResponse().readEntity(String.class));
		} catch (ProcessingException e) {
			throw new StoreRestException("Connection to storesservice refused!");
		}

	}

	/**
	 * Create store ==> valid enterprise id needed!
	 * @param store
	 * @param enterpriseId
	 * @return
	 * @throws StoreRestException
	 */
	public long create(StoreTO store, long enterpriseId) throws StoreRestException {
		try {
			Response response = this.webTarget.path("trading-enterprises").path(Long.toString(enterpriseId))
					.path("stores").request(MediaType.APPLICATION_XML_TYPE).post(Entity.xml(store));
			URI uri = URI.create(response.getHeaderString("Location"));
			Long id = Long.valueOf(uri.getPath().substring(uri.getPath().lastIndexOf('/') + 1));
			return id;
		} catch (NotFoundException e) {
			throw new StoreRestException(e.getResponse().readEntity(String.class));
		} catch (ProcessingException e) {
			throw new StoreRestException("Connection to storesservice refused!");
		}

	}

	/**
	 * Update Store
	 * @param store
	 * @throws StoreRestException
	 */
	public void update(StoreTO store) throws StoreRestException {
		try {
			this.webTarget.path("stores").path(Long.toString(store.getId())).request(MediaType.APPLICATION_XML_TYPE)
					.put(Entity.xml(store));

		} catch (NotFoundException e) {
			throw new StoreRestException(e.getResponse().readEntity(String.class));
		} catch (ProcessingException e) {
			throw new StoreRestException("Connection to storesservice refused!");
		} 

	}

	/**
	 * Delete store
	 * @param store
	 * @throws StoreRestException
	 */
	public void delete(StoreTO store) throws StoreRestException {
		try {
			this.webTarget.path("stores").path(Long.toString(store.getId())).request().delete();
			
		} catch (NotFoundException e) {
			throw new StoreRestException(e.getResponse().readEntity(String.class));
		} catch (ProcessingException e) {
			throw new StoreRestException("Connection to storesservice refused!");
		}

	}

	/**
	 * Get all stores of an specific enterprise
	 * @param enterpriseId
	 * @return
	 * @throws StoreRestException
	 */
	public Collection<StoreTO> findByEnterprise(long enterpriseId) throws StoreRestException {
		try {
			return this.webTarget.path("trading-enterprises").path(Long.toString(enterpriseId)).path("stores").request()
					.accept(MediaType.APPLICATION_XML_TYPE).get(new GenericType<Collection<StoreTO>>() {
					});
		} catch (NotFoundException e) {
			throw new StoreRestException(e.getResponse().readEntity(String.class));
		} catch (ProcessingException e) {
			throw new StoreRestException("Connection to storesservice refused!");
		}
		
	}
}
