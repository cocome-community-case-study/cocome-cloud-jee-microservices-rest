package org.cocome.ordersservice.controller;

import java.util.Collection;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.cocome.ordersservice.domain.ProductOrder;
import org.cocome.ordersservice.repository.ProductOrderRepository;

@RequestScoped
@Path("/stores")
public class StoreResource {
	@EJB
	private ProductOrderRepository orderRepository;
	
	@GET
	@Path("/{id}/product-orders")
	public Collection<ProductOrder> getOrders(@PathParam("id") Long storeId) {
		// TODO: Implement me!
		return null;
	}
	
	@POST
	@Path("/{id}/product-orders")
	@Consumes(MediaType.APPLICATION_XML)
	public Response createOrder(@Context UriInfo uriInfo, @PathParam("id") Long storeId, ProductOrder order) {
		order.setStoreId(storeId);
		Long orderId = orderRepository.create(order);
		UriBuilder builder = UriBuilder.fromUri(uriInfo.getBaseUri()).path(ProductOrderResource.class).path(orderId.toString());
		return Response.created(builder.build()).build();
	}
}
