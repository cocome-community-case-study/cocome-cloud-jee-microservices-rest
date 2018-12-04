package org.cocome.ordersservice.controller;

import java.util.Collection;
import java.util.LinkedList;

import javax.ejb.CreateException;
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
import org.cocome.ordersclient.domain.OrderEntryTO;
import org.cocome.ordersclient.domain.ProductOrderTO;
import org.cocome.ordersservice.domain.ProductOrder;
import org.cocome.ordersservice.entryquery.IEntryQuery;
import org.cocome.ordersservice.exceptions.QueryException;
import org.cocome.ordersservice.orderquery.IOrderQuery;

/**
 * REST-Interface for Order-Queries WITHOUT store-id <br>
 * Queries to get product-order by given store information are redirected to
 * Store Ressource
 * 
 * @author Niko Benkler
 * @author Robert Heinrich
 *
 */
@RequestScoped
@Path("/product-orders")
public class ProductOrderResource {

	private static final Logger LOG = Logger.getLogger(ProductOrderResource.class);

	@EJB
	IOrderQuery orderQuery;

	@EJB
	IEntryQuery entryQuery;
	
	/**
	 * Find all orders
	 * @return
	 */
	@GET
	public Collection<ProductOrderTO> findAll() {
		LOG.debug("REST: Try to find all Orders");
		try {
			return toProductOrderTOCollection(orderQuery.getAllOrders());
		} catch (QueryException e) {
			LOG.debug("REST: Could not Query alls Orders");
			throw new NotFoundException(e.getMessage());
		}

	}
	
	/**
	 * Find order with given id
	 * @param id
	 * @return
	 */
	@GET
	@Path("/{id}")
	public ProductOrderTO find(@PathParam("id") Long id) {
		LOG.debug("REST: Try to find Order with id: " + id);
		ProductOrder order;
		try {
			order = orderQuery.findOrderById(id);
		} catch (QueryException e) {
			LOG.debug("REST: Did not find Order with id: " + id);
			throw new NotFoundException(e.getMessage());
		}

		return toProductOrderTO(order);
	}
	
	/**
	 * Update Order with given id
	 * @param id
	 * @param order
	 * @return
	 */
	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_XML)
	public Response update(@PathParam("id") Long id, ProductOrderTO order) {
		LOG.debug("REST: Trying to update order with id: " + id);

		try {
			orderQuery.updateOrder(id, order.getDeliveryDate(), order.getOrderingDate());
		} catch (QueryException e) {
			LOG.debug("REST: Could not update Order with id: " + id);
			throw new NotFoundException(e.getMessage());
		}
		return Response.noContent().build();

	}

	/**
	 * Delete order with given id
	 * @param id
	 * @return
	 */
	@DELETE
	@Path("/{id}")
	public Response delete(@PathParam("id") Long id) {
		LOG.debug("REST: Trying to delete Order with id: " + id);

		try {
			orderQuery.deleteOrder(id);
		} catch (QueryException e) {
			LOG.debug("REST: Could not delete Order with id: " + id);
			throw new NotFoundException(e.getMessage());
		}
		return Response.noContent().build();

	}
	
	/**
	 * get entries of given order (id)
	 * @param orderId
	 * @return
	 */
	@GET
	@Path("/{id}/order-entries")
	public Collection<OrderEntryTO> getEntries(@PathParam("id") Long orderId) {
		LOG.debug("REST: Trying to get all entries from order with order id: " + orderId);
		ProductOrder order;
		try {
			order = orderQuery.findOrderById(orderId);
		} catch (QueryException e) {
			LOG.debug("REST: Did not find Order with id: " + orderId);
			throw new NotFoundException(e.getMessage());
		}

		return OrderEntryResource.toEntryTOCollection(order.getOrderEntries());

	}

	/**
	 * Add new entry to given order
	 * @param uriInfo
	 * @param orderId
	 * @param entry
	 * @return
	 */
	@POST
	@Path("/{id}/order-entries")
	@Consumes(MediaType.APPLICATION_XML)
	public Response createEntry(@Context UriInfo uriInfo, @PathParam("id") Long orderId, OrderEntryTO entry) {
		LOG.debug("REST: Create Entry for order with order id: " + orderId);
		Long entryId;
		try {
			//entry query updates corresponding order
			entryId = entryQuery.createEntry(orderId, entry.getProductId(), entry.getAmount());
		} catch (CreateException e) {
			LOG.debug("REST: Could not create Entry for order with order id: " + orderId);
			throw new NotFoundException(e.getMessage());
		}

		UriBuilder builder = UriBuilder.fromUri(uriInfo.getBaseUri()).path(OrderEntryResource.class)
				.path(entryId.toString());
		return Response.created(builder.build()).build();
	}

	public static ProductOrderTO toProductOrderTO(ProductOrder order) {
		ProductOrderTO orderTO = new ProductOrderTO();
		orderTO.setId(order.getId());
		orderTO.setDeliveryDate(order.getDeliveryDate());
		orderTO.setOrderingDate(order.getOrderingDate());
		orderTO.setStoreId(order.getStoreId());
		orderTO.setEntries(OrderEntryResource.toEntryTOCollection(order.getOrderEntries()));

		return orderTO;
	}

	public static Collection<ProductOrderTO> toProductOrderTOCollection(Collection<ProductOrder> orders) {
		Collection<ProductOrderTO> orderTOs = new LinkedList<ProductOrderTO>();

		for (ProductOrder order : orders) {
			orderTOs.add(toProductOrderTO(order));
		}
		return orderTOs;
	}

}
