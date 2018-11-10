package org.cocome.productsservice.repository;

import java.util.Collection;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.cocome.productsservice.domain.Product;
import org.cocome.productsservice.domain.ProductSupplier;

@Local
@Stateless
public class ProductSupplierDBRepository implements ProductSupplierRepository {
	@PersistenceContext(unitName = "InventoryManager")
	private EntityManager em;
	private static final Logger LOG = Logger.getLogger(ProductSupplierDBRepository.class);
	private final long COULD_NOT_CREATE_ENTITY = -1;
	
	@Override
	public Long create(ProductSupplier entity) {
		try {
			em.persist(entity);
		} catch (Exception e) {
			LOG.error("DATABASE: Database Error while creating ProductSupplier with name: " + entity.getName() + "     "
					+ e.getMessage());
			return COULD_NOT_CREATE_ENTITY;
		}
		return entity.getId();
	}

	@Override
	public ProductSupplier find(Long id) {
		ProductSupplier supplier;
		try {
			supplier = em.find(ProductSupplier.class, id);
		} catch (Exception e) {
			LOG.error("DATABASE: Database Error while finding Supplier with Id: " + id + "     " + e.getMessage());
			return null;
		}

		return supplier;
	}

	@Override
	public ProductSupplier update(ProductSupplier entity) {
		try {
			ProductSupplier supplier = em.merge(entity);
			return supplier;
		} catch (Exception e) {
			LOG.error("DATABASE: Database error while updating Supplier with Id " + entity.getId() + "     "
					+ e.getMessage());
			return null;
		}
	}

	@Override
	public void delete(Long key) {
		try {
			ProductSupplier entity = find(key);
			em.remove(entity);
		} catch (Exception e) {
			LOG.error("DATABASE: Could not delete supplier with id: " + key + "     " + e.getMessage());
		}
	}

	@Override
	public Collection<ProductSupplier> all() {
		try {
			return em.createQuery("SELECT p FROM ProductSupplier p", ProductSupplier.class).getResultList();
		} catch (Exception e) {
			LOG.error("DATABASE: Database error while retrieving all Products" + "     " + e.getMessage());
			return null;
		}

	}
}
