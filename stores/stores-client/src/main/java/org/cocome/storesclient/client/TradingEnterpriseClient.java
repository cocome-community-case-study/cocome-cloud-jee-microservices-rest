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

import org.cocome.storesclient.config.StoresConfig;
import org.cocome.storesclient.domain.TradingEnterpriseTO;
import org.cocome.storesclient.exception.StoreRestException;

/**
 * Client to ease REST-Calls to Enterprise Rest-Interface
 * @author Niko Benkler
 * @author Robert Heinrich
 */
public class TradingEnterpriseClient {
	private final WebTarget webTarget;

	public TradingEnterpriseClient() {
		Client client = ClientBuilder.newClient();
		this.webTarget = client.target(StoresConfig.getBaseUri());
	}

	/**
	 * Find all enterprises
	 * @return
	 * @throws StoreRestException
	 */
	public Collection<TradingEnterpriseTO> findAll() throws StoreRestException {
		try {
			return this.webTarget.path("trading-enterprises").request().accept(MediaType.APPLICATION_XML_TYPE)
					.get(new GenericType<Collection<TradingEnterpriseTO>>() {
					});

		} catch (NotFoundException e) {
			throw new StoreRestException(e.getResponse().readEntity(String.class));
		} catch (ProcessingException e) {
			throw new StoreRestException("Connection to storesservice refused!");
		}

	}

	/**
	 * Find specific enterprise
	 * @param id
	 * @return
	 * @throws StoreRestException
	 */
	public TradingEnterpriseTO find(long id) throws StoreRestException {
		try {
			return this.webTarget.path("trading-enterprises").path(Long.toString(id)).request()
					.accept(MediaType.APPLICATION_XML_TYPE).get(TradingEnterpriseTO.class);

		} catch (NotFoundException e) {
			throw new StoreRestException(e.getResponse().readEntity(String.class));
		} catch (ProcessingException e) {
			throw new StoreRestException("Connection to storesservice refused!"); 
		}

	}

	/**
	 * Create enterprise
	 * @param enterprise
	 * @return
	 * @throws StoreRestException
	 */
	public long create(TradingEnterpriseTO enterprise) throws StoreRestException {
		try {
			Response response = this.webTarget.path("trading-enterprises").request(MediaType.APPLICATION_XML_TYPE)
					.post(Entity.xml(enterprise));
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
	 * Update enterprise
	 * @param enterprise
	 * @throws StoreRestException
	 */
	public void update(TradingEnterpriseTO enterprise) throws StoreRestException {
		try {
			this.webTarget.path("trading-enterprises").path(Long.toString(enterprise.getId()))
					.request(MediaType.APPLICATION_XML_TYPE).put(Entity.xml(enterprise));

		} catch (NotFoundException e) {
			throw new StoreRestException(e.getResponse().readEntity(String.class));
		} catch (ProcessingException e) {
			throw new StoreRestException("Connection to storesservice refused!");
		}

	}

	/**
	 * Delete Enterprise
	 * @param enterprise
	 * @throws StoreRestException
	 */
	public void delete(TradingEnterpriseTO enterprise) throws StoreRestException {
		try {
			this.webTarget.path("trading-enterprises").path(Long.toString(enterprise.getId()))
					.request().delete();

		} catch (NotFoundException e) {
			throw new StoreRestException(e.getResponse().readEntity(String.class));
		} catch (ProcessingException e) {
			throw new StoreRestException("Connection to storesservice refused!");
		}

	}
}
