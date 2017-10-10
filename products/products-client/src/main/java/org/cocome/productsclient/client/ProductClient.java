package org.cocome.productsclient.client;

import java.net.URI;
import java.util.Collection;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.cocome.productsclient.config.Config;
import org.cocome.productsclient.domain.Product;

public class ProductClient {
private final WebTarget webTarget;
	
	public ProductClient() {
		Client client = ClientBuilder.newClient();
		this.webTarget = client.target(Config.getBaseUri());
	}
	
	@SuppressWarnings("unchecked")
	public Collection<Product> findAll() {
		return this.webTarget.path("products")
							 .request()
							 .accept(MediaType.APPLICATION_XML_TYPE)
							 .get(Collection.class);
	}
	
	public Product find(long id) {
		return this.webTarget.path("products")
							 .path(Long.toString(id))
							 .request()
							 .accept(MediaType.APPLICATION_XML_TYPE)
							 .get(Product.class);
	}
	
	public long create(Product product, long supplierId) {
		Response response = this.webTarget.path("product-suppliers")
										 .path(Long.toString(supplierId))
										 .path("products")
										 .request(MediaType.APPLICATION_XML_TYPE)
										 .post(Entity.xml(product));
		URI uri = URI.create(response.getHeaderString("Location"));
		Long id = Long.valueOf(uri.getPath().substring(uri.getPath().lastIndexOf('/') + 1));
		return id;
	}
	
	public void update(Product store) {
		this.webTarget.path("products")
					  .path(Long.toString(store.getId()))
					  .request(MediaType.APPLICATION_XML_TYPE)
					  .put(Entity.xml(store));
	}
	
	public void delete(Product store) {
		this.webTarget.path("products")
					  .path(Long.toString(store.getId()))
					  .request()
					  .delete();
	}
	
	public Collection<Product> findByEnterprise(long supplierId) {
		return this.webTarget.path("product-suppliers")
									.path(Long.toString(supplierId))
									.path("products")
									.request()
									.accept(MediaType.APPLICATION_XML_TYPE)
									.get(Collection.class);
	}
}
