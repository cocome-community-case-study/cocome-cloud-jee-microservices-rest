package org.cocome.storesservice.cashDesk.cashDeskModel;

public interface ICashDeskView {

	public void startSale();
	
	public void addDigitToBarcode(char digit);
	
	public void removeLastDigitFromBarcode();
	
	public void clearBarcode();

	public void submitBarcode();
	
	public void submitBarcode(String id);
	
	public String getDisplayOutput();
	
	public String[] getPrinterOutput();
	
	public void finishSale();
	
	public void setCashPayment();
	
	public boolean enterCashPaymentAmount(double amount);
	
	public void setCardPayment();
	
	public void enterBankinformation(String pin, String accountNumber);
}