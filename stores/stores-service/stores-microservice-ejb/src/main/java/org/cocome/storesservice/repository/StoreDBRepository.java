package org.cocome.storesservice.repository;

import java.util.Collection;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.cocome.storesservice.domain.Store;
import org.cocome.storesservice.domain.TradingEnterprise;

/**
 * This class uses a InventoryManager defined in persistance.xml to access
 * Database. <br>
 * Its methods should only be used by Store/EnterpriseQuery, as this class does
 * not do any specific error handling, except database errors
 * 
 * @author Niko Benkler
 * @author Robert Heinrich
 *
 */
@Local
@Stateless
public class StoreDBRepository implements StoreRepository {
	@PersistenceContext(unitName = "InventoryManager")
	private EntityManager em;

	private static final Logger LOG = Logger.getLogger(StoreDBRepository.class);

	@Override
	public Long create(Store entity) {
		try {
			em.persist(entity);
			return entity.getId();
		} catch (Exception e) {
			LOG.error("DATABASE: Error while creating Store with id: " + entity.getId() + " and name: "
					+ entity.getName() + "     " + e.getMessage());
			return null;
		}

	}

	@Override
	public Store find(Long id) {
		try {
			return em.find(Store.class, id);
		} catch (Exception e) {
			LOG.error("DATABASE: Could not find Store with id: " + id + "     " + e.getMessage());
			return null;
		}

	}

	@Override
	public Store update(Store entity) {
		try {
			return em.merge(entity);

		} catch (Exception e) {
			LOG.error("DATABASE: Error while updating Store with id: " + entity.getId() + " and name: "
					+ entity.getName() + "     " + e.getMessage());
			return null;
		}
	}

	@Override
	public void delete(Long key) {
		try {
			Store entity = find(key);
			em.remove(entity);
		} catch (Exception e) {
			LOG.error("DATABASE: Could not delete store with id: " + key + "     " + e.getMessage());
		}

	}

	@Override
	public Collection<Store> all() {
		try {
			return em.createQuery("SELECT s FROM Store s", Store.class).getResultList();
		} catch (Exception e) {
			LOG.error("DATABASE: Could not retrieve alls Stores" + "     " + e.getMessage());
			return null;
		}

	}
}
