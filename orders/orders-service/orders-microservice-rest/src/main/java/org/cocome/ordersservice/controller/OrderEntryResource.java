package org.cocome.ordersservice.controller;

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

import org.cocome.ordersservice.domain.OrderEntry;
import org.cocome.ordersservice.repository.OrderEntryRepository;

@RequestScoped
@Path("/order-entries")
public class OrderEntryResource {
	@EJB
	private OrderEntryRepository entryRepository;
	
	@GET
	@Path("/{id}")
	public OrderEntry find(@PathParam("id") Long id) {
		return entryRepository.find(id);
	}
	
	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_XML)
	public Response update(@PathParam("id") Long id, OrderEntry entry) {
		entryRepository.update(entry);
		return Response.noContent().build();
	}
	
	@DELETE
	@Path("/{id}")
	public Response delete(@PathParam("id") Long id) {
		OrderEntry entry = entryRepository.find(id);
		entryRepository.delete(entry);
		return Response.noContent().build();
	}
}
