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
import org.cocome.ordersservice.exceptions.QueryException;

/**
 * REST-Interface for Entry-Queries
 * @author Niko Benkler
 * @author Robert Heinrich
 *
 */
@RequestScoped
@Path("/order-entries")
public class OrderEntryResource {

	@EJB
	IEntryQuery entryQuery;

	private static final Logger LOG = Logger.getLogger(OrderEntryResource.class);
    
	/**
	 * 
	 * @return all Order entries for all stores
	 */
	@GET
	public Collection<OrderEntryTO> findAll(){
		LOG.debug("REST: Find all Entries");
		try {
			return toEntryTOCollection(entryQuery.getAllEntries());
		} catch (QueryException e) {
			LOG.debug("REST: Could not Query all Orders ") ;
			throw new NotFoundException("Could not Query all Orders " );
		}
	}
	
	/**
	 * 
	 * @param id
	 * @return order entry with id
	 */
	@GET
	@Path("/{id}")
	public OrderEntryTO find(@PathParam("id") Long id) {
		LOG.debug("REST: Try to find OrderEntry with id: " + id);
		OrderEntry entry;
		try {
			entry = entryQuery.findEntryById(id);
		} catch (QueryException e) {
			LOG.debug("REST: Did not find Entry with id: " + id);
			throw new NotFoundException("Did not find Entry with id: " + id);
		}
		

		return toEntryTO(entry);
	}

	/**
	 * Update order entry with given id
	 * @param id
	 * @param entryTO
	 * @return
	 */
	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_XML)
	public Response update(@PathParam("id") Long id, OrderEntryTO entryTO) {
		LOG.debug("REST: Trying to update entry with id: " + id);
		
		try {
			entryQuery.updateEntry(id, entryTO.getAmount());
		} catch (QueryException e) {
			LOG.debug("REST: Could not update Entry with id: " + id);
			throw new NotFoundException(e.getMessage());
		}
		return Response.noContent().build();
		
		
		

	}
 
	/**
	 * Delete order entry with given id
	 * @param id
	 * @return
	 */
	@DELETE
	@Path("/{id}")
	public Response delete(@PathParam("id") Long id) {
		LOG.debug("REST: Trying to delete Entry with id: " + id);
		
		try {
			entryQuery.deleteEntry(id);
		} catch (QueryException e) {
			LOG.debug("REST: Could not delete Entry with id: " + id);
			throw new NotFoundException(e.getMessage());
		}
		return Response.noContent().build();
		
	
		
	}

	/**
	 * Helper Method to change between local entity and transferable Object. <br>
	 * Important: Entry TO does not contain whole Order TO. Only a refference (its id)
	 * @param entry
	 * @return
	 */
	public static OrderEntryTO toEntryTO(OrderEntry entry) {
		OrderEntryTO entryTO = new OrderEntryTO();
		entryTO.setAmount(entry.getAmount());
		entryTO.setId(entry.getId());
		entryTO.setProductId(entry.getProductId());
		entryTO.setOrderId(entry.getOrder().getId());
		return entryTO;

	}
  
	/**
	 * Helper Method to change between local entity collection and transferable Object. <br>
	 * Important: Entry TO does not contain whole Order TO. Only a refference (its id)
	 * @param entry
	 * @return
	 */
	public static Collection<OrderEntryTO> toEntryTOCollection(Collection<OrderEntry> entries) {
		Collection<OrderEntryTO> entrieTOs = new LinkedList<OrderEntryTO>();
		for(OrderEntry entry: entries) {
			entrieTOs.add(OrderEntryResource.toEntryTO(entry));
		}
		return entrieTOs;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
