package org.cocome.storesserviceservice.StoreQuery;

import java.util.Collection;

import javax.validation.constraints.NotNull;

import org.cocome.storesservice.domain.StockItem;

public interface IStockQuery {



	long createStockItem(@NotNull double salesPrice, @NotNull long amount, @NotNull long minStock,
			@NotNull long maxStock, @NotNull long barcode, @NotNull long incomingAmount, @NotNull long productId,
			@NotNull long storeId);

	Collection<StockItem> getAllStockItems();

	StockItem getStockItemById(@NotNull long stockItemId);

	Collection<StockItem> getStockItemsByStore(@NotNull long storeId);

	

	/**
	 * Update StockItem does not require store and product-id reference. These
	 * should not modifyable
	 * 
	 * @param id
	 * @param salesPrice
	 * @param amount
	 * @param minStock
	 * @param maxStock
	 * @param barcode
	 * @param incomingAmount
	 * @return
	 */
	boolean updateStockeItem(@NotNull long id, @NotNull double salesPrice, @NotNull long amount, @NotNull long minStock,
			@NotNull long maxStock, @NotNull long barcode, @NotNull long incomingAmount);

	boolean deleteStockItem(@NotNull long stockItemId);
}
