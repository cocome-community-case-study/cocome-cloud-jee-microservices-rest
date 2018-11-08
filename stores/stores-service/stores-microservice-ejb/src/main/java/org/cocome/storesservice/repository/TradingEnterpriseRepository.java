package org.cocome.storesservice.repository;

import javax.ejb.Local;

import org.cocome.storesservice.domain.TradingEnterprise;

@Local
public interface TradingEnterpriseRepository extends Repository<TradingEnterprise, Long> {

}
