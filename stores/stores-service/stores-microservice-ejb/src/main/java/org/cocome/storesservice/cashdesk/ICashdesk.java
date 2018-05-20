package org.cocome.storesservice.cashdesk;


import org.cocome.storesservice.domain.StockItem;

public interface ICashdesk {
	
	//DisplayMode newSale
	void startNewSale();
	
	//display false item
	StockItem addStockItem();
	
	String getPrinter();
	
	long getTotalAmout();
	
	// money back
	void cashPaiment(long amount);
	
	String getDisplay();
	
	boolean cardPaiment(Long cardNumber, int PIN);
	
	//TODO logging completed sales
	//write completed imformation to printer
	//succeded sales safe sale!?! "logged"
}
