package org.cocome.storesservice.enterpriseQuery;

import java.io.Serializable;
import java.util.Collection;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.validation.constraints.NotNull;

import org.apache.log4j.Logger;
import org.cocome.storesservice.domain.Store;
import org.cocome.storesservice.domain.TradingEnterprise;
import org.cocome.storesservice.exceptions.CreateException;
import org.cocome.storesservice.exceptions.QueryException;
import org.cocome.storesservice.repository.TradingEnterpriseRepository;

/**
 * This class abstracts the Database Access and provides more specific
 * CRUD-Operations. <br>
 * It uses the Entities specified in {@link org.cocome.storesservice.domain}<br>
 * 
 * It handles Queries from the enterprise-frontend.
 * 
 * @author Niko Benkler
 * @author Robert Heinrich
 *
 */
@Local
@Stateless
public class EnterpriseQuery implements IEnterpriseQuery, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6903140662512027702L;

	private Logger LOG = Logger.getLogger(EnterpriseQuery.class);

	@EJB
	private TradingEnterpriseRepository enterpriseRepo;

	/**
	 * Create enterprise with name @param enterpriseName
	 * 
	 * @throws CreateException
	 */
	@Override
	public long createEnterprise(@NotNull String enterpriseName) throws CreateException {
		TradingEnterprise entity = new TradingEnterprise();
		entity.setName(enterpriseName);
		LOG.debug("QUERY: Try to create Enterprise with name " + entity.getName());
		Long enterpriseId = enterpriseRepo.create(entity);

		if (enterpriseId == null) {
			LOG.error(
					"QUERY: Could not create enterprise with name " + entity.getName() + " and id: " + entity.getId());

			throw new CreateException(
					"QUERY: Could not create enterprise with name " + entity.getName() + " and id: " + entity.getId());
		}

		LOG.debug("QUERY: sucessfully create enterprise with name " + entity.getName() + " and id: " + entity.getId());
		return enterpriseId;

	}

	/**
	 * Return all existing enterprises
	 */
	@Override
	public Collection<TradingEnterprise> getAllEnterprises() {
		Collection<TradingEnterprise> enterprises = enterpriseRepo.all();

		StringBuilder sb = new StringBuilder();
		sb.append("QUERY: Retrieving ALL Enterprises from database with following [name, Id]: ");

		for (TradingEnterprise enterprise : enterprises) {
			sb.append("[ " + enterprise.getName() + " ," + enterprise.getId() + " ]");
		}
		LOG.debug(sb.toString());

		return enterprises;

	}

	/**
	 * Return Enterprise by enterpriseId
	 * 
	 * @throws QueryException
	 */
	@Override
	public TradingEnterprise getEnterpriseById(@NotNull long enterpriseId) throws QueryException {
		LOG.debug("QUERY: Retrieving Enterprise from Database with Id: " + enterpriseId);

		// find enterprise
		TradingEnterprise enterprise = enterpriseRepo.find(enterpriseId);

		if (enterprise == null) {
			LOG.debug("QUERY: Did not find enterprise with Id: " + enterpriseId);
			throw new QueryException("Did not find enterprise with Id: " + enterpriseId);
		}

		// Logging
		LOG.debug("QUERY: Successfully found enterprise with Id: " + enterpriseId);
		LOG.debug("With stores:");
		for (Store store : enterprise.getStores()) {
			LOG.debug("Has store: " + store.getId());
		}

		return enterprise;

	}

	/**
	 * Delete Enterprise by id
	 * 
	 * @throws QueryException
	 */
	@Override
	public void deleteEnterprise(long enterpriseId) throws QueryException {
		LOG.debug("QUERY: Try to delete enterprise with id: " + enterpriseId);

		if (enterpriseRepo.delete(enterpriseId)) {
			LOG.debug("QUERY: Successfully deleted Enterprise with id: " + enterpriseId);
			return;
		}
		LOG.error("QUERY: Could not delete Enterprise with id: " + enterpriseId);
		throw new QueryException("Could not delete Enterprise with id: " + enterpriseId);

	}

	@Override
	public void updateEnterprise(long id, String name) throws QueryException {
		LOG.debug("QUERY: Trying to update Enterprise with id: " + id);

		// find corresponding enterprise
		TradingEnterprise enterprise = enterpriseRepo.find(id);

		if (enterprise == null) {
			LOG.error("QUERY: Could not update Enterprise with name: " + name + " and Id: " + id
					+ ". Enterprise not found");
			throw new QueryException("QUERY: Could not update Enterprise with name: " + name + " and Id: " + id
					+ ". Enterprise not found");

		}

		// update values
		enterprise.setName(name);
		if (enterpriseRepo.update(enterprise) == null) {
			LOG.debug("QUERY:Could notupdate Enterprise with name: " + enterprise.getName() + " and Id: "
					+ enterprise.getId());
			throw new QueryException("Could not update Enterprise with name: " + enterprise.getName() + " and Id: "
					+ enterprise.getId());

		}

		LOG.debug("QUERY: Sucessfully updated Enterprise with name: " + enterprise.getName() + " and Id: "
				+ enterprise.getId());

	}

}
