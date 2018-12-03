package org.cocome.storesservice.frontend.stock;

import java.io.Serializable;
import java.util.Collection;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.cocome.storesservice.domain.StockItem;
import org.cocome.storesservice.exceptions.QueryException;
import org.cocome.storesservice.frontend.viewdata.StockItemViewData;
import org.cocome.storesserviceservice.StoreQuery.IStockQuery;
/**
 * Interface that connects backend functionality and frontend regarding Stock
 * @author Niko Benkler
 * @author Robert Heinrich
 *
 */
@Named
@SessionScoped
public class StockManager implements IStockManager, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1762737113407031682L;

	@EJB
	IStockQuery stockQuery;

	/**
	 * Return all Items for a certain Store
	 */
	@Override
	public Collection<StockItemViewData> getStockItemsByStore(long storeId) throws QueryException {

		Collection<StockItem> items = stockQuery.getStockItemsByStore(storeId);

		return StockItemViewData.fromStockItemCollection(items);
	}

	/**
	 * Update certain stock item
	 */
	@Override
	public void updateStockItem(StockItemViewData item) throws QueryException {

		stockQuery.updateStockeItem(item.getId(), item.getSalesPrice(), item.getAmount(), item.getMinStock(),
				item.getMaxStock(), item.getBarcode(), item.getIncomingAmount(), item.getName());

	}

	/**
	 * Return stock item with certain (unique!) barcode from store with given id.
	 * <br> Store is only given to fasten the query.
	 */
	@Override
	public StockItemViewData getStockItemByBarcodeAndStoreId(long barcode, long storeId) throws QueryException {

		return StockItemViewData.fromStockItem(stockQuery.getStockItemByBarcodeAndStore(barcode, storeId));
	}
	
	/**
	 * Do stock Exchange according to UC8
	 * @throws QueryException 
	 */
	 @Override
	 public void doStockExchange(long stockItemId) throws QueryException {
		 stockQuery.stockExchange(stockItemId);
	 }

}
