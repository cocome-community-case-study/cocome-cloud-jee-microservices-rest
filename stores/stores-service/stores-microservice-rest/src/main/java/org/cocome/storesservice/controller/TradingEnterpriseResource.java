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
import org.cocome.enterpriseservice.StoreQuery.IStoreQuery;
import org.cocome.enterpriseservice.enterpriseQuery.IEnterpriseQuery;
import org.cocome.storesclient.domain.StoreTO;
import org.cocome.storesclient.domain.TradingEnterpriseTO;
import org.cocome.storesservice.domain.Store;
import org.cocome.storesservice.domain.TradingEnterprise;

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
		
		for(TradingEnterprise enterprise : enterpriseQuery.getAllEnterprises()) {
			collection.add(toEnterpriseTO(enterprise));
		}

		return collection;
	}

	@GET
	@Path("/{id}")
	public TradingEnterpriseTO find(@PathParam("id") Long id) {
		LOG.debug("REST: Trying to find Enterprise with id: " +id);
		TradingEnterprise enterprise = enterpriseQuery.getEnterpriseById(id);
		if(enterprise == null) {
			LOG.debug("REST: Could not find enterprise with id: " + id);
			throw new NotFoundException("Could not find enterprise with id: " + id);
		}
		
		return toEnterpriseTO(enterprise);
	}
    
	
	
	@POST
	@Consumes(MediaType.APPLICATION_XML)
	public Response create(@Context UriInfo uriInfo, TradingEnterpriseTO tradingEnterpriseTO) {
		LOG.debug("REST: Trying to create enterprise with name: " +tradingEnterpriseTO.getName());
	    Long id = enterpriseQuery.createEnterprise(tradingEnterpriseTO.getName());
	    
	    if(id == COULD_NOT_CREATE_ENTITY) {
	    	LOG.debug("REST: Could not create Enterprise with name: " + tradingEnterpriseTO.getName());
			throw new NotFoundException(" Could not create Enterprise with name: " + tradingEnterpriseTO.getName());
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
		tradingEnterpriseTO.setId(id);
		TradingEnterprise enterprise = enterpriseQuery.getEnterpriseById(id);
		
		if(enterprise == null) {
			LOG.debug("REST: Could not update Enterprise with id: " + id+ ". Enterprise not found");
			throw new NotFoundException("Could not update product with Id: " + id+". Product not found");
		}
		
		//We need to preserve StoresList
		enterprise = fromEnterpriseTO(tradingEnterpriseTO, enterprise);
		
		if(enterpriseQuery.updateEnterprise(enterprise)) {
			return Response.noContent().build();
		}
		LOG.debug("REST: Could not update Enterprise with id: " + id);
		throw new NotFoundException("Could not update enterprise with Id: " + id);
	}

	@DELETE
	@Path("/{id}")
	public Response deleteEnterprise(@PathParam("id") Long id) {
		LOG.debug("REST: Try do delete enterprise with id:  " + id);
		if (enterpriseQuery.deleteEnterprise(id)) {
			return Response.noContent().build();
		}

		LOG.debug("REST: Could not delete enterprise with id: " + id);
		throw new NotFoundException("Could not delete enterprise with Id: " + id);
	}

	

	@GET
	@Path("/{id}/stores")
	public Collection<StoreTO> findStores(@PathParam("id") Long enterpriseId) {
		LOG.debug("REST: Try to retrieve Stores from enterprise with Id: " + enterpriseId);

		TradingEnterprise tradingEnterprise = enterpriseQuery.getEnterpriseById(enterpriseId);

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

		if (enterpriseQuery.getEnterpriseById(enterpriseId) == null) {
			LOG.debug("REST: Could not create Store. Enterprise not found with id: " + enterpriseId);
			throw new NotFoundException("Could not find enterprise with Id: " + enterpriseId);
		}

		Long storeId = storeQuery.createStore(storeTO.getName(), storeTO.getLocation(), enterpriseId);

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