package org.cocome.storesserviceservice.StoreQuery;

import java.util.Collection;

import javax.validation.constraints.NotNull;

import org.cocome.storesservice.domain.StockItem;
import org.cocome.storesservice.exceptions.CreateException;
import org.cocome.storesservice.exceptions.QueryException;

public interface IStockQuery {



	long createStockItem(@NotNull double salesPrice, @NotNull long amount, @NotNull long minStock,
			@NotNull long maxStock, @NotNull long barcode, @NotNull long incomingAmount, @NotNull long productId,
			@NotNull long storeId, @NotNull String name) throws CreateException;

	Collection<StockItem> getAllStockItems();

	StockItem getStockItemById(@NotNull long stockItemId) throws QueryException;

	Collection<StockItem> getStockItemsByStore(@NotNull long storeId) throws QueryException;

	

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
	 * @throws QueryException 
	 */
	void updateStockeItem(@NotNull long id, @NotNull double salesPrice, @NotNull long amount, @NotNull long minStock,
			@NotNull long maxStock, @NotNull long barcode, @NotNull long incomingAmount, @NotNull String name) throws QueryException;

	void deleteStockItem(@NotNull long stockItemId) throws QueryException;
}
