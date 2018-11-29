package org.cocome.ordersservice.repository;

import java.util.Collection;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.cocome.ordersservice.domain.ProductOrder;
/**
 * Order-Repo: Simple CRUD-Repo. High-Level functionality is provided in corresponding Query
 * @author Niko Benkler
 * @author Robert Heinrich
 *
 */

@Remote
@Stateless
public class ProductOrderDBRepository implements ProductOrderRepository {

	private static final Logger LOG = Logger.getLogger(ProductOrderDBRepository.class);

	@PersistenceContext(unitName = "InventoryManager")
	private EntityManager em;

	@Override
	public Long create(ProductOrder entity) {
		try {
			em.persist(entity);
			em.flush();
			return entity.getId();
		} catch (Exception e) {
			LOG.error("DATABASE: Could not create ProductOrder with productId: " + entity.getId() + e.getMessage());
			return null;
		}
	}

	@Override
	public ProductOrder find(Long id) {
		try {
			return em.find(ProductOrder.class, id);
		} catch (Exception e) {
			LOG.error("DATABASE: Could not find ProductOrder with id: " + id + "     " + e.getMessage());
			return null;
		}
	}

	@Override
	public ProductOrder update(ProductOrder entity) {
		try {
			return em.merge(entity);
		} catch (Exception e) {
			LOG.error("DATABASE: Could not update ProductOrder with Id: " + entity.getId() + e.getMessage());
			return null;
		}
	}

	@Override
	public boolean delete(Long key) {
		try {
			ProductOrder entity = find(key);
			if (entity != null) {
				em.remove(entity);
				return true;
			}

		} catch (Exception e) {
			LOG.error("DATABASE: Could not delete ProductOrder with Id: " + key + "     " + e.getMessage());
		}
		return false;
	}

	@Override
	public Collection<ProductOrder> all() {
		try {
			return em.createQuery("SELECT p FROM ProductOrder p", ProductOrder.class).getResultList();
		} catch (Exception e) {
			LOG.error("DATABASE: Could not retrieve all Product Orders" + "     " + e.getMessage());
			return null;
		}
	}
}
