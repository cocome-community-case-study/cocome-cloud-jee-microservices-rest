package org.cocome.storesservice.cashDesk;

//public class CashDeskCommunicator implements ICashDeskManaging{
	public class CashDeskCommunicator {

//	private ICashDesk activeCashDesk;
//	
//	private final IEnterpriseOrganizer eOrganizer;
//	
//
//	public CashDeskCommunicator() {
//		eOrganizer = new EnterpriseOrganizer();
//	}
//	
//	
//	private void checkActiveStore(long enterpriseId, long storeId, String cashDeskName) {
//		if(activeCashDesk == null || equalCashDesk(enterpriseId, storeId, cashDeskName) )
//		{
//			try {
//				eOrganizer.getActiveEnterprise(enterpriseId).getActiveStore(storeId).getCashdesk(cashDeskName);
//			}catch (NullPointerException e) {
//				// TODO: handle exception
//			}
//		}
//	}
//	
//	private boolean equalCashDesk(long enterpriseId, long storeId, String cashDeskName) {
//		return	(activeCashDesk.getEnterpriseId() == enterpriseId 
//				&& activeCashDesk.getStoreId() == storeId 
//				&& activeCashDesk.getCashDeskName() == cashDeskName);
//	}
//
//
//	@Override
//	public void createCashdesk(long enterpriseId, long storeId, String cashDeskName) {
//		try {
//			eOrganizer.getActiveEnterprise(enterpriseId).getActiveStore(storeId).getCashdesk(cashDeskName);
//		}catch (NullPointerException e) {
//			//TODO: handle exception
//		}
//	}
//
//
//	@Override
//	public Collection<ICashDesk> showCashdesks(long enterpriseId, long storeId) {
//		Collection<ICashDesk> cashDesks = new ArrayList<ICashDesk>();
//
//		try {
//			cashDesks = eOrganizer.getActiveEnterprise(enterpriseId).getActiveStore(storeId).getAllCashDesks();
//		} catch (NullPointerException e) {
//			// TODO: handle exception
//		}
//		
//		return cashDesks;
//	}
//
//
//	@Override
//	public void deleteCashdesk(long enterpriseId, long storeId, String cashDeskName) {
//		try {
//			eOrganizer.getActiveEnterprise(enterpriseId).getActiveStore(storeId).deleteCashdesk(cashDeskName);
//		} catch (NullPointerException e) {
//			// TODO: handle exception
//		}
//	}
//
//
//	@Override
//	public void startSale(long enterpriseId, long storeId, String cashDeskName) {
//		checkActiveStore(enterpriseId, storeId, cashDeskName);
//		activeCashDesk.startSale();
//	}
//
//
//	@Override
//	public void addDigitToBarcode(long enterpriseId, long storeId, String cashDeskName, char digit) {
//		checkActiveStore(enterpriseId, storeId, cashDeskName);
//		activeCashDesk.addDigitToBarcode(digit);
//	}
//
//
//	@Override
//	public void removeLastDigitFromBarcode(long enterpriseId, long storeId, String cashDeskName) {
//		checkActiveStore(enterpriseId, storeId, cashDeskName);
//		activeCashDesk.removeLastDigitFromBarcode();
//	}
//
//
//	@Override
//	public void clearBarcode(long enterpriseId, long storeId, String cashDeskName) {
//		checkActiveStore(enterpriseId, storeId, cashDeskName);
//		activeCashDesk.clearBarcode();
//	}
//
//
//	@Override
//	public void submitBarcode(long enterpriseId, long storeId, String cashDeskName) {
//		checkActiveStore(enterpriseId, storeId, cashDeskName);
//		activeCashDesk.submitBarcode();
//	}
//
//	@Override
//	public void submitBarcode(long enterpriseId, long storeId, String cashDeskName, String id) {
//		checkActiveStore(enterpriseId, storeId, cashDeskName);
//		activeCashDesk.submitBarcode(id);
//	}
//
//	@Override
//	public String getDisplayOutput(long enterpriseId, long storeId, String cashDeskName) {
//		checkActiveStore(enterpriseId, storeId, cashDeskName);
//		return activeCashDesk.getDisplayOutput();
//	}
//
//
//	@Override
//	public String[] getPrinterOutput(long enterpriseId, long storeId, String cashDeskName) {
//		checkActiveStore(enterpriseId, storeId, cashDeskName);
//		return activeCashDesk.getPrinterOutput();
//	}
//
//
//	@Override
//	public void finishSale(long enterpriseId, long storeId, String cashDeskName) {
//		checkActiveStore(enterpriseId, storeId, cashDeskName);
//		activeCashDesk.finishSale();
//	}
//
//
//	@Override
//	public void setCashPayment(long enterpriseId, long storeId, String cashDeskName) {
//		checkActiveStore(enterpriseId, storeId, cashDeskName);
//		activeCashDesk.setCashPayment();
//	}
//
//
//	@Override
//	public boolean enterCashPaymentAmount(long enterpriseId, long storeId, String cashDeskName, double amount) {
//		checkActiveStore(enterpriseId, storeId, cashDeskName);
//		return activeCashDesk.enterCashPaymentAmount(amount);
//	}
//
//
//	@Override
//	public void setCardPayment(long enterpriseId, long storeId, String cashDeskName) {
//		checkActiveStore(enterpriseId, storeId, cashDeskName);
//		activeCashDesk.setCardPayment();
//	}
//
//
//	@Override
//	public void enterBankinformation(long enterpriseId, long storeId, String cashDeskName, String pin,
//			String accountNumber) {
//		checkActiveStore(enterpriseId, storeId, cashDeskName);
//		activeCashDesk.enterBankinformation(pin, accountNumber);
//	}
//
//
//	@Override
//	public void setExpressLight(long enterpriseId, long storeId, String cashDeskName, boolean expressLightValue) {
//		checkActiveStore(enterpriseId, storeId, cashDeskName);
//		activeCashDesk.setExpressLight(expressLightValue);
//	}
//
//
//	@Override
//	public ExpressLightStates getExpressLight(long enterpriseId, long storeId, String cashDeskName) {
//		checkActiveStore(enterpriseId, storeId, cashDeskName);
//		return activeCashDesk.getExpressLight();
//	}
//
//


	
}
