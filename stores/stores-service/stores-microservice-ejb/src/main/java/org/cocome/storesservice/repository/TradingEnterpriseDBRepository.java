package org.cocome.storesservice.repository;

import java.util.Collection;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
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

@Stateless
public class TradingEnterpriseDBRepository implements TradingEnterpriseRepository {

	@PersistenceContext(unitName = "InventoryManager")
	private EntityManager em;

	private final long COULD_NOT_CREATE_ENTITY = -1;

	private static final Logger LOG = Logger.getLogger(TradingEnterprise.class);

	public TradingEnterpriseDBRepository() {
	}
    
	/**
	 * Creation of entity TradingEnterprise <br>
	 * @return COULD_NOT_CREATE_ENTITY or Id of Tradingenterprise
	 */
	@Override
	public Long create(TradingEnterprise entity) {
		try {
			em.persist(entity);
		} catch (Exception e) {
			LOG.error("DATABASE: Database Error while creating Enterprise with name: " + entity.getName()
					+ e.getMessage());
			return COULD_NOT_CREATE_ENTITY;
		}
		return entity.getId();
	}
    
	/**
	 * Find enterprise by id
	 * @return null if no enterprise found; TradingEnterprise else
	 */
	@Override
	public TradingEnterprise find(Long id) {
		TradingEnterprise enterprise;
		try {
			enterprise = em.find(TradingEnterprise.class, id);
		} catch (Exception e) {
			LOG.error("DATABASE: Database Error while finding Enterprise with Id: " + id);
			return null;
		}

		return enterprise;
	}

	@Override
	public TradingEnterprise update(TradingEnterprise entity) {
		return em.merge(entity);
	}

	@Override
	public void delete(Long key) {
		TradingEnterprise entity = find(key);
		em.remove(entity);
	}

	@Override
	public Collection<TradingEnterprise> all() {
		return em.createQuery("SELECT t FROM TradingEnterprise t", TradingEnterprise.class).getResultList();
	}
}
