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

import org.cocome.storesservice.domain.TradingEnterprise;
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
	
	@GET
	public Collection<TradingEnterprise> finAll() {
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
	public Response update(@PathParam("id") Long id, TradingEnterprise tradingEnterprise) {
		tradingEnterpriseRepository.update(tradingEnterprise);
		return Response.noContent().build();
	}
	
	@DELETE
	@Path("/{id}")
	public Response destroy(@PathParam("id") Long id) {
		TradingEnterprise tradingEnterprise = tradingEnterpriseRepository.find(id);
		tradingEnterpriseRepository.delete(tradingEnterprise);
		return Response.noContent().build();
	}
	
	// Creating and Fetching nested stores
}