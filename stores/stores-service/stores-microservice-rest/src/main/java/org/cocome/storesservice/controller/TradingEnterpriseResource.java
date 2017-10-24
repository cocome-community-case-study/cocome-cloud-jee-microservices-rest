package org.cocome.storesservice.controller;

import java.util.Collection;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.cocome.storesservice.domain.Store;
import org.cocome.storesservice.domain.TradingEnterprise;
import org.cocome.storesservice.repository.StoreRepository;
import org.cocome.storesservice.repository.TradingEnterpriseRepository;

/**
 * RESTful resource for trading enterprises and nested store resources.
 * 
 * @author Nils Sommer
 *
 */
@RequestScoped
@Path("/trading-enterprises")
public class TradingEnterpriseResource {
	@EJB
	private TradingEnterpriseRepository tradingEnterpriseRepository;
	
	@EJB
	private StoreRepository storeRepository;
	
	@GET
	public Collection<TradingEnterprise> findAll() {
		return tradingEnterpriseRepository.all();
	}
	
	@GET
	@Path("/{id}")
	public TradingEnterprise find(@PathParam("id") Long id) {
		return tradingEnterpriseRepository.find(id);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_XML)
	public Response create(@Context UriInfo uriInfo, TradingEnterprise tradingEnterprise) {
		Long id = tradingEnterpriseRepository.create(tradingEnterprise);
		UriBuilder builder = UriBuilder.fromUri(uriInfo.getBaseUri()).path(TradingEnterpriseResource.class).path(id.toString());
		return Response.created(builder.build()).build();
	}
	
	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_XML)
	public Response update(@PathParam("id") Long id, TradingEnterprise tradingEnterprise) {
		tradingEnterprise.setId(id);
		tradingEnterpriseRepository.update(tradingEnterprise);
		return Response.noContent().build();
	}
	
	@DELETE
	@Path("/{id}")
	public Response destroy(@PathParam("id") Long id) {
		tradingEnterpriseRepository.delete(id);
		return Response.noContent().build();
	}
	
	// Creating and Fetching nested stores
	
	@GET
	@Path("/{id}/stores")
	public Collection<Store> findStores(@PathParam("id") Long enterpriseId) {
		TradingEnterprise tradingEnterprise = tradingEnterpriseRepository.find(enterpriseId);
		return tradingEnterprise.getStores();
	}
	
	@POST
	@Path("/{id}/stores")
	@Consumes(MediaType.APPLICATION_XML)
	public Response createStore(@Context UriInfo uriInfo, @PathParam("id") Long enterpriseId, Store store) {
		TradingEnterprise tradingEnterprise = tradingEnterpriseRepository.find(enterpriseId);
		store.setEnterprise(tradingEnterprise);
		Long storeId = storeRepository.create(store);
		UriBuilder builder = UriBuilder.fromUri(uriInfo.getBaseUri()).path(StoreResource.class).path(storeId.toString());
		return Response.created(builder.build()).build();
	}
}