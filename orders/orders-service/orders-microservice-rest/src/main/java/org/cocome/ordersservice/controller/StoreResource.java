package org.cocome.ordersservice.controller;

import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.apache.log4j.Logger;
import org.cocome.ordersclient.domain.ProductOrderTO;
import org.cocome.ordersservice.domain.ProductOrder;
import org.cocome.ordersservice.exceptions.QueryException;
import org.cocome.ordersservice.orderquery.IOrderQuery;

/**
 * This class provides  REST-Interface-dunctionality for order of a specific store
 * @author Niko Benkler
 * @author Robert Heinrich
 *
 */

@RequestScoped
@Path("/stores")
public class StoreResource {
	
	
	@EJB
	IOrderQuery orderQuery;
	
	
	private static final Logger LOG = Logger.getLogger(StoreResource.class);
	
	/**
	 * Return all product orders of a specific store
	 * @param storeId
	 * @return
	 */
	@GET
	@Path("/{id}/product-orders")
	public Collection<ProductOrderTO> getOrders(@PathParam("id") Long storeId) {
		LOG.debug("REST: Retrieve all order for store with store id: " + storeId);
		
		Collection<ProductOrder> orders;
		try {
			orders = orderQuery.getOrdersByStoreId(storeId);
		} catch (QueryException e) {
			LOG.debug("REST: Could not retrieve orders for store with storeId: " + storeId);
			throw new NotFoundException(e.getMessage());
		}
		
		
		return ProductOrderResource.toProductOrderTOCollection(orders);
	}
	
	/**
	 * Create Order only possible by giving a store id
	 * @param uriInfo
	 * @param storeId
	 * @param orderTO
	 * @return
	 */
	@POST
	@Path("/{id}/product-orders")
	@Consumes(MediaType.APPLICATION_XML)
	public Response createOrder(@Context UriInfo uriInfo, @PathParam("id") Long storeId, ProductOrderTO orderTO) {
		LOG.debug("REST: Trying to create Order for store with id: " + storeId );
		
		Long orderId;
		try {
			orderId = orderQuery.createOrder(orderTO.getDeliveryDate(), orderTO.getOrderingDate(), storeId);
		} catch (CreateException e) {
			LOG.debug("REST: Could not create Order for Store with storeId " + storeId);
			throw new NotFoundException(e.getMessage());
		}

		UriBuilder builder = UriBuilder.fromUri(uriInfo.getBaseUri()).path(ProductOrderResource.class).path(orderId.toString());
		return Response.created(builder.build()).build();
	}
}
