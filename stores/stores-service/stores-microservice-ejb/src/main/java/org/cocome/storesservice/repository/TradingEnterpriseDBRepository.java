package org.cocome.storesservice.repository;

import java.util.Collection;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.cocome.storesservice.domain.TradingEnterprise;

/**
 * Basic Crud Repository for
 * {@link org.cocome.storesservice.domain.TradingEnterprise} entities <br>
 * Only Database Exceptions are handeled <br>
 * 
 * 
 * @author Niko Benkler
 * @author Robert Heinrich
 *
 */
@Local
@Stateless
public class TradingEnterpriseDBRepository implements TradingEnterpriseRepository {

	@PersistenceContext(unitName = "InventoryManager")
	private EntityManager em;

	

	private static final Logger LOG = Logger.getLogger(TradingEnterprise.class);

	public TradingEnterpriseDBRepository() {
	}

	/**
	 * Creation of entity TradingEnterprise <br>
	 * 
	 * @return Id of Tradingenterprise
	 */
	@Override
	public Long create(TradingEnterprise entity) {
		try {
			em.persist(entity);
			em.flush();
		} catch (Exception e) {
			LOG.error("DATABASE: Database Error while creating Enterprise with name: " + entity.getName() + "     "
					+ e.getMessage());
			return null;
		}
		return entity.getId();
	}

	/**
	 * Find enterprise by id
	 * 
	 * @return null if no enterprise found; TradingEnterprise else
	 */
	@Override
	public TradingEnterprise find(Long id) {
		TradingEnterprise enterprise;
		try {
			enterprise = em.find(TradingEnterprise.class, id);
		} catch (Exception e) {
			LOG.error("DATABASE: Database Error while finding Enterprise with Id: " + id + "     " + e.getMessage());
			return null;
		}

		return enterprise;
	}

	@Override
	public TradingEnterprise update(TradingEnterprise entity) {
		try {
			TradingEnterprise enterprise = em.merge(entity);
			return enterprise;
		} catch (Exception e) {
			LOG.error("DATABASE: Database error while updating Enterprise with Id " + entity.getId() + "     "
					+ e.getMessage());
			return null;
		}

	}

	@Override
	public boolean delete(Long key) {
		try {
			TradingEnterprise entity = find(key);
			if (entity != null) {
				em.remove(entity);
				return true;
			}

		} catch (Exception e) {
			LOG.error("DATABASE: Could not delete TradinEnterprise with id: " + key + "     " + e.getMessage());
		}
		return false;
	}

	@Override
	public Collection<TradingEnterprise> all() {
		Collection<TradingEnterprise> query ;
		try {
			 query = em.createQuery("SELECT t FROM TradingEnterprise t", TradingEnterprise.class).getResultList();
		} catch (Exception e) {
			LOG.error("DATABASE: Database error while retrieving all enterprises" + "     " + e.getMessage());
			return null;
		}
		return query;

	}
}
