package org.cocome.storesservice.storeManager;

public interface IStoreAdminManagement extends ICashDeskWorker{
	void changePrice(long productId, double Price);
}
