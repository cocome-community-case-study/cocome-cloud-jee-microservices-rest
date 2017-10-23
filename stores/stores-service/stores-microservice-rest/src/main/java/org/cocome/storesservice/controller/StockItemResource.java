package org.cocome.storesservice.controller;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.cocome.storesservice.domain.StockItem;
import org.cocome.storesservice.repository.StockItemRepository;

/**
 * RESTful resource for stock items.
 * 
 * @author Nils Sommer
 *
 */
@RequestScoped
@Path("/stock-items")
public class StockItemResource {
	@EJB
	private StockItemRepository stockItemRepository;
	
	@GET
	@Path("/{id}")
	public StockItem find(@PathParam("id") Long id) {
		return stockItemRepository.find(id);
	}
	
	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_XML)
	public Response update(@PathParam("id") Long id, StockItem stockItem) {
		stockItem.setId(id);
		stockItemRepository.update(stockItem);
		return Response.noContent().build();
	}
	
	@DELETE
	@Path("/{id}")
	public Response delete(@PathParam("id") Long id) {
		StockItem stockItem = stockItemRepository.find(id);
		stockItemRepository.delete(stockItem);
		return Response.noContent().build();
	}
}
