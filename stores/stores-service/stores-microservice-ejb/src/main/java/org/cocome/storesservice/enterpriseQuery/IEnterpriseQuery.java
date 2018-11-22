package org.cocome.storesservice.enterpriseQuery;

import java.util.Collection;

import javax.validation.constraints.NotNull;

import org.cocome.storesservice.domain.TradingEnterprise;
import org.cocome.storesservice.exceptions.CreateException;
import org.cocome.storesservice.exceptions.QueryException;


public interface IEnterpriseQuery {
	
	public long createEnterprise(@NotNull String enterpriseName) throws CreateException;
	
	public Collection<TradingEnterprise> getAllEnterprises() throws QueryException;
	
	public TradingEnterprise getEnterpriseById(@NotNull long enterpriseId) throws QueryException;
	
	public void deleteEnterprise(@NotNull long enterpriseId) throws QueryException;
	
	public void updateEnterprise(@NotNull long id, @NotNull String name) throws QueryException;
	

}
