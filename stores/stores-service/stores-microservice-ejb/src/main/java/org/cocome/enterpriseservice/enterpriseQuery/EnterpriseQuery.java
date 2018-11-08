package org.cocome.enterpriseservice.enterpriseQuery;

import java.io.Serializable;
import java.util.Collection;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.cocome.storesservice.domain.TradingEnterprise;
import org.cocome.storesservice.repository.TradingEnterpriseDBRepository;
import org.cocome.storesservice.repository.TradingEnterpriseRepository;
import org.apache.log4j.Logger;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * This class abstracts the Database Access and provides more specific CRUD-Operations. <br>
 * It uses the Entities specified in {@link org.cocome.storesservice.domain}<br>
 * 
 * It handles Queries from the enterprise-frontend
 * 
 * @author Niko Benkler
 * @author Robert Heinrich
 *
 */
@Local
@Stateless
public class EnterpriseQuery implements IEnterpriseQuery, Serializable {

	private static final long serialVersionUID = 1L;
	private Logger LOG = Logger.getLogger(EnterpriseQuery.class);

	@EJB
	private TradingEnterpriseRepository enterpriseRepo;

	/**
	 * Create enterprise with name @param enterpriseName
	 */
	@Override
	public boolean createEnterprise(String enterpriseName) {
		TradingEnterprise entity = new TradingEnterprise();
		entity.setName(enterpriseName);
		LOG.debug("QUERY: createEnterprise with name " + entity.getName() + " and id: " + entity.getId());
		return enterpriseRepo.create(entity) != -1;

	}

	/**
	 * Return all existing enterprises
	 */
	@Override
	public Collection<TradingEnterprise> getAllEnterprises() {
		Collection<TradingEnterprise> enterprises = enterpriseRepo.all();

		for (TradingEnterprise enterprise : enterprises) {
			LOG.debug("QUERY: Retrieving Enterprise from database with name " + enterprise.getName() + " and id: "
					+ enterprise.getId());
		}

		return enterprises;

	}

	@Override
	public TradingEnterprise getEnterpriseById(long enterpriseId) {
		LOG.debug("QUERY: Retrieving Enterprise from Database with Id: " +  enterpriseId);
		TradingEnterprise enterprise = enterpriseRepo.find(enterpriseId);
		if(enterprise !=null) {
			LOG.debug("Successfully found enterprise with Id: " +  enterpriseId);
			return enterprise;
		}
		return null;
	}

}
