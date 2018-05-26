package org.cocome.storesservice.cashDesk.cashDeskModel.cashDeskSetup.scanner;

public interface IScanner {
	void addToDigit(char nextDigit);
	void submitBarcode();
	void resetBarcode();
	void removeLastDigit();
}
