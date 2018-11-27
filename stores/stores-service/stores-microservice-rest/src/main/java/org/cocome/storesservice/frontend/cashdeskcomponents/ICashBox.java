package org.cocome.storesservice.frontend.cashdeskcomponents;


public interface ICashBox {
	
	
	void resetBarcode();
	void removeLastDigit();
	void submitBarcode();
	void addToDigit(char nextDigit);
	void pressButtonFinishSale();
	void selectCashPayment();
	void selectCardPayment();
	String getBarcode();
	void setBarcode(String barcode);
	void startSale();
	double getRunningTotal();
	void setRunningTotal(double runningTotal);
	void addItemToSale(long barcode, long storeId);
}
