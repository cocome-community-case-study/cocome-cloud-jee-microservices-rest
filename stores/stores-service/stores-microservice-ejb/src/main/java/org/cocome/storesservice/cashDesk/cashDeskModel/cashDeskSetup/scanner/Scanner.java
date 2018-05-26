package org.cocome.storesservice.cashDesk.cashDeskModel.cashDeskSetup.scanner;

import org.cocome.storesservice.cashDesk.cashDeskModel.CashDesk;
import org.cocome.storesservice.cashDesk.cashDeskModel.cashDeskSetup.IScannerAdapter;

public class Scanner implements IScanner{
	private String barcode = "";
	private final IScannerAdapter cDesk;
	
	public Scanner(CashDesk cDesk) {
		this.cDesk = cDesk;
	}

	@Override
	public void addToDigit(char nextDigit) {
		if(Character.isDigit(nextDigit)) {
			barcode += nextDigit;
		}
	}

	@Override
	public void submitBarcode() {
		cDesk.barcodeScanned(Long.parseLong(barcode));
		barcode="";
	}

	@Override
	public void resetBarcode() {
		barcode = "";
	}
	
	@Override
	public void removeLastDigit() {
		 barcode = barcode.substring(0, barcode.length() - 1);
	}
}
