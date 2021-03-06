package org.cocome.productsservice.repository;

import java.util.Collection;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.cocome.productsservice.domain.Product;

/**
 * Basic CRUD-Repository for {@link Product} Entity <br>
 * This class does not do any specific error Handling. Only Database errors are handled
 * @author Niko Benkler
 * @author Robert Heinrich
 *
 */
@Local
@Stateless
public class ProductDBRepository implements ProductRepository {
	@PersistenceContext(unitName = "InventoryManager")
	private EntityManager em;
	
	private static final Logger LOG = Logger.getLogger(ProductDBRepository.class);
	private final long COULD_NOT_CREATE_ENTITY = -1;

	@Override
	public Long create(Product entity) {
		try {
			em.persist(entity);
			em.flush();
		} catch (Exception e) {
			LOG.error("DATABASE: Database Error while creating Product with name: " + entity.getName() + "     "
					+ e.getMessage());
			return COULD_NOT_CREATE_ENTITY;
		}
		return entity.getId();
	}

	@Override
	public Product find(Long id) {
		Product product;
		try {
			product = em.find(Product.class, id);
		} catch (Exception e) {
			LOG.error("DATABASE: Database Error while finding Product with Id: " + id + "     " + e.getMessage());
			return null;
		}

		return product;
	}

	@Override
	public Product update(Product entity) {
		try {
			Product product = em.merge(entity);
			return product;
		} catch (Exception e) {
			LOG.error("DATABASE: Database error while updating Product with Id " + entity.getId() + "     "
					+ e.getMessage());
			return null;
		}
	}

	@Override
	public boolean delete(Long key) {
		try {
			Product entity = find(key);
			if(entity!=null) {
				em.remove(entity);
				return true;
			}
			
		} catch (Exception e) {
			LOG.error("DATABASE: Could not delete Product with id: " + key + "     " + e.getMessage());
		}
		return false;
	}

	@Override
	public Collection<Product> all() {
		try {
			return em.createQuery("SELECT p FROM Product p", Product.class).getResultList();
		} catch (Exception e) {
			LOG.error("DATABASE: Database error while retrieving all Products" + "     " + e.getMessage());
			return null;
		}
		
	}
	
	
}
