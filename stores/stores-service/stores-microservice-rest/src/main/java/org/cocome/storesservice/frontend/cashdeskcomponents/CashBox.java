package org.cocome.storesservice.frontend.cashdeskcomponents;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.cocome.storesservice.events.InvalidProductBarcodeEvent;
import org.cocome.storesservice.events.ProductOutOfStockEvent;
import org.cocome.storesservice.events.RunningTotalChangedEvent;

import org.cocome.storesservice.exceptions.QueryException;
import org.cocome.storesservice.frontend.stock.IStockManager;
import org.cocome.storesservice.frontend.viewdata.StockItemViewData;
import org.cocome.storesservice.events.InvalidProductBarcodeEvent;

/**
 * Class that represents functionality of the CashBox
 * @author Niko Benkler
 * @author Robert Heinrich
 *
 */
@Named
@SessionScoped
public class CashBox implements ICashBox, Serializable{
    
	@Inject
	IStockManager stockManager;
	
	@Inject 
	Event<InvalidProductBarcodeEvent> invalidProductBarcodeEvent;
	
	@Inject 
	Event<RunningTotalChangedEvent> runningTotalChangedEvents;
	
	@Inject 
	Event<ProductOutOfStockEvent> productOutOfStockEvent;
	
	private static final long serialVersionUID = 3992593730770915195L;
	private String barcode;
	private double runningTotal;
	private List<StockItemViewData> saleProducts = new ArrayList<>();
	
	

	private static final Logger LOG = Logger.getLogger(CashBox.class);
	
	@PostConstruct
	private void init() {
		this.barcode = "";
		this.setRunningTotal(0.0);
	}
	
	@Override
	public void addToDigit(char nextDigit) {
		if(Character.isDigit(nextDigit)) {
			barcode += nextDigit;
		}
	}

	@Override
	public void submitBarcode() {
	
	}
	
	@Override
	public String getBarcode() {
		return this.barcode;
	}
	
	
	
	
	@Override
	public void setBarcode(String barcode) {
		this.barcode = barcode;
		
	}

	@Override
	public void resetBarcode() {
		barcode ="";
	}

	@Override
	public void removeLastDigit() {
		barcode = barcode.substring(0, barcode.length() - 1);
		
	}

	@Override
	public void pressButtonFinishSale() {
				
	}

	@Override
	public void selectCashPayment() {
		
	}

	@Override
	public void selectCardPayment() {
		
	}

	@Override
	public void startSale() {
		resetBarcode();
		this.runningTotal = 0.0;
		
	}
    @Override
	public double getRunningTotal() {
		return runningTotal;
	}
    
	@Override
	public void setRunningTotal(double runningTotal) {
		this.runningTotal = runningTotal;
	}
	

	@Override
	public void addItemToSale(long barcode, long storeId) {
		//TODO already enough Items => ExpressCheckout policy
		
		StockItemViewData item ;
		
		try {
			 item = stockManager.getStockItemByBarcodeAndStoreId(barcode, storeId);
		} catch (QueryException e) {
			LOG.debug("FRONTEND: Could not add item with barcode " + barcode + " to sale");
			invalidProductBarcodeEvent.fire(new InvalidProductBarcodeEvent(barcode));
			return;
		}
		
		
		if(addItemToSale(item)) {
			final double price = item.getSalesPrice();
			this.runningTotal = this.computeNewRunningTotal(price);
			this.sendRunningTotalChangedEvent(item.getName(), price);
		}//else is covered by productoutofStockEvent
		


	}
	
	private boolean addItemToSale(StockItemViewData stockItem) {
		
		// Check if StockItem Exists and reduce amount
		int index = saleProducts.indexOf(stockItem);
		
		//Item already exists
		if(index != -1) {
			StockItemViewData itemInSale = saleProducts.get(index);
			long currentAmount = itemInSale.getAmount();
			if(currentAmount > 0) {
				itemInSale.setAmount(currentAmount-1);
			} else {
				productOutOfStockEvent.fire(new ProductOutOfStockEvent());
				return false;
			}
	
		//Item does not exist --> Add and reduce amount	
		}else { 
			long currentAmount = stockItem.getAmount();
			if(currentAmount < 1) {
				productOutOfStockEvent.fire(new ProductOutOfStockEvent());
				return false;
			}else {
				//reduce amount
				stockItem.setAmount(currentAmount-1);
				saleProducts.add(stockItem);
			}
			
		}
		
		return true;
	}
	
	private double computeNewRunningTotal(final double price) {
		final double result = this.runningTotal + price;
		return Math.rint(100 * result) / 100;
	}
	
	private void sendRunningTotalChangedEvent(String productName, 
			double salePrice) {
		runningTotalChangedEvents.fire(new RunningTotalChangedEvent(
				productName, salePrice, this.runningTotal));
	}

}
