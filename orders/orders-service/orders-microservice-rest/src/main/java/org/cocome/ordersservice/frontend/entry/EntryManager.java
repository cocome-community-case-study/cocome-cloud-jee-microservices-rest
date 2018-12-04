package org.cocome.ordersservice.frontend.entry;

import java.io.Serializable;

import javax.ejb.CreateException;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import org.cocome.ordersservice.entryquery.IEntryQuery;

/**
 * This CLass Provides access to backend regarding entry functionality
 * @author Niko Benkler
 * @author Robert Heinrich
 *
 */
@Named
@SessionScoped
public class EntryManager implements IEntryManager, Serializable {

	private static final long serialVersionUID = 1151268760064780617L;
	
	
	
	@EJB
	IEntryQuery entryQuery;

	/**
	 * Create Order Entry with given parametes
	 */
	@Override
	public long createEntry(@NotNull long orderId, @NotNull long productId, @NotNull long amount) throws CreateException {
		long entryId =entryQuery.createEntry(orderId, productId, amount);
		
	
		return entryId;
	}
}
