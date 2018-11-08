package org.cocome.storesservice.frontend.viewdata;

import java.util.Collection;

import org.cocome.storesservice.domain.TradingEnterprise;

/**
 * Represents a Enterprise Entity
 * {@link org.cocome.storesservice.domain.TradingEnterprise} for the Enterprise
 * Frontend <br>
 * EnterpriseViewData has a Collection of each store that belongs to this Enterprise
 * 
 * @author Niko Benkler
 * @author Robert Heinrich
 *
 */

public class EnterpriseViewData {

	private long id;
	private String name;
	private Collection<StoreViewData> stores;

	public EnterpriseViewData() {
		id = 0;
		name = "N/A";
	}

	public EnterpriseViewData(long id, String name, Collection<StoreViewData> stores) {
		this.id = id;
		this.name = name;
		this.setStores(stores);
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

	public static EnterpriseViewData fromTradingEnterprise(TradingEnterprise enterprise) {
		return new EnterpriseViewData(enterprise.getId(), enterprise.getName(),
				StoreViewData.fromStoreCollection(enterprise.getStores()));
	}

	public Collection<StoreViewData> getStores() {
		return stores;
	}

	public void setStores(Collection<StoreViewData> stores) {
		this.stores = stores;
	}

}
