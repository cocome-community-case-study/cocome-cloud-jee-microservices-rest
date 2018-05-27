package org.cocome.storesservice.cashDesk.cashDeskModel.cashDeskSetup.cashBox;

import org.cocome.storesservice.cashDesk.cashDeskModel.CashDesk;

public class CashBox implements ICashBox{
	
	private String barcode;
	
	private final CashDesk cDesk;
	
	public CashBox(CashDesk cDesk) {
		this.cDesk = cDesk;
		this.barcode = "";
	}
	
	@Override
	public void addToDigit(char nextDigit) {
		if(Character.isDigit(nextDigit)) {
			barcode += nextDigit;
		}
	}

	@Override
	public void submitBarcode() {
		if(barcode != "")
			cDesk.barcodeScanned(Long.parseLong(barcode));
		barcode="";
	}
	@Override
	public void resetBarcode() {
		barcode ="";
	}

	@Override
	public void removeLastDigit() {
		barcode = barcode.substring(0, barcode.length() - 1);
		
	}

	@Override
	public void pressButtonFinischSale() {
		cDesk.finischSaleButtompressed();		
	}

	@Override
	public void selectCashPayment() {
		cDesk.setCashPaymentButtomPressed();
	}

	@Override
	public void selectCardPayment() {
		cDesk.setCardPaymentButtomPressed();
	}

}
