package org.cocome.storesservice.frontend.enterprise;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.cocome.enterpriseservice.enterpriseManager.EnterpriseQuery;
import org.cocome.enterpriseservice.enterpriseManager.IEnterpriseQuery;
import org.cocome.storesservice.domain.TradingEnterprise;

@Named
@ApplicationScoped
public class EnterpriseManager implements IEnterpriseManager {

	/*
	 * We might want to use cacheing here!
	 */
	private Map<Long, EnterpriseViewData> enterprises;
	private static final Logger LOG = Logger.getLogger(EnterpriseManager.class);

	
	
	@EJB
	IEnterpriseQuery enterpriseQuery;
	
	@Override
	public String createEnterprise(String enterpriseName) {
		LOG.debug("Trying to enterprise with name " + enterpriseName);
		LOG.debug("Query ist: " + enterpriseQuery);
		 if(enterpriseQuery.createEnterprise(enterpriseName)){
		
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Successfully created the new enterprise!", null));
			LOG.debug("Sucessfully created Enterprise with name: " + enterpriseName);
			return "show_enterprises";
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error creating the new enterprise!", null));
			LOG.debug("Could not created Enterprise with name: " + enterpriseName);
			return "enterpriseMain";
		}

	}

	@Override
	public Collection<EnterpriseViewData> getEnterprises() {

		this.enterprises = new HashMap<Long, EnterpriseViewData>();

		for (TradingEnterprise enterprise : enterpriseQuery.getAllEnterprises()) {
			enterprises.put(enterprise.getId(), EnterpriseViewData.fromTradingEnterprise(enterprise));
		}

		return enterprises.values();
	}

	public String printString() {

		return "printString";
	}

}
