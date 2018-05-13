package org.cocome.storesservice.repository;

import java.util.Collection;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.cocome.storesservice.domain.Store;

@Remote
@Stateless
public class StoreDBRepository implements StoreRepository {
	@PersistenceContext(unitName = "InventoryManager")
	private EntityManager em;
	
	@Override
	public Long create(Store entity) {
		em.persist(entity);
		return entity.getId();
	}

	@Override
	public Store find(Long id) {
		return em.find(Store.class, id);
	}

	@Override
	public Store update(Store entity) {
		return em.merge(entity);
	}

	@Override
	public void delete(Long key) {
		Store entity = find(key);
		em.remove(entity);
	}

	@Override
	public Collection<Store> all() {
		return em.createQuery("SELECT s FROM Store s", Store.class).getResultList();
	}
}
