package org.cocome.enterpriseservice.enterpriseQuery;

import java.util.Collection;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.validation.constraints.NotNull;

import org.cocome.storesservice.domain.TradingEnterprise;


public interface IEnterpriseQuery {
	
	public boolean createEnterprise(@NotNull String enterpriseName);
	
	public Collection<TradingEnterprise> getAllEnterprises();
	
	public TradingEnterprise getEnterpriseById(@NotNull long enterpriseId);

}
