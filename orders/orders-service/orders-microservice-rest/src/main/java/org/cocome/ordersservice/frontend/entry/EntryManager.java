package org.cocome.ordersservice.frontend.entry;

import java.io.Serializable;

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
	
	private final long COULD_NOT_CREATE_ENTITY = -1;
	
	@EJB
	IEntryQuery entryQuery;

	@Override
	public long createEntry(long orderId, long productId, long amount) {
		long entryId =entryQuery.createEntry(orderId, productId, amount);
		
		if(entryId == COULD_NOT_CREATE_ENTITY) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Could not create OrderEntry ", null));
			return COULD_NOT_CREATE_ENTITY;
		}
		return entryId;
	}
}
