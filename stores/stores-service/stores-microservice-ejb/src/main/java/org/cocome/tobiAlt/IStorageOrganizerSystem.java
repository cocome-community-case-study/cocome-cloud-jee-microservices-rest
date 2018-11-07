package org.cocome.tobiAlt;

import org.cocome.storesservice.domain.StockItem;

public interface IStorageOrganizerSystem {
	public boolean containStockItem(long id);
	public StockItem getItem(long id);
	public boolean reduceInventory(long id, int amount);
	public void incrementInventory(long id, int amount);
	public long getIDByBarcode(long Barcode);
}
