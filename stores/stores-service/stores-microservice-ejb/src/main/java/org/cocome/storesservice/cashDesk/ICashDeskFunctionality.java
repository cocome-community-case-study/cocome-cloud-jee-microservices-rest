package org.cocome.storesservice.cashDesk;

import org.cocome.storesservice.cashDesk.cashDeskModel.cashDeskSetup.expressLight.ExpressLightStates;

public interface ICashDeskFunctionality {

	public void startSale(long enterpriseId, long storeId, String cashDeskName);
	
	public void addDigitToBarcode(long enterpriseId, long storeId, String cashDeskName, char digit);
	
	public void removeLastDigitFromBarcode(long enterpriseId, long storeId, String cashDeskName);
	
	public void clearBarcode(long enterpriseId, long storeId, String cashDeskName);

	public void submitBarcode(long enterpriseId, long storeId, String cashDeskName);
	
	public void submitBarcode(long enterpriseId, long storeId, String cashDeskName, String id);
	
	public String getDisplayOutput(long enterpriseId, long storeId, String cashDeskName);
	
	public String[] getPrinterOutput(long enterpriseId, long storeId, String cashDeskName);
	
	public void finishSale(long enterpriseId, long storeId, String cashDeskName);
	
	public void setCashPayment(long enterpriseId, long storeId, String cashDeskName);
	
	public boolean enterCashPaymentAmount(long enterpriseId, long storeId, String cashDeskName, double amount);
	
	public void setCardPayment(long enterpriseId, long storeId, String cashDeskName);
	
	public void enterBankinformation(long enterpriseId, long storeId, String cashDeskName, String pin, String accountNumber);
	
	public void setExpressLight(long enterpriseId, long storeId, String cashDeskName, boolean expressLightValue);
	
	public ExpressLightStates getExpressLight(long enterpriseId, long storeId, String cashDeskName);
	
	
}
