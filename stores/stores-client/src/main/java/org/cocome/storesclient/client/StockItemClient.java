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
import org.cocome.storesclient.domain.StockItem;

public class StockItemClient {
private final WebTarget webTarget;
	
	public StockItemClient() {
		Client client = ClientBuilder.newClient();
		this.webTarget = client.target(Config.getBaseUri());
	}
	
	public Collection<StockItem> findAll() {
		return this.webTarget.path("stock-items")
							 .request()
							 .accept(MediaType.APPLICATION_XML_TYPE)
							 .get(new GenericType<Collection<StockItem>>() {});
	}
	
	public StockItem find(long id) {
		return this.webTarget.path("stock-items")
							 .path(Long.toString(id))
							 .request()
							 .accept(MediaType.APPLICATION_XML_TYPE)
							 .get(StockItem.class);
	}
	
	public long create(StockItem stockItem, long storeId) {
		Response response = this.webTarget.path("stores")
										 .path(Long.toString(storeId))
										 .path("stock-items")
										 .request(MediaType.APPLICATION_XML_TYPE)
										 .post(Entity.xml(stockItem));
		URI uri = URI.create(response.getHeaderString("Location"));
		Long id = Long.valueOf(uri.getPath().substring(uri.getPath().lastIndexOf('/') + 1));
		return id;
	}
	
	public void update(StockItem stockItem) {
		this.webTarget.path("stock-items")
					  .path(Long.toString(stockItem.getId()))
					  .request(MediaType.APPLICATION_XML_TYPE)
					  .put(Entity.xml(stockItem));
	}
	
	public void delete(StockItem stockItem) {
		this.webTarget.path("stock-items")
					  .path(Long.toString(stockItem.getId()))
					  .request()
					  .delete();
	}
	
	public Collection<StockItem> findByStore(long storeId) {
		return this.webTarget.path("stores")
									.path(Long.toString(storeId))
									.path("stock-items")
									.request()
									.accept(MediaType.APPLICATION_XML_TYPE)
									.get(new GenericType<Collection<StockItem>>() {});
	}
}
