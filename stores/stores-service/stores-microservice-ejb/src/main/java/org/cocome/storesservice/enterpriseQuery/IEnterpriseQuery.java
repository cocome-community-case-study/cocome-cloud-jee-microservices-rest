package org.cocome.storesservice.enterpriseQuery;

import java.util.Collection;

import javax.validation.constraints.NotNull;

import org.cocome.storesservice.domain.TradingEnterprise;


public interface IEnterpriseQuery {
	
	public long createEnterprise(@NotNull String enterpriseName);
	
	public Collection<TradingEnterprise> getAllEnterprises();
	
	public TradingEnterprise getEnterpriseById(@NotNull long enterpriseId);
	
	public boolean deleteEnterprise(@NotNull long enterpriseId);
	
	public boolean updateEnterprise(@NotNull long id, @NotNull String name);
	

}
