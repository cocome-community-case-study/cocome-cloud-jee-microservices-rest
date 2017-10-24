package org.cocome.ordersservice.controller;

import java.util.Collection;
import java.util.Date;

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

import org.cocome.ordersservice.domain.OrderEntry;
import org.cocome.ordersservice.domain.ProductOrder;
import org.cocome.ordersservice.repository.OrderEntryRepository;
import org.cocome.ordersservice.repository.ProductOrderRepository;

@RequestScoped
@Path("/product-orders")
public class ProductOrderResource {
	@EJB
	private ProductOrderRepository orderRepository;
	
	@EJB
	private OrderEntryRepository entryRepository;
	
	@GET
	@Path("/{id}")
	public ProductOrder find(@PathParam("id") Long id) {
		return orderRepository.find(id);
	}
	
	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_XML)
	public Response update(@PathParam("id") Long id, ProductOrder order) {
		order.setId(id);
		orderRepository.update(order);
		return Response.noContent().build();
	}
	
	@DELETE
	@Path("/{id}")
	public Response delete(@PathParam("id") Long id) {
		orderRepository.delete(id);
		return Response.noContent().build();
	}
	
	@GET
	@Path("/{id}/order-entries")
	public Collection<OrderEntry> getEntries(@PathParam("id") Long orderId) {
		return orderRepository.find(orderId).getOrderEntries();
	}
	
	@POST
	@Path("/{id}/order-entries")
	@Consumes(MediaType.APPLICATION_XML)
	public Response createEntry(@Context UriInfo uriInfo, @PathParam("id") Long orderId, OrderEntry entry) {
		ProductOrder order = orderRepository.find(orderId);
		entry.setOrder(order);
		Long entryId = entryRepository.create(entry);
		UriBuilder builder = UriBuilder.fromUri(uriInfo.getBaseUri()).path(OrderEntryResource.class).path(entryId.toString());
		return Response.created(builder.build()).build();
	}
}
