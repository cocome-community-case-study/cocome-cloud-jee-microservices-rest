package org.cocome.storesservice.cashDesk.cashDeskModel.cashDeskSetup.scanner;

import org.cocome.storesservice.cashDesk.cashDeskModel.CashDesk;
import org.cocome.storesservice.cashDesk.cashDeskModel.cashDeskSetup.IScannerAdapter;

public class Scanner implements IScanner{
	
	private final IScannerAdapter cDesk;
	
	public Scanner(CashDesk cDesk) {
		this.cDesk = cDesk;
	}


	@Override
	public void submitBarcode(String id) {
		try {
			
		cDesk.barcodeScanned(Long.parseLong(id));
		barcode="";
		} catch (Exception e) {
			// TODO: handle exception
		}
	}


}
