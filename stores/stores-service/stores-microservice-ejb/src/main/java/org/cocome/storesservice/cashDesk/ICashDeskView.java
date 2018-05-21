package org.cocome.storesservice.cashDesk;

public interface ICashDeskView {

	public void startSale(String cashDeskName);
	
	public void addDigittoBarcode(char digit);
	
	public void removeLastDigitFromBarcode();
	
	public void clearBarcode();
	
	public void submitBarcode();
	
	public String getDisplayOutput();
	
	public String[] getPrinterOutput();
	
	public void finishSale();
	
	public void setCashPayment();
	
	public void enterCashPaymentAmount(double amount);
	
	public void setCardPayment();
	
	public void enterPin(String pin);
}
