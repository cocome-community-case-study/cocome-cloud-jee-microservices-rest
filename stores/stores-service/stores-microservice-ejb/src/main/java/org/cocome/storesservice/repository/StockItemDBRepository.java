package org.cocome.storesservice.repository;

import java.util.Collection;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.cocome.storesservice.domain.StockItem;

@Remote
@Stateless
public class StockItemDBRepository implements StockItemRepository {
	@PersistenceContext(unitName = "InventoryManager")
	private EntityManager em;

	@Override
	public Long create(StockItem entity) {
		em.persist(entity);
		return entity.getId();
	}

	@Override
	public StockItem find(Long id) {
		return em.find(StockItem.class, id);
	}

	@Override
	public StockItem update(StockItem entity) {
		return em.merge(entity);
	}

	@Override
	public void delete(Long key) {
		StockItem entity = find(key);
		em.remove(entity);
	}

	@Override
	public Collection<StockItem> all() {
		return em.createQuery("SELECT s FROM StockItem s", StockItem.class).getResultList();
	}
}
