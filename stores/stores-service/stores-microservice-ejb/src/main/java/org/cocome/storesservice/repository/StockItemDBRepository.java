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
	public void update(StockItem entity) {
		StockItem old = find(entity.getId());
		old.setAmount(entity.getAmount());
		old.setIncomingAmount(entity.getIncomingAmount());
		old.setMaxStock(entity.getMaxStock());
		old.setMinStock(entity.getMinStock());
		old.setProductId(entity.getProductId());
		old.setSalesPrice(entity.getSalesPrice());
		old.setStore(entity.getStore());
		em.persist(old);
	}

	@Override
	public void delete(StockItem entity) {
		if (!em.contains(entity))
			entity = em.merge(entity);
		
		em.persist(entity);
	}

	@Override
	public Collection<StockItem> all() {
		return em.createQuery("SELECT s FROM StockItem s", StockItem.class).getResultList();
	}
}
