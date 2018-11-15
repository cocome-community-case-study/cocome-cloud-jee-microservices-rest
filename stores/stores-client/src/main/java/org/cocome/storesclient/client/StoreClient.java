package org.cocome.storesclient.client;


import java.net.URI;
import java.util.Collection;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.cocome.storesclient.config.Config;
import org.cocome.storesclient.domain.StoreTO;

public class StoreClient {
	private final WebTarget webTarget;
	
	public StoreClient() {
		Client client = ClientBuilder.newClient();
		this.webTarget = client.target(Config.getBaseUri());
	}
	
	public Collection<StoreTO> findAll() {
		return this.webTarget.path("stores")
							 .request()
							 .accept(MediaType.APPLICATION_XML_TYPE)
							 .get(new GenericType<Collection<StoreTO>>() {});
	}
	
	public StoreTO find(long id) {
		return this.webTarget.path("stores")
							 .path(Long.toString(id))
							 .request()
							 .accept(MediaType.APPLICATION_XML_TYPE)
							 .get(StoreTO.class);
	}
	
	public long create(StoreTO store, long enterpriseId) {
		Response response = this.webTarget.path("trading-enterprises")
										 .path(Long.toString(enterpriseId))
										 .path("stores")
										 .request(MediaType.APPLICATION_XML_TYPE)
										 .post(Entity.xml(store));
		URI uri = URI.create(response.getHeaderString("Location"));
		Long id = Long.valueOf(uri.getPath().substring(uri.getPath().lastIndexOf('/') + 1));
		return id;
	}
	
	public boolean update(StoreTO store) {
		Response response = this.webTarget.path("stores")
			  .path(Long.toString(store.getId()))
			  .request(MediaType.APPLICATION_XML_TYPE)
			  .put(Entity.xml(store));
		return response.getStatus() == Response.Status.NO_CONTENT.getStatusCode();
	}
	
	public boolean delete(StoreTO store) {
		Response response = this.webTarget.path("stores")
			  .path(Long.toString(store.getId()))
			  .request()
			  .delete();
		return response.getStatus() == Response.Status.NO_CONTENT.getStatusCode();
	}
	
	public Collection<StoreTO> findByEnterprise(long enterpriseId) {
		return this.webTarget.path("trading-enterprises")
									.path(Long.toString(enterpriseId))
									.path("stores")
									.request()
									.accept(MediaType.APPLICATION_XML_TYPE)
									.get(new GenericType<Collection<StoreTO>>() {});
	}
}
