package org.cocome.enterpriseservice.enterpriseQuery;

import java.io.Serializable;
import java.util.Collection;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import org.cocome.storesservice.domain.Store;
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
	private final long COULD_NOT_CREATE_ENTITY = -1;

	private Logger LOG = Logger.getLogger(EnterpriseQuery.class);

	@EJB
	private TradingEnterpriseRepository enterpriseRepo;

	/**
	 * Create enterprise with name @param enterpriseName
	 */
	@Override
	public boolean createEnterprise(@NotNull String enterpriseName) {
		TradingEnterprise entity = new TradingEnterprise();
		entity.setName(enterpriseName);
		LOG.debug("QUERY: Try to create Enterprise with name " + entity.getName() + " and id: " + entity.getId());
		if( enterpriseRepo.create(entity) != COULD_NOT_CREATE_ENTITY) {
			LOG.debug("QUERY: sucessfully create enterprise with name " + entity.getName() + " and id: " + entity.getId());
			return true;
		}else {
			LOG.error("QUERY: Could not create enterprise with name " + entity.getName() + " and id: " + entity.getId());
			return false;
		}

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
			sb.append("[ " + enterprise.getName() + " ," + enterprise.getId()  +" ]");
		}
		LOG.debug(sb.toString());
		return enterprises;

	}

	@Override
	public TradingEnterprise getEnterpriseById(@NotNull long enterpriseId) {
		LOG.debug("QUERY: Retrieving Enterprise from Database with Id: " +  enterpriseId);
		TradingEnterprise enterprise = enterpriseRepo.find(enterpriseId);
		if(enterprise !=null) {
			LOG.debug("QUERY: Successfully found enterprise with Id: " +  enterpriseId);
			LOG.debug("With stores:");
			for(Store store :enterprise.getStores()) {
				LOG.debug("Has store: " +  store.getId());
			}
			return enterprise;
		}
		LOG.debug("QUERY: Did not find enterprise with Id: " +  enterpriseId);
		return null;
	}

}
