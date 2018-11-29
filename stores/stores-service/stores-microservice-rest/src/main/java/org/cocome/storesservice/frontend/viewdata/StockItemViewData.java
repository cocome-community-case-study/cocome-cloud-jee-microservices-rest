package org.cocome.storesservice.frontend.viewdata;

import java.util.Collection;
import java.util.LinkedList;

import org.cocome.storesservice.domain.StockItem;
/**
 * Represents StockItem domain object in frontend format. Contains some more field for temporally saviong new names.
 * @author Niko Benkler
 * @author Robert Heinrich
 *
 */
public class StockItemViewData {

	// Actuacl fields for stockitem
	private long id;
	private double salesPrice;
	private long amount;
	private long minStock;
	private long maxStock;
	private long barcode;
	private String name;
	private long incomingAmount;
	private long productId;
	private long storeId;

	// field for changing values
	private boolean editingEnabled = false;
	private long newMinAmount;
	private long newMaxAmount;
	private double newSalesPrice;

	public StockItemViewData(long id, double salesPrice, long amount, long minStock, long maxStock, long barcode,
			long incomingAmount, long productId, long storeId, String name) {

		this.id = id;
		this.salesPrice = salesPrice;
		this.amount = amount;
		this.minStock = minStock;
		this.maxStock = maxStock;
		this.barcode = barcode;
		this.incomingAmount = incomingAmount;
		this.productId = productId;
		this.storeId = storeId;
		this.name = name;
		
		//Init new values with old values
		this.newMinAmount = minStock;
		this.newMaxAmount = maxStock;
		this.newSalesPrice = salesPrice;
	}

	/*
	 * ----------------------------------------------------
	 */
	
	/**
	 * Submit the changes that were made during the edit store process
	 */
	public void submitEdit() {
		this.salesPrice = newSalesPrice;
		this.minStock = newMinAmount;
		this.maxStock = newMaxAmount;
		
		//amount should not exceed max stock
		this.amount = Math.min(maxStock, amount);
		
		this.editingEnabled = false;
		
		//TODO what happen if currentamount < minStock?
	}

	public void setEditingEnabled(boolean editingEnabled) {
		this.editingEnabled = editingEnabled;
	}


	
	public boolean isEditingEnabled() {
		return editingEnabled;
	}
	/*
	 * ------------------------------------------------
	 */

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public double getSalesPrice() {
		return salesPrice;
	}

	public void setSalesPrice(double salesPrice) {
		this.salesPrice = salesPrice;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	public long getMinStock() {
		return minStock;
	}

	public void setMinStock(long minStock) {
		this.minStock = minStock;
	}

	public long getMaxStock() {
		return maxStock;
	}

	public void setMaxStock(long maxStock) {
		this.maxStock = maxStock;
	}

	public long getBarcode() {
		return barcode;
	}

	public void setBarcode(long barcode) {
		this.barcode = barcode;
	}

	public long getIncomingAmount() {
		return incomingAmount;
	}

	public void setIncomingAmount(long incomingAmount) {
		this.incomingAmount = incomingAmount;
	}

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public long getStoreId() {
		return storeId;
	}

	public void setStoreId(long storeId) {
		this.storeId = storeId;
	}

	/*
	 * ------------------------------------------------
	 */

	public void setNewMinAmount(long newMinAmount) {
		this.newMinAmount = newMinAmount;
	}

	public void setNewMaxAmount(long newMaxAmount) {
		this.newMaxAmount = newMaxAmount;
	}

	public void setNewSalesPrice(double newSalesPrice) {
		this.newSalesPrice = newSalesPrice;
	}
	

	public long getNewMinAmount() {
		return newMinAmount;
	}

	public long getNewMaxAmount() {
		return newMaxAmount;
	}

	public double getNewSalesPrice() {
		return newSalesPrice;
	}
	
	
	public static StockItemViewData fromStockItem(StockItem item) {
		return new StockItemViewData(item.getId(), item.getSalesPrice(), item.getAmount(), item.getMinStock(),
				item.getMaxStock(), item.getBarcode(), item.getIncomingAmount(), item.getProductId(),
				item.getStore().getId(), item.getName());
	}

	public static Collection<StockItemViewData> fromStockItemCollection(Collection<StockItem> items) {

		Collection<StockItemViewData> collection = new LinkedList<StockItemViewData>();

		for (StockItem item : items) {
			collection.add(fromStockItem(item));
		}

		return collection;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StockItemViewData other = (StockItemViewData) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	

}
