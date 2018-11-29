package org.cocome.productsclient.client;

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

import org.cocome.productsclient.config.Config;
import org.cocome.productsclient.domain.ProductSupplierTO;
import org.cocome.productsclient.exception.ProductsRestException;

/**
 * Client to access Product-REST-Interface by simple method calls
 * 
 * @author Niko Benkler
 * @author Robert Heinrich
 *
 */
public class ProductSupplierClient {
	private final WebTarget webTarget;

	public ProductSupplierClient() {
		Client client = ClientBuilder.newClient();
		this.webTarget = client.target(Config.getBaseUri());
	}

	/**
	 * Find all suppliers
	 * 
	 * @return
	 * @throws ProductsRestException
	 */
	public Collection<ProductSupplierTO> findAll() throws ProductsRestException {
		try {
			return this.webTarget.path("product-suppliers").request().accept(MediaType.APPLICATION_XML_TYPE)
					.get(new GenericType<Collection<ProductSupplierTO>>() {
					});

		} catch (NotFoundException e) {
			throw new ProductsRestException(e.getResponse().readEntity(String.class));
		} catch (ProcessingException e) {
			throw new ProductsRestException("Connection to productsservice refused!");
		}

	}

	/**
	 * Find specific supplier
	 * 
	 * @param id
	 * @return
	 * @throws ProductsRestException
	 */
	public ProductSupplierTO find(long id) throws ProductsRestException {
		try {
			return this.webTarget.path("product-suppliers").path(Long.toString(id)).request()
					.accept(MediaType.APPLICATION_XML_TYPE).get(ProductSupplierTO.class);

		} catch (NotFoundException e) {
			throw new ProductsRestException(e.getResponse().readEntity(String.class));
		} catch (ProcessingException e) {
			throw new ProductsRestException("Connection to productsservice refused!");
		}

	}

	/**
	 * Create supplier 
	 * @param supplier
	 * @return
	 * @throws ProductsRestException
	 */
	public long create(ProductSupplierTO supplier) throws ProductsRestException {
		try {
			Response response = this.webTarget.path("product-suppliers").request(MediaType.APPLICATION_XML_TYPE)
					.post(Entity.xml(supplier));
			URI uri = URI.create(response.getHeaderString("Location"));
			Long id = Long.valueOf(uri.getPath().substring(uri.getPath().lastIndexOf('/') + 1));
			return id;

		} catch (NotFoundException e) {
			throw new ProductsRestException(e.getResponse().readEntity(String.class));
		} catch (ProcessingException e) {
			throw new ProductsRestException("Connection to productsservice refused!");
		}

	}

	/**
	 * Update supplier: Supplier must be existent
	 * @param supplier
	 * @throws ProductsRestException
	 */
	public void update(ProductSupplierTO supplier) throws ProductsRestException {
		try {
			this.webTarget.path("product-suppliers").path(Long.toString(supplier.getId()))
					.request(MediaType.APPLICATION_XML_TYPE).put(Entity.xml(supplier));

		} catch (NotFoundException e) {
			throw new ProductsRestException(e.getResponse().readEntity(String.class));
		} catch (ProcessingException e) {
			throw new ProductsRestException("Connection to productsservice refused!");
		}

	}

	/**
	 * Delete suppleir
	 * @param supplier
	 * @throws ProductsRestException
	 */
	public void delete(ProductSupplierTO supplier) throws ProductsRestException {
		try {

			this.webTarget.path("product-suppliers").path(Long.toString(supplier.getId())).request().delete();
		} catch (NotFoundException e) {
			throw new ProductsRestException(e.getResponse().readEntity(String.class));
		} catch (ProcessingException e) {
			throw new ProductsRestException("Connection to productsservice refused!");
		}

	}

}
