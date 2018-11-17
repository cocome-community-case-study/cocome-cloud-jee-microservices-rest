package org.cocome.storesservice.controller;

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
import org.cocome.storesclient.domain.StockItemTO;
import org.cocome.storesservice.domain.StockItem;
import org.cocome.storesserviceservice.StoreQuery.IStockQuery;

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
	private IStockQuery stockQuery;

	private static final Logger LOG = Logger.getLogger(StockItemResource.class);

	@GET
	public Collection<StockItemTO> findAll() {
		LOG.debug("REST: Retrieving all StockItems!");
		Collection<StockItemTO> collection = new LinkedList<StockItemTO>();

		for (StockItem item : stockQuery.getAllStockItems()) {
			collection.add(toStockItemTo(item));
		}
		return collection;
	}

	@GET
	@Path("/{id}")
	public StockItemTO find(@PathParam("id") Long id) {
		LOG.debug("REST: Trying to find StockItem with id: " + id);

		StockItem item = stockQuery.getStockItemById(id);

		if (item == null) {
			LOG.debug("REST: Could not find StockItem with id: " + id);
			throw new NotFoundException("Could not find StockItem with id: " + id);
		}
		return toStockItemTo(item);

	}

	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_XML)
	public Response update(@PathParam("id") Long id, StockItemTO stockItemTO) {
		LOG.debug("REST: Try to update StockItem with id" + id);

		if (stockQuery.updateStockeItem(id, stockItemTO.getSalesPrice(), stockItemTO.getAmount(),
				stockItemTO.getMinStock(), stockItemTO.getMaxStock(), stockItemTO.getBarcode(),
				stockItemTO.getIncomingAmount())) {
			return Response.noContent().build();
		}

		LOG.debug("REST: Could not update StockItem with id: " + id);
		throw new NotFoundException("Could not update StockItem with Id: " + id );
	}

	@DELETE
	@Path("/{id}")
	public Response delete(@PathParam("id") Long id) {
		LOG.debug("REST: Try to delete StockItem with Id: " + id);
		if(stockQuery.deleteStockItem(id)) {
			return Response.noContent().build();
		}
		LOG.debug("REST: Could not delete StockItem with id: " + id);
		throw new NotFoundException("Could not delete StockItem with Id: " + id );
	}

	public static StockItem fromStockItemTO(StockItemTO itemTO, StockItem item) {
		item.setAmount(itemTO.getAmount());
		item.setBarcode(itemTO.getBarcode());
		item.setIncomingAmount(itemTO.getIncomingAmount());
		item.setMaxStock(itemTO.getMaxStock());
		item.setMinStock(itemTO.getMaxStock());
		item.setMinStock(itemTO.getMinStock());
		// item.setProductId(itemTO.getProductId()); should not change?!
		item.setSalesPrice(itemTO.getSalesPrice());

		return item;
	}

	public static StockItemTO toStockItemTo(StockItem item) {
		StockItemTO itemTO = new StockItemTO();
		itemTO.setAmount(item.getAmount());
		itemTO.setId(item.getId());
		itemTO.setIncomingAmount(item.getIncomingAmount());
		itemTO.setMaxStock(item.getMaxStock());
		itemTO.setMinStock(item.getMinStock());
		itemTO.setProductId(item.getProductId());
		itemTO.setSalesPrice(item.getSalesPrice());
		itemTO.setStore(StoreResource.toStoreTO(item.getStore()));
		itemTO.setBarcode(item.getBarcode());

		return itemTO;

	}

}
