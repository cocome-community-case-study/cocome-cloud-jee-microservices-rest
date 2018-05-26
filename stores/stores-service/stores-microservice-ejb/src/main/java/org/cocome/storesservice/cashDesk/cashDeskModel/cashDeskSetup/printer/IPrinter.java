package org.cocome.storesservice.cashDesk.cashDeskModel.cashDeskSetup.printer;

public interface IPrinter {
	String[] getPrinterOutput();
	void addPrinterOutput(String output);
	void resetPrinterOutput();
}
