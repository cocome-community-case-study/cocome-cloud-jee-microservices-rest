package org.cocome.ordersservice.repository;

import java.util.Collection;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.cocome.ordersservice.domain.OrderEntry;

@Remote
@Stateless
public class OrderEntryDBRepository implements OrderEntryRepository {
	@PersistenceContext(unitName = "InventoryManager")
	private EntityManager em;
		
	@Override
	public Long create(OrderEntry entity) {
		em.persist(entity);
		return entity.getId();
	}

	@Override
	public OrderEntry find(Long id) {
		return em.find(OrderEntry.class, id);
	}

	@Override
	public void update(OrderEntry entity) {
		if (!em.contains(entity))
			entity = em.merge(entity);
		
		em.persist(entity);
	}

	@Override
	public void delete(OrderEntry entity) {
		if (!em.contains(entity))
			entity = em.merge(entity);
		
		em.remove(entity);
	}

	@Override
	public Collection<OrderEntry> all() {
		return em.createQuery("SELECT o FROM OrderEntry o", OrderEntry.class).getResultList();
	}
}
