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
import org.cocome.productsclient.domain.ProductTO;
import org.cocome.productsclient.exception.ProductsRestException;
/**
 * Client to access Product-REST-Interface by simple method calls
 * @author Niko Benkler
 * @author Robert Heinrich
 *
 */
public class ProductClient {
	private final WebTarget webTarget;

	public ProductClient() {
		Client client = ClientBuilder.newClient();
		this.webTarget = client.target(Config.getBaseUri());
	}

	/**
	 * Retrieve all Products
	 * @return
	 * @throws ProductsRestException
	 */
	public Collection<ProductTO> findAll() throws ProductsRestException {
		try {
			return this.webTarget.path("products").request().accept(MediaType.APPLICATION_XML_TYPE)
					.get(new GenericType<Collection<ProductTO>>() {
					});

		} catch (NotFoundException e) {
			throw new ProductsRestException(e.getResponse().readEntity(String.class));
		} catch (ProcessingException e) {
			throw new ProductsRestException("Connection to productsservice refused!");
		}

	}

	/**
	 * Get Product with given Id
	 * @param id
	 * @return
	 * @throws ProductsRestException
	 */
	public ProductTO find(long id) throws ProductsRestException {
		try {
			return this.webTarget.path("products").path(Long.toString(id)).request()
					.accept(MediaType.APPLICATION_XML_TYPE).get(ProductTO.class);

		} catch (NotFoundException e) {
			throw new ProductsRestException(e.getResponse().readEntity(String.class));
		} catch (ProcessingException e) {
			throw new ProductsRestException("Connection to productsservice refused!");
		}

	}

	/**
	 * Create product with given supplier id and product information
	 * @param product
	 * @param supplierId
	 * @return
	 * @throws ProductsRestException
	 */
	public long create(ProductTO product, long supplierId) throws ProductsRestException {
		try {

			Response response = this.webTarget.path("product-suppliers").path(Long.toString(supplierId))
					.path("products").request(MediaType.APPLICATION_XML_TYPE).post(Entity.xml(product));
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
	 * Update Product Information. Product needs to be existent. If not, Exception is thrown
	 * @param productTO
	 * @throws ProductsRestException
	 */
	public void update(ProductTO productTO) throws ProductsRestException {
		try {

			this.webTarget.path("products").path(Long.toString(productTO.getId()))
					.request(MediaType.APPLICATION_XML_TYPE).put(Entity.xml(productTO));

		} catch (NotFoundException e) {
			throw new ProductsRestException(e.getResponse().readEntity(String.class));
		} catch (ProcessingException e) {
			throw new ProductsRestException("Connection to productsservice refused!");
		}

	}

	/**
	 * Delete Product
	 * @param productTO
	 * @throws ProductsRestException
	 */
	public void delete(ProductTO productTO) throws ProductsRestException {
		try {

			this.webTarget.path("products").path(Long.toString(productTO.getId())).request().delete();
		} catch (NotFoundException e) {
			throw new ProductsRestException(e.getResponse().readEntity(String.class));
		} catch (ProcessingException e) {
			throw new ProductsRestException("Connection to productsservice refused!");
		}

	}

	/**
	 * Find all Products of a given supplier
	 * @param supplierId
	 * @return
	 * @throws ProductsRestException
	 */
	public Collection<ProductTO> findBySupplier(long supplierId) throws ProductsRestException {
		try {
			return this.webTarget.path("product-suppliers").path(Long.toString(supplierId)).path("products").request()
					.accept(MediaType.APPLICATION_XML_TYPE).get(new GenericType<Collection<ProductTO>>() {
					});

		} catch (NotFoundException e) {
			throw new ProductsRestException(e.getResponse().readEntity(String.class));
		} catch (ProcessingException e) {
			throw new ProductsRestException("Connection to productsservice refused!");
		}

	}
}
