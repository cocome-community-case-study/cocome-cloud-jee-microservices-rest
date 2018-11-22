package org.cocome.storesservice.frontend.enterprise;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.cocome.storesservice.domain.TradingEnterprise;
import org.cocome.storesservice.enterpriseQuery.IEnterpriseQuery;
import org.cocome.storesservice.exceptions.CreateException;
import org.cocome.storesservice.exceptions.QueryException;
import org.cocome.storesservice.frontend.viewdata.EnterpriseViewData;
import org.cocome.storesservice.navigation.NavigationElements;

/**
 * This class manages the EJB-Bean Access of the backend
 * ({@link org.cocome.storesservice.enterpriseQuery}) <br>
 * It converts Frontend specific ViewData/requests into Backend-Format and vice
 * versa. <br>
 * Depending on the request, it navigates to a different outcome
 * 
 * @author Niko Benkler
 * @author Robert Heinrich
 *
 */
@Named
@SessionScoped
public class EnterpriseManager implements IEnterpriseManager, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8414021992178376298L;

	/*
	 * We do caching here! See loadEnterprises()
	 */
	private Map<Long, EnterpriseViewData> enterprises;
	boolean listUpToDate = false;

	@EJB
	IEnterpriseQuery enterpriseQuery;

	/**
	 * Create Enterprise with given Name <br>
	 * Routing to show_enterprises.xhtml if successful
	 */
	@Override
	public String createEnterprise(String enterpriseName) {

		try {
			enterpriseQuery.createEnterprise(enterpriseName);
		} catch (CreateException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error creating the new enterprise!", null));

			return NavigationElements.ENTERPRISE_MAIN.getNavigationOutcome();
		}
		
		//Refresh EnterpriseList
		listUpToDate = false;
		

		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Successfully created the new enterprise!", null));

		return NavigationElements.SHOW_ENTERPRISES.getNavigationOutcome();

	}

	/**
	 * Cache Enterprises in Frontend to reduce traffic!
	 */
	private void loadEnterprises() {

		/*
		 * If enterprises List has changed--> return <br>
		 * Else do backend Query
		 */
		if (listUpToDate) {
			return;
		}

		this.enterprises = new HashMap<Long, EnterpriseViewData>();
		Collection<TradingEnterprise> query;
		try {
			query = enterpriseQuery.getAllEnterprises();
		} catch (QueryException e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));

			return;
		}

		for (TradingEnterprise enterprise : query) {
			enterprises.put(enterprise.getId(), EnterpriseViewData.fromTradingEnterprise(enterprise));
		}

		listUpToDate = true;

	}

	/**
	 * Get all Enterprises stored in the Database
	 */
	@Override
	public Collection<EnterpriseViewData> getEnterprises() {

		loadEnterprises();
		if (enterprises.isEmpty()) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "No Enterprises available!", null));

		}

		return enterprises.values();
	}

	/**
	 * Get Enterprise by given Id
	 * 
	 * @throws QueryException
	 */
	@Override
	public EnterpriseViewData getEnterpriseById(long enterpriseId) throws QueryException {

		TradingEnterprise enterprise = enterpriseQuery.getEnterpriseById(enterpriseId);

		return EnterpriseViewData.fromTradingEnterprise(enterprise);

	}

}
