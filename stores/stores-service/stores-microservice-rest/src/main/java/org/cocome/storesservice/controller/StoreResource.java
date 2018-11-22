package org.cocome.storesservice.controller;

import java.util.Collection;
import java.util.LinkedList;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.apache.log4j.Logger;
import org.cocome.storesclient.domain.StockItemTO;
import org.cocome.storesclient.domain.StoreTO;
import org.cocome.storesservice.domain.StockItem;
import org.cocome.storesservice.domain.Store;
import org.cocome.storesservice.exceptions.CreateException;
import org.cocome.storesservice.exceptions.QueryException;
import org.cocome.storesserviceservice.StoreQuery.IStockQuery;
import org.cocome.storesserviceservice.StoreQuery.IStoreQuery;

/**
 * RESTful resource for stores and nested stock items. To create stores or fetch
 * them by their trading enterprise associaton, the TradingEnterpriseResource is
 * used.
 * 
 * @author Nils Sommer
 *
 */
@RequestScoped
@Path("/stores")
public class StoreResource {

	@EJB
	private IStoreQuery storeQuery;

	@EJB
	private IStockQuery stockQuery;

	private final long COULD_NOT_CREATE_ENTITY = -1;
	private static final Logger LOG = Logger.getLogger(StoreResource.class);

	@GET
	public Collection<StoreTO> findAll() {
		LOG.debug("REST: Retrieve all Enterprises");
		Collection<StoreTO> collection = new LinkedList<StoreTO>();

		for (Store store : storeQuery.getAllStores()) {
			collection.add(toStoreTO(store));
		}

		return collection;
	}

	@GET
	@Path("/{id}")
	public StoreTO find(@PathParam("id") Long id) {
		LOG.debug("REST: Try to find store with id: " + id);
		Store store;
		try {
			store = storeQuery.getStoreById(id);
		} catch (QueryException e) {
			LOG.debug("REST: " + e.getMessage());
			throw new NotFoundException(e.getMessage());
		}

		return toStoreTO(store);

	}

	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_XML)
	public Response update(@PathParam("id") Long id, StoreTO storeTO) {
		LOG.debug("REST: Try to update store with name: " + storeTO.getName() + " and id:" + id);

		try {
			storeQuery.updateStore(id, storeTO.getName(), storeTO.getLocation());
		} catch (QueryException e) {
			LOG.debug("REST: " + e.getMessage());
			throw new NotFoundException(e.getMessage());
		}

		return Response.noContent().build();

	}

	@DELETE
	@Path("/{id}")
	public Response delete(@PathParam("id") Long id) {
		LOG.debug("REST: Trying to delete Store with id: " + id);

		try {
			storeQuery.deleteStore(id);
		} catch (QueryException e) {
			LOG.debug("REST: " + e.getMessage());
			throw new NotFoundException(e.getMessage());
		}

		return Response.noContent().build();

	}

	// Creating and fetching nested stock items

	@GET
	@Path("/{id}/stock-items")
	public Collection<StockItemTO> findStockItems(@PathParam("id") Long storeId) {
		LOG.debug("REST: Trying to find ALL stock items of store with id: " + storeId);

		Store store;
		try {
			store = storeQuery.getStoreById(storeId);
		} catch (QueryException e) {
			LOG.debug("REST: " + e.getMessage());

			throw new NotFoundException(e.getMessage());
		}

		Collection<StockItemTO> collection = new LinkedList<StockItemTO>();

		for (StockItem item : store.getStockItems()) {
			collection.add(StockItemResource.toStockItemTo(item));
		}
		return collection;
	}

	@POST
	@Path("/{id}/stock-items")
	@Consumes(MediaType.APPLICATION_XML)
	public Response createStockItem(@Context UriInfo uriInfo, @PathParam("id") Long storeId, StockItemTO stockItemTO) {
		LOG.debug("REST:Trying to create stockItem with productId: " + stockItemTO.getProductId()
				+ " in store with id: " + storeId);

		Long id;
		try {
			id = stockQuery.createStockItem(stockItemTO.getSalesPrice(), stockItemTO.getAmount(),
					stockItemTO.getMinStock(), stockItemTO.getMaxStock(), stockItemTO.getBarcode(),
					stockItemTO.getIncomingAmount(), stockItemTO.getProductId(), storeId, stockItemTO.getName());
		} catch (CreateException e) {
			LOG.debug("REST: " + e.getMessage());
			throw new NotFoundException(e.getMessage());
		}

		UriBuilder builder = UriBuilder.fromUri(uriInfo.getBaseUri()).path(StockItemResource.class).path(id.toString());
		return Response.created(builder.build()).build();
	}

	public static Store fromStoreTO(StoreTO storeTO, Store store) {
		store.setLocation(storeTO.getLocation());
		store.setName(storeTO.getName());
		return store;
	}

	public static StoreTO toStoreTO(Store store) {
		StoreTO storeTO = new StoreTO();
		storeTO.setId(store.getId());
		storeTO.setLocation(store.getLocation());
		storeTO.setName(store.getName());

		return storeTO;

	}

}
