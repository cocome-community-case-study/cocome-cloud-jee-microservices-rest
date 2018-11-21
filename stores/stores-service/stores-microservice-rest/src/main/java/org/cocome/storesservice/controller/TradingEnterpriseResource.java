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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.apache.log4j.Logger;
import org.cocome.storesclient.domain.StoreTO;
import org.cocome.storesclient.domain.TradingEnterpriseTO;
import org.cocome.storesservice.domain.Store;
import org.cocome.storesservice.domain.TradingEnterprise;
import org.cocome.storesservice.enterpriseQuery.IEnterpriseQuery;
import org.cocome.storesservice.exceptions.CreateException;
import org.cocome.storesservice.exceptions.QueryException;
import org.cocome.storesserviceservice.StoreQuery.IStoreQuery;

/**
 * RESTful resource for trading enterprises and nested store resources.
 * 
 * @author Nils Sommer
 *
 */
@RequestScoped
@Path("/trading-enterprises")
public class TradingEnterpriseResource {

	private final long COULD_NOT_CREATE_ENTITY = -1;

	@EJB
	private IEnterpriseQuery enterpriseQuery;

	@EJB
	private IStoreQuery storeQuery;

	private static final Logger LOG = Logger.getLogger(TradingEnterpriseResource.class);

	@GET
	public Collection<TradingEnterpriseTO> findAll() {
		LOG.debug("REST: Retrieve all Enterprises");
		Collection<TradingEnterpriseTO> collection = new LinkedList<TradingEnterpriseTO>();

		for (TradingEnterprise enterprise : enterpriseQuery.getAllEnterprises()) {
			collection.add(toEnterpriseTO(enterprise));
		}

		return collection;
	}

	@GET
	@Path("/{id}")
	public TradingEnterpriseTO find(@PathParam("id") Long id) {
		LOG.debug("REST: Trying to find Enterprise with id: " + id);

		TradingEnterprise enterprise;
		try {
			enterprise = enterpriseQuery.getEnterpriseById(id);
		} catch (QueryException e) {
			LOG.debug("REST: " + e.getMessage());
			throw new NotFoundException(e.getMessage());
		}

		return toEnterpriseTO(enterprise);
	}

	@POST
	@Consumes(MediaType.APPLICATION_XML)
	public Response create(@Context UriInfo uriInfo, TradingEnterpriseTO tradingEnterpriseTO) {
		LOG.debug("REST: Trying to create enterprise with name: " + tradingEnterpriseTO.getName());

		Long id;
		try {
			id = enterpriseQuery.createEnterprise(tradingEnterpriseTO.getName());
		} catch (CreateException e) {
			LOG.debug("REST: " + e.getMessage());
			throw new NotFoundException(e.getMessage());
		}

		UriBuilder builder = UriBuilder.fromUri(uriInfo.getBaseUri()).path(TradingEnterpriseResource.class)
				.path(id.toString());
		return Response.created(builder.build()).build();
	}

	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_XML)
	public Response update(@PathParam("id") Long id, TradingEnterpriseTO tradingEnterpriseTO) {
		LOG.debug("REST: Try to update Enterprise with Id: " + id);

		try {
			enterpriseQuery.updateEnterprise(id, tradingEnterpriseTO.getName());
		} catch (QueryException e) {

			LOG.debug("REST: " +e.getMessage());
			throw new NotFoundException(e.getMessage());
		}

		return Response.noContent().build();
	}

	@DELETE
	@Path("/{id}")
	public Response deleteEnterprise(@PathParam("id") Long id) {
		LOG.debug("REST: Try do delete enterprise with id:  " + id);

		try {
			enterpriseQuery.deleteEnterprise(id);
		} catch (QueryException e) {
			LOG.debug("REST: " + e.getMessage());
			throw new NotFoundException(e.getMessage());
		}

		return Response.noContent().build();
	}

	@GET
	@Path("/{id}/stores")
	public Collection<StoreTO> findStores(@PathParam("id") Long enterpriseId) {
		LOG.debug("REST: Try to retrieve Stores from enterprise with Id: " + enterpriseId);

		TradingEnterprise tradingEnterprise;
		try {
			tradingEnterprise = enterpriseQuery.getEnterpriseById(enterpriseId);
		} catch (QueryException e) {
			LOG.debug("REST:" +  e.getMessage());
			throw new NotFoundException(e.getMessage());
		}

		Collection<StoreTO> collection = new LinkedList<StoreTO>();
		for (Store store : tradingEnterprise.getStores()) {
			collection.add(StoreResource.toStoreTO(store));

		}
		return collection;
	}

	@POST
	@Path("/{id}/stores")
	@Consumes(MediaType.APPLICATION_XML)
	public Response createStore(@Context UriInfo uriInfo, @PathParam("id") Long enterpriseId, StoreTO storeTO) {
		LOG.debug("REST: Trying to create Store with name: " + storeTO.getName());

		Long storeId;
		try {
			storeId = storeQuery.createStore(storeTO.getName(), storeTO.getLocation(), enterpriseId);
		} catch (CreateException e) {
			LOG.debug("REST: "+ e.getMessage());
			throw new NotFoundException(e.getMessage());
		}

		

		UriBuilder builder = UriBuilder.fromUri(uriInfo.getBaseUri()).path(StoreResource.class)
				.path(storeId.toString());
		return Response.created(builder.build()).build();
	}

	public static TradingEnterpriseTO toEnterpriseTO(TradingEnterprise enterprise) {
		TradingEnterpriseTO enterpriseTO = new TradingEnterpriseTO();
		enterpriseTO.setId(enterprise.getId());
		enterpriseTO.setName(enterprise.getName());
		return enterpriseTO;
	}

	public static TradingEnterprise fromEnterpriseTO(TradingEnterpriseTO enterpriseTO, TradingEnterprise enterprise) {
		enterprise.setName(enterpriseTO.getName());
		return enterprise;
	}

}