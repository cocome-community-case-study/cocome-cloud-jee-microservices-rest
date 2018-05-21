package org.cocome.storesservice.storeManager;

public interface IStoreAdminManagement extends ICashDeskFinder{
	void changePrice(long productId, double Price);
}
