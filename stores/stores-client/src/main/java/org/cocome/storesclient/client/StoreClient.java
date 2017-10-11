package org.cocome.storesclient.client;


import java.net.URI;
import java.util.Collection;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.cocome.storesclient.config.Config;
import org.cocome.storesclient.domain.Store;

public class StoreClient {
	private final WebTarget webTarget;
	
	public StoreClient() {
		Client client = ClientBuilder.newClient();
		this.webTarget = client.target(Config.getBaseUri());
	}
	
	@SuppressWarnings("unchecked")
	public Collection<Store> findAll() {
		return this.webTarget.path("stores")
							 .request()
							 .accept(MediaType.APPLICATION_XML_TYPE)
							 .get(Collection.class);
	}
	
	public Store find(long id) {
		return this.webTarget.path("stores")
							 .path(Long.toString(id))
							 .request()
							 .accept(MediaType.APPLICATION_XML_TYPE)
							 .get(Store.class);
	}
	
	public long create(Store store, long enterpriseId) {
		Response response = this.webTarget.path("trading-enterprises")
										 .path(Long.toString(enterpriseId))
										 .path("stores")
										 .request(MediaType.APPLICATION_XML_TYPE)
										 .post(Entity.xml(store));
		URI uri = URI.create(response.getHeaderString("Location"));
		Long id = Long.valueOf(uri.getPath().substring(uri.getPath().lastIndexOf('/') + 1));
		return id;
	}
	
	public void update(Store store) {
		this.webTarget.path("stores")
					  .path(Long.toString(store.getId()))
					  .request(MediaType.APPLICATION_XML_TYPE)
					  .put(Entity.xml(store));
	}
	
	public void delete(Store store) {
		this.webTarget.path("stores")
					  .path(Long.toString(store.getId()))
					  .request()
					  .delete();
	}
	
	public Collection<Store> findByEnterprise(long enterpriseId) {
		return this.webTarget.path("trading-enterprises")
									.path(Long.toString(enterpriseId))
									.path("stores")
									.request()
									.accept(MediaType.APPLICATION_XML_TYPE)
									.get(Collection.class);
	}
}