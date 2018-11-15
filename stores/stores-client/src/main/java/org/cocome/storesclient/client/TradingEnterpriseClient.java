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
import org.cocome.storesclient.domain.TradingEnterpriseTO;

public class TradingEnterpriseClient {
	private final WebTarget webTarget;
	
	public TradingEnterpriseClient() {
		Client client = ClientBuilder.newClient();
		this.webTarget = client.target(Config.getBaseUri());
	}
	
	public Collection<TradingEnterpriseTO> findAll() {
		return this.webTarget.path("trading-enterprises")
							 .request()
							 .accept(MediaType.APPLICATION_XML_TYPE)
							 .get(new GenericType<Collection<TradingEnterpriseTO>>() {});
	}
	
	public TradingEnterpriseTO find(long id) {
		return this.webTarget.path("trading-enterprises")
							 .path(Long.toString(id))
							 .request()
							 .accept(MediaType.APPLICATION_XML_TYPE)
							 .get(TradingEnterpriseTO.class);
	}
	
	public long create(TradingEnterpriseTO enterprise) {
		Response response = this.webTarget.path("trading-enterprises").request(MediaType.APPLICATION_XML_TYPE).post(Entity.xml(enterprise));
		URI uri = URI.create(response.getHeaderString("Location"));
		Long id = Long.valueOf(uri.getPath().substring(uri.getPath().lastIndexOf('/') + 1));
		return id;
	}
	
	public boolean update(TradingEnterpriseTO enterprise) {
		Response response = this.webTarget.path("trading-enterprises")
			  .path(Long.toString(enterprise.getId()))
			  .request(MediaType.APPLICATION_XML_TYPE)
			  .put(Entity.xml(enterprise));
		return response.getStatus() == Response.Status.NO_CONTENT.getStatusCode();
	}
	
	public boolean delete(TradingEnterpriseTO enterprise) {
		Response response = this.webTarget.path("trading-enterprises")
			  .path(Long.toString(enterprise.getId()))
			  .request()
			  .delete();
		return response.getStatus() == Response.Status.NO_CONTENT.getStatusCode();
	}
}
