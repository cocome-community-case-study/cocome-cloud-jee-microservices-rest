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
	private boolean editingEnabled = false;
	private String newName;
	private String newLocation;

	public StoreViewData(long id, EnterpriseViewData enterprise, String location, String name) {
		this.id = id;
		setEnterprise(enterprise);
		this.name = name;
		this.location = location;
		this.setNewLocation(location);
		this.setNewName(name);
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

	public boolean isEditingEnabled() {
		return editingEnabled;
	}

	public void setEditingEnabled(boolean editingEnabled) {
		if (!editingEnabled) {
			newName = name;
			newLocation = location;
		}
		this.editingEnabled = editingEnabled;
	}

	public EnterpriseViewData getEnterprise() {
		return enterprise;
	}

	public void setEnterprise(EnterpriseViewData enterprise) {
		this.enterprise = enterprise;
	}

	public String getNewName() {
		return newName;
	}

	public void setNewName(String newName) {
		this.newName = newName;
	}

	public String getNewLocation() {
		return newLocation;
	}

	public void setNewLocation(String newLocation) {
		this.newLocation = newLocation;
	}

	public void updateStoreInformation() {
		name = newName;
		location = newLocation;
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
