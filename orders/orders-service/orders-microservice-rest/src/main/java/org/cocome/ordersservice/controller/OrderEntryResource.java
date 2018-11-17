package org.cocome.ordersservice.controller;

import java.util.Collection;
import java.util.LinkedList;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.cocome.ordersclient.domain.OrderEntryTO;
import org.cocome.ordersservice.domain.OrderEntry;
import org.cocome.ordersservice.entryquery.IEntryQuery;

@RequestScoped
@Path("/order-entries")
public class OrderEntryResource {

	@EJB
	IEntryQuery entryQuery;

	private final long COULD_NOT_CREATE_ENTITY = -1;
	private static final Logger LOG = Logger.getLogger(OrderEntryResource.class);
    
	@GET
	public Collection<OrderEntryTO> findAll(){
		LOG.debug("REST: Find all Entries");
		return toEntryTOCollection(entryQuery.getAllEntries());
	}
	
	@GET
	@Path("/{id}")
	public OrderEntryTO find(@PathParam("id") Long id) {
		LOG.debug("REST: Try to find OrderEntry with id: " + id);
		OrderEntry entry = entryQuery.findEntryById(id);
		if (entry == null) {
			LOG.debug("REST: Did not find Entry with id: " + id);
			throw new NotFoundException("Did not find Entry with id: " + id);
		}

		return toEntryTO(entry);
	}

	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_XML)
	public Response update(@PathParam("id") Long id, OrderEntryTO entryTO) {
		LOG.debug("REST: Trying to update entry with id: " + id);
		if (entryQuery.updateEntry(id, entryTO.getAmount())) {
			return Response.noContent().build();
		}
		LOG.debug("REST: Could not update Entry with id: " + id);
		throw new NotFoundException("Could not update Entry with id: " + id);

	}

	@DELETE
	@Path("/{id}")
	public Response delete(@PathParam("id") Long id) {
		LOG.debug("REST: Trying to delete Entry with id: " + id);
		if (entryQuery.deleteEntry(id)) {
			return Response.noContent().build();
		}
		LOG.debug("REST: Could not delete Entry with id: " + id);
		throw new NotFoundException("Could not delete Entry with Id: " + id);
	}

	public static OrderEntryTO toEntryTO(OrderEntry entry) {
		OrderEntryTO entryTO = new OrderEntryTO();
		entryTO.setAmount(entry.getAmount());
		entryTO.setId(entry.getId());
		entryTO.setProductId(entry.getProductId());
		entryTO.setOrder(ProductOrderResource.toProductOrderTO(entry.getOrder()));
		return entryTO;

	}

	public static Collection<OrderEntryTO> toEntryTOCollection(Collection<OrderEntry> entries) {
		Collection<OrderEntryTO> entrieTOs = new LinkedList<OrderEntryTO>();
		for(OrderEntry entry: entries) {
			entrieTOs.add(OrderEntryResource.toEntryTO(entry));
		}
		return entrieTOs;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
