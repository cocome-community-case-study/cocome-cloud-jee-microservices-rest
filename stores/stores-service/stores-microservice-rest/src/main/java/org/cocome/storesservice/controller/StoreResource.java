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

import org.cocome.storesservice.domain.StockItem;
import org.cocome.storesservice.domain.Store;
import org.cocome.storesservice.repository.StockItemRepository;
import org.cocome.storesservice.repository.StoreRepository;

/**
 * RESTful resource for stores and nested stock items. To create stores or fetch them by their trading enterprise associaton,
 * the TradingEnterpriseResource is used.
 * 
 * @author Nils Sommer
 *
 */
@RequestScoped
@Path("/stores")
public class StoreResource {
	@EJB
	private StoreRepository storeRepository;
	
	@EJB
	private StockItemRepository stockItemRepository;
	
	@GET
	@Path("/{id}")
	public Store find(@PathParam("id") Long id) {
		return storeRepository.find(id);
	}
	
	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_XML)
	public Response update(@PathParam("id") Long id, Store store) {
		storeRepository.update(store);
		return Response.noContent().build();
	}
	
	@DELETE
	@Path("/{id}")
	public Response delete(@PathParam("id") Long id) {
		Store store = storeRepository.find(id);
		storeRepository.delete(store);
		return Response.noContent().build();
	}
	
	// Creating and fetching nested stock items
	
	@GET
	@Path("/{id}/stock-items")
	public Collection<StockItem> findStockItems(@PathParam("id") Long storeId) {
		Store store = storeRepository.find(storeId);
		return store.getStockItems();
	}
	
	@POST
	@Path("/{id}/stock-items")
	@Consumes(MediaType.APPLICATION_XML)
	public Response createStockItem(@Context UriInfo uriInfo, @PathParam("id") Long storeId, StockItem stockItem) {
		Store store = storeRepository.find(storeId);
		stockItem.setStore(store);
		Long id = stockItemRepository.create(stockItem);
		UriBuilder builder = UriBuilder.fromUri(uriInfo.getBaseUri()).path(StockItemResource.class).path(id.toString());
		return Response.created(builder.build()).build();
	}
}
