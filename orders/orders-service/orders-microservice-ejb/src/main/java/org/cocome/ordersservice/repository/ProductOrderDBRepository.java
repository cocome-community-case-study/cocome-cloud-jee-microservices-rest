package org.cocome.ordersservice.repository;

import java.util.Collection;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.cocome.ordersservice.domain.ProductOrder;

@Remote
@Stateless
public class ProductOrderDBRepository implements ProductOrderRepository {
	@PersistenceContext(unitName = "InventoryManager")
	private EntityManager em;

	@Override
	public Long create(ProductOrder entity) {
		em.persist(entity);
		return entity.getId();
	}

	@Override
	public ProductOrder find(Long id) {
		return em.find(ProductOrder.class, id);
	}

	@Override
	public void update(ProductOrder entity) {
		if (!em.contains(entity))
			entity = em.merge(entity);
		
		em.persist(entity);
	}

	@Override
	public void delete(ProductOrder entity) {
		if (!em.contains(entity))
			entity = em.merge(entity);
		
		em.remove(entity);
	}

	@Override
	public Collection<ProductOrder> all() {
		return em.createQuery("SELECT p FROM ProductOrder p", ProductOrder.class).getResultList();
	}
}
