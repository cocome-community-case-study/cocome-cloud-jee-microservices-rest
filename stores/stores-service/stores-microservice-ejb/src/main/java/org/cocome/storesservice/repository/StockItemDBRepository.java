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
	public void create(StockItem entity) {
		em.persist(entity);
	}

	@Override
	public StockItem find(Long id) {
		return em.find(StockItem.class, id);
	}

	@Override
	public void update(StockItem entity) {
		em.persist(entity);
	}

	@Override
	public void delete(StockItem entity) {
		em.persist(entity);
	}

	@Override
	public Collection<StockItem> all() {
		// TODO Auto-generated method stub
		return null;
	}
}