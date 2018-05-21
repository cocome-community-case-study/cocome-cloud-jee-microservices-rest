package org.cocome.storesservice.cashDesk;

public interface ICashDeskFunctionality {

	public void startSale(long enterpriseId, long storeId, String cashDeskName);
	
	public void addDigittoBarcode(long enterpriseId, long storeId, String cashDeskName, char digit);
	
	public void removeLastDigitFromBarcode(long enterpriseId, long storeId, String cashDeskName);
	
	public void clearBarcode(long enterpriseId, long storeId, String cashDeskName);
	
	public void submitBarcode(long enterpriseId, long storeId, String cashDeskName);
	
	public String getDisplayOutput(long enterpriseId, long storeId, String cashDeskName);
	
	public String[] getPrinterOutput(long enterpriseId, long storeId, String cashDeskName);
	
	public void finishSale(long enterpriseId, long storeId, String cashDeskName);
	
	public void setCashPayment(long enterpriseId, long storeId, String cashDeskName);
	
	public void enterCashPaymentAmount(long enterpriseId, long storeId, String cashDeskName, double amount);
	
	public void setCardPayment(long enterpriseId, long storeId, String cashDeskName);
	
	public void enterPin(long enterpriseId, long storeId, String cashDeskName, String pin);
}
