package org.cocome.ordersservice.frontend.entry;

import java.io.Serializable;

import javax.ejb.CreateException;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.cocome.ordersservice.entryquery.IEntryQuery;

@Named
@ApplicationScoped
public class EntryManager implements IEntryManager, Serializable {

	private static final long serialVersionUID = 1151268760064780617L;
	
	
	
	@EJB
	IEntryQuery entryQuery;

	@Override
	public long createEntry(long orderId, long productId, long amount) throws CreateException {
		long entryId =entryQuery.createEntry(orderId, productId, amount);
		
	
		return entryId;
	}
}
