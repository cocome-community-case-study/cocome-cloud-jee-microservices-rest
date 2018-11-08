package org.cocome.storesservice.frontend.viewdata;

import java.util.Collection;
import java.util.LinkedList;

import org.cocome.storesservice.domain.Store;

/**
 * Holds informtaion about a Store.
 * 
 * @author Tobias Pöppke
 * @author Robert Heinrich
 */
public class StoreViewData {
	private long id;
	private EnterpriseViewData enterprise;
	private String name;
	private String location;
	

	public StoreViewData(long id, EnterpriseViewData enterprise, String location, String name) {
		this.id = id;
		this.enterprise = enterprise;
		this.name = name;
		this.location = location;
		
	}

	public long getID() {
		return id;
	}

	public void setID(long storeID) {
		this.id = storeID;
	}

	public String getName() {
		return name;
	}

	public void setName(String storeName) {
		this.name = storeName;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String storeLocation) {
		this.location = storeLocation;
	}

	

	public EnterpriseViewData getEnterprise() {
		return enterprise;
	}

	public void setEnterprise(EnterpriseViewData enterprise) {
		this.enterprise = enterprise;
	}

	

	public static StoreViewData fromStore(Store store) {
		return new StoreViewData(store.getId(), EnterpriseViewData.fromTradingEnterprise(store.getEnterprise()),
				store.getLocation(), store.getName());
	}

	public static Collection<StoreViewData> fromStoreCollection(Collection<Store> stores) {
		Collection<StoreViewData> collection = new LinkedList<StoreViewData>();
		
		for (Store store :stores) {
			collection.add(StoreViewData.fromStore(store));
		}
		return collection;
	}

}
