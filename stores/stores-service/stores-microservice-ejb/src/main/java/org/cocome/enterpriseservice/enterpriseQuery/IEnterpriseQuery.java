package org.cocome.enterpriseservice.enterpriseQuery;

import java.util.Collection;

import javax.ejb.Local;
import javax.ejb.Remote;

import org.cocome.storesservice.domain.TradingEnterprise;


public interface IEnterpriseQuery {
	
	public boolean createEnterprise(String enterpriseName);
	
	public Collection<TradingEnterprise> getAllEnterprises();

}
