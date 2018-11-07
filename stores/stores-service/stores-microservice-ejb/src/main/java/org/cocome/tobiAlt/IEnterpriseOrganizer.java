package org.cocome.tobiAlt;

import java.util.Collection;

import org.cocome.storesservice.domain.Store;
import org.cocome.storesservice.domain.TradingEnterprise;

/**
 * organizing enterprise in form of creating, deleting etc. handles TE as objects, does NOT work on them
 * (including in special getting the correct EnterpiriseManager to work with)
 *
 * @author Tobias Haßberg
 */
public interface IEnterpriseOrganizer {

	public IEnterpriseManager getActiveEnterprise(long enterpriseId);
	
	public Collection<TradingEnterprise> getAll();
	
	public long createNewEnterprise(String Name, Collection<Store> stores);
	
	public void deleteEnterprise(long enterpriseId);
	
}
