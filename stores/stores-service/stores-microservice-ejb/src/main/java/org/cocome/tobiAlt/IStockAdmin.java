package org.cocome.tobiAlt;

public interface IStockAdmin {
	// rest call to get product item and read out the needed informations
	void createStockItem(long productId, long salePrice, long minStockAmount, long maxStockAmount, long storeId);
	
	// updating this informations
	void updateStockItem(long stockItemId, long salePrice, long minStockAmount, long maxStockAmount, long storeId);
}
