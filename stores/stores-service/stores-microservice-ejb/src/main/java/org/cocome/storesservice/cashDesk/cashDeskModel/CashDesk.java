package org.cocome.storesservice.cashDesk.cashDeskModel;

import org.cocome.storesservice.cashDesk.ICashDesk;

public class CashDesk implements ICashDesk{
	
	
	private final String cashDeskName;
	
	private final long enterpriseId;
	
	private final long storeId;

	public CashDesk(Long enterpriseId, long storeId, String name) {
		this.enterpriseId = enterpriseId;
		this.storeId = storeId;
		cashDeskName = name;
	}	
	
	public String getCashDeskName() {
		return cashDeskName;
	}


	public long getEnterpriseId() {
		return enterpriseId;
	}


	public long getStoreId() {
		return storeId;
	}
/*	private static final Set<CashDeskState> START_SALE_STATES = CashDesk.setOfStates(
			CashDeskState.EXPECTING_SALE,
			CashDeskState.EXPECTING_ITEMS,
			CashDeskState.EXPECTING_PAYMENT,
			CashDeskState.PAYING_BY_CASH,
			CashDeskState.EXPECTING_CARD_INFO,
			CashDeskState.PAYING_BY_CREDIT_CARD);
*/

	@Override
	public void startSale(String cashDeskName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addDigittoBarcode(char digit) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeLastDigitFromBarcode() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clearBarcode() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void submitBarcode() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getDisplayOutput() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getPrinterOutput() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void finishSale() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setCashPayment() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterCashPaymentAmount(double amount) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setCardPayment() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enterPin(String pin) {
		// TODO Auto-generated method stub
		
	}

}
