package org.cocome.storesservice.frontend.enterprise;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.cocome.enterpriseservice.enterpriseQuery.IEnterpriseQuery;
import org.cocome.storesservice.domain.TradingEnterprise;
import org.cocome.storesservice.frontend.viewdata.EnterpriseViewData;
import org.cocome.storesservice.navigation.NavigationElements;

/**
 * This class manages the EJB-Bean Access of the backend
 * ({@link org.cocome.enterpriseservice.enterpriseQuery}) <br>
 * It converts Frontend specific ViewData/requests into Backend-Format and vice
 * versa. <br>
 * Depending on the request, it navigates to a different outcome
 * 
 * @author Niko Benkler
 * @author Robert Heinrich
 *
 */
@Named
@ApplicationScoped
public class EnterpriseManager implements IEnterpriseManager {

	/*
	 * We might want to use cacheing here!
	 */
	private Map<Long, EnterpriseViewData> enterprises;
	private static final Logger LOG = Logger.getLogger(EnterpriseManager.class);

	private final long COULD_NOT_CREATE_ENTITY = -1;
	
	@EJB
	IEnterpriseQuery enterpriseQuery;

	/**
	 * Create Enterprise with given Name <br>
	 * Routing to show_enterprises.xhtml if successful
	 */
	@Override
	public String createEnterprise(String enterpriseName) {

		if (enterpriseQuery.createEnterprise(enterpriseName) != COULD_NOT_CREATE_ENTITY) {

			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Successfully created the new enterprise!", null));

			return NavigationElements.SHOW_ENTERPRISES.getNavigationOutcome();
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error creating the new enterprise!", null));

			return NavigationElements.ENTERPRISE_MAIN.getNavigationOutcome();
		}

	}

	/**
	 * Get all Enterprises stored in the Database
	 */
	@Override
	public Collection<EnterpriseViewData> getEnterprises() {

		this.enterprises = new HashMap<Long, EnterpriseViewData>();

		for (TradingEnterprise enterprise : enterpriseQuery.getAllEnterprises()) {
			enterprises.put(enterprise.getId(), EnterpriseViewData.fromTradingEnterprise(enterprise));
		}

		return enterprises.values();
	}

	
    /**
     * Get Enterprise by given Id
     */
	@Override
	public EnterpriseViewData getEnterpriseById(long enterpriseId) {
		TradingEnterprise enterprise = enterpriseQuery.getEnterpriseById(enterpriseId);
		if(enterprise == null) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Could not find Enterprise with Id " + enterpriseId, null));
			return null;
		}else {
			return EnterpriseViewData.fromTradingEnterprise(enterprise);
			
		}
		
	}

	

}
