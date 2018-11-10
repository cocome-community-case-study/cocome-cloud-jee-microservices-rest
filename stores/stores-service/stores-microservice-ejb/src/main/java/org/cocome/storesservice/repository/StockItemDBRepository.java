package org.cocome.storesservice.repository;

import java.util.Collection;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.cocome.storesservice.domain.StockItem;

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
public class StockItemDBRepository implements StockItemRepository {
	@PersistenceContext(unitName = "InventoryManager")
	private EntityManager em;

	private static final Logger LOG = Logger.getLogger(StockItemDBRepository.class);

	@Override
	public Long create(StockItem entity) {
		try {
			em.persist(entity);
			return entity.getId();
		} catch (Exception e) {
			LOG.error("DATABASE: Could not create StickItem with productId: " + entity.getProductId() + " and Barcode: "
					+ entity.getBarcode());
			return null;
		}

	}

	@Override
	public StockItem find(Long id) {
		try {
			return em.find(StockItem.class, id);
		} catch (Exception e) {
			LOG.error("DATABASE: Could not find StockItem with id: " + id + "     " + e.getMessage());
			return null;
		}

	}

	@Override
	public StockItem update(StockItem entity) {

		try {
			return em.merge(entity);
		} catch (Exception e) {
			LOG.error("DATABASE: Could not update StockItem with ProductId: " + entity.getProductId() + " and Barcode: "
					+ entity.getBarcode() + "     " + e.getMessage());
			return null;
		}

	}

	@Override
	public void delete(Long key) {
		try {
			StockItem entity = find(key);
			em.remove(entity);
		} catch (Exception e) {
			LOG.error("DATABASE: Could not delete StockItem with StockItemId: " + key + "     " + e.getMessage());
		}

	}

	@Override
	public Collection<StockItem> all() {
		try {
			return em.createQuery("SELECT s FROM StockItem s", StockItem.class).getResultList();
		} catch (Exception e) {
			LOG.error("DATABASE: Could not retrieve all StockItems" + "     " + e.getMessage());
			return null;
		}

	}
}
