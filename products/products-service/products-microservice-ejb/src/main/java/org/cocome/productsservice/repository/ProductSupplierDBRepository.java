package org.cocome.productsservice.repository;

import java.util.Collection;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.cocome.productsservice.domain.ProductSupplier;

@Remote
@Stateless
public class ProductSupplierDBRepository implements ProductSupplierRepository {
	@PersistenceContext(unitName = "InventoryManager")
	private EntityManager em;
	
	@Override
	public Long create(ProductSupplier entity) {
		em.persist(entity);
		return entity.getId();
	}

	@Override
	public ProductSupplier find(Long id) {
		return em.find(ProductSupplier.class, id);
	}

	@Override
	public void update(ProductSupplier entity) {
		em.persist(entity);
	}

	@Override
	public void delete(ProductSupplier entity) {
		em.remove(entity);
	}

	@Override
	public Collection<ProductSupplier> all() {
		return em.createQuery("SELECT p FROM ProductSupplier p", ProductSupplier.class).getResultList();
	}
}
