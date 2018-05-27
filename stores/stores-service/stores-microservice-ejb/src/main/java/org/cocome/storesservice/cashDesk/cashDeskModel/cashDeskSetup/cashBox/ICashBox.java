package org.cocome.storesservice.cashDesk.cashDeskModel.cashDeskSetup.cashBox;

public interface ICashBox {
	void resetBarcode();
	void removeLastDigit();
	void submitBarcode();
	void addToDigit(char nextDigit);
	void pressButtonFinischSale();
	void selectCashPayment();
	void selectCardPayment();
}
