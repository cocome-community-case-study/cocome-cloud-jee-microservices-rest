package org.cocome.productsservice.repository;

import java.util.Collection;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.cocome.productsservice.domain.Product;

@Remote
@Stateless
public class ProductDBRepository implements ProductRepository {
	@PersistenceContext(unitName = "InventoryManager")
	private EntityManager em;

	@Override
	public Long create(Product entity) {
		em.persist(entity);
		return entity.getId();
	}

	@Override
	public Product find(Long id) {
		return em.find(Product.class, id);
	}

	@Override
	public void update(Product entity) {
		if (!em.contains(entity))
			entity = em.merge(entity);
		
		em.persist(entity);
	}

	@Override
	public void delete(Product entity) {
		if (!em.contains(entity))
			entity = em.merge(entity);
		
		em.remove(entity);
	}

	@Override
	public Collection<Product> all() {
		return em.createQuery("SELECT p FROM Product p", Product.class).getResultList();
	}
}
