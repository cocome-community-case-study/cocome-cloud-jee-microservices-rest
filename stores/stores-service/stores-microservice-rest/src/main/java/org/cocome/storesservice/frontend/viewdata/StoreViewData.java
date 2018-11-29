package org.cocome.storesservice.frontend.viewdata;

import java.util.Collection;
import java.util.LinkedList;

import org.cocome.storesservice.domain.Store;

/**
 * Frontend specific Store domain object
 * @author Niko Benkler
 * @author Robert Heinrich
 *
 */
public class StoreViewData {
	private long id;
	private long enterpriseId;
	private String name;
	private String location;
	
	
	

	public StoreViewData(long id, long enterpriseId, String location, String name) {
		this.id = id;
		this.enterpriseId = enterpriseId;
		this.name = name;
		this.location = location;
		
	}

	public long getId() {
		return id;
	}

	public void setId(long storeID) {
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

	

	public long getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterprise(long enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	

	public static StoreViewData fromStore(Store store) {
		return new StoreViewData(store.getId(), store.getEnterprise().getId(),
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
