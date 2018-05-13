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
	public TradingEnterprise update(TradingEnterprise entity) {
		return em.merge(entity);
	}

	@Override
	public void delete(Long key) {
		TradingEnterprise entity = find(key);
		em.remove(entity);
	}

	@Override
	public Collection<TradingEnterprise> all() {
		return em.createQuery("SELECT t FROM TradingEnterprise t", TradingEnterprise.class).getResultList();
	}
}
