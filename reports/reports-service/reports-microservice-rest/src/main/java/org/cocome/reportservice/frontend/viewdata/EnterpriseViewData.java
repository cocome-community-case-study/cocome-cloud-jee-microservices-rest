package org.cocome.reportservice.frontend.viewdata;

import java.util.Collection;
import java.util.LinkedList;

import org.cocome.storesclient.domain.TradingEnterpriseTO;

/**
 * Represents a Enterprise Entity
 * {@link org.cocome.storesservice.domain.TradingEnterprise} for the Enterprise
 * Frontend <br>
 * EnterpriseViewData has a Collection of each store that belongs to this
 * Enterprise
 * 
 * @author Niko Benkler
 * @author Robert Heinrich
 *
 */

public class EnterpriseViewData {

	private long id;
	private String name;

	public EnterpriseViewData() {
		id = 0;
		name = "N/A";
	}

	public EnterpriseViewData(long id, String name) {
		this.id = id;
		this.name = name;

	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static EnterpriseViewData fromEnterpriseTO(TradingEnterpriseTO enterprise) {
		return new EnterpriseViewData(enterprise.getId(), enterprise.getName());
	}

	public static Collection<EnterpriseViewData> fromEnterpriseTOCollectio(
			Collection<TradingEnterpriseTO> enterprises) {

		Collection<EnterpriseViewData> enterprisesViewData = new LinkedList<EnterpriseViewData>();

		for (TradingEnterpriseTO enterprise : enterprises) {
			enterprisesViewData.add(fromEnterpriseTO(enterprise));
		}

		return enterprisesViewData;

	}

}
