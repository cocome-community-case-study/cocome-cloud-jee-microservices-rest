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
	public void update(Store entity) {
		em.persist(entity);
	}

	@Override
	public void delete(Store entity) {
		em.remove(entity);
	}

	@Override
	public Collection<Store> all() {
		// TODO Auto-generated method stub
		return null;
	}
}
