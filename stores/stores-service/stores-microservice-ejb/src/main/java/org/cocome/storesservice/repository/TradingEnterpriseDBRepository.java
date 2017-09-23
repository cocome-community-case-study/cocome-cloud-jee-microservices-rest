package org.cocome.storesservice.repository;

import java.util.Collection;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.cocome.storesservice.domain.TradingEnterprise;

@Remote
@Stateless
public class TradingEnterpriseDBRepository implements TradingEnterpriseRepository {
	@PersistenceContext(unitName = "InventoryManager")
	private EntityManager em;
	
	public TradingEnterpriseDBRepository() {}

	@Override
	public Long create(TradingEnterprise entity) {
		em.persist(entity);
		return entity.getId();
	}

	@Override
	public TradingEnterprise find(Long id) {
		return em.find(TradingEnterprise.class, id);
	}

	@Override
	public void update(TradingEnterprise entity) {
		em.persist(entity);
	}

	@Override
	public void delete(TradingEnterprise entity) {
		em.remove(entity);
	}

	@Override
	public Collection<TradingEnterprise> all() {
		// TODO Auto-generated method stub
		return null;
	}
}
