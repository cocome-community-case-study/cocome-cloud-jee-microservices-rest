package org.cocome.ordersservice.repository;

import java.util.Collection;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.cocome.ordersservice.domain.OrderEntry;

/**
 * Entry-Repo: Simple CRUD-Repo. High-Level functionality is provided in corresponding Query
 * @author Niko Benkler
 * @author Robert Heinrich
 *
 */
@Remote
@Stateless
public class OrderEntryDBRepository implements OrderEntryRepository {
	@PersistenceContext(unitName = "InventoryManager")
	private EntityManager em;

	private static final Logger LOG = Logger.getLogger(OrderEntryDBRepository.class);

	@Override
	public Long create(OrderEntry entity) {
		try {
			em.persist(entity);
			em.flush();
			return entity.getId();
		} catch (Exception e) {
			LOG.error("DATABASE: Could not create OrderEntry with Id: " + entity.getProductId() + " and OrderId: "
					+ entity.getOrder().getId());
			return null;
		}
	}

	@Override
	public OrderEntry find(Long id) {
		try {
			return em.find(OrderEntry.class, id);
		} catch (Exception e) {
			LOG.error("DATABASE: Could not find OrderEntry with id: " + id + "     " + e.getMessage());
			return null;
		}

	}

	@Override
	public OrderEntry update(OrderEntry entity) {

		try {
			return em.merge(entity);
		} catch (Exception e) {
			LOG.error("DATABASE: Could not update OrderEntry with Id: " + entity.getId() + " and OrderId: "
					+ entity.getOrder().getId() + "     " + e.getMessage());
			return null;
		}
	}

	@Override
	public boolean delete(Long key) {
		try {
			OrderEntry entity = find(key);
			if (entity != null) {
				em.remove(entity);
				return true;
			}

		} catch (Exception e) {
			LOG.error("DATABASE: Could not delete OrderEntry with Id: " + key + "     " + e.getMessage());
		}
		return false;
	}

	@Override
	public Collection<OrderEntry> all() {
		try {
			return em.createQuery("SELECT o FROM OrderEntry o", OrderEntry.class).getResultList();
		} catch (Exception e) {
			LOG.error("DATABASE: Could not retrieve all Order Entries" + "     " + e.getMessage());
			return null;
		}
	}
}
