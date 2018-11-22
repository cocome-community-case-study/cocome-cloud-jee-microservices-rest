package org.cocome.storesservice.frontend.stock;

import java.io.Serializable;
import java.util.Collection;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

import org.cocome.storesservice.domain.StockItem;
import org.cocome.storesservice.exceptions.QueryException;
import org.cocome.storesservice.frontend.viewdata.StockItemViewData;
import org.cocome.storesserviceservice.StoreQuery.IStockQuery;

@Named
@ApplicationScoped
public class StockManager implements IStockManager, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1762737113407031682L;

	@EJB
	IStockQuery stockQuery;

	@Override
	public Collection<StockItemViewData> getStockItemsByStore(long storeId) throws QueryException {

		Collection<StockItem> items = stockQuery.getStockItemsByStore(storeId);

		return StockItemViewData.fromStockItemCollection(items);
	}

	@Override
	public void updateStockItem(StockItemViewData item) throws QueryException {

		stockQuery.updateStockeItem(item.getId(), item.getSalesPrice(), item.getAmount(), item.getMinStock(),
				item.getMaxStock(), item.getBarcode(), item.getIncomingAmount(), item.getName());

		
	}

}
