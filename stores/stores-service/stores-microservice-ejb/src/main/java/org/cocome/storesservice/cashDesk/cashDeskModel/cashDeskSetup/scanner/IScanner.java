package org.cocome.storesservice.cashDesk.cashDeskModel.cashDeskSetup.scanner;

public interface IScanner {
	void addToDigit(char nextDigit);
	
	void submitBarcode();
	
	void submitBarcode(String id);
	
	void resetBarcode();
	
	void removeLastDigit();
}
