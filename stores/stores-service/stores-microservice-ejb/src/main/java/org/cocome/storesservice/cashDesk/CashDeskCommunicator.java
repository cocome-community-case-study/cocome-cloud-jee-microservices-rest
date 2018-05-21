package org.cocome.storesservice.cashDesk;

import java.util.ArrayList;
import java.util.Collection;

import org.cocome.enterpriseservice.EnterpriseOrganizer;
import org.cocome.enterpriseservice.IEnterpriseOrganizer;
import org.cocome.storesservice.cashDesk.cashDeskModel.CashDesk;

public class CashDeskCommunicator implements ICashDeskManaging{

	private ICashDesk asctiveCashDesk;
	
	private final IEnterpriseOrganizer eOrganizer;
	
	public CashDeskCommunicator() {
		eOrganizer = new EnterpriseOrganizer();
	}
	
	
	private void checkActiveStore(long enterpriseId, long storeId, String cashDeskName) {
		if(asctiveCashDesk == null || equalCashDesk(enterpriseId, storeId, cashDeskName) )
		{
			try {
				eOrganizer.getActiveEnterprise(enterpriseId).getActiveStore(storeId).getCashdesk(cashDeskName);
			}catch (NullPointerException e) {
				// TODO: handle exception
			}
		}
	}
	
	private boolean equalCashDesk(long enterpriseId, long storeId, String cashDeskName) {
		return	(asctiveCashDesk.getEnterpriseId() == enterpriseId 
				&& asctiveCashDesk.getStoreId() == storeId 
				&& asctiveCashDesk.getCashDeskName() == cashDeskName);
	}


	@Override
	public void createCashdesk(long enterpriseId, long storeId, String cashDeskName) {
		try {
			eOrganizer.getActiveEnterprise(enterpriseId).getActiveStore(storeId).getCashdesk(cashDeskName);
		}catch (NullPointerException e) {
			//TODO: handle exception
		}
	}


	@Override
	public Collection<ICashDesk> showCashdesks(long enterpriseId, long storeId) {
		Collection<ICashDesk> cashDesks = new ArrayList<ICashDesk>();

		try {
			cashDesks = eOrganizer.getActiveEnterprise(enterpriseId).getActiveStore(storeId).getAll();
		} catch (NullPointerException e) {
			// TODO: handle exception
		}
		
		return cashDesks;
	}


	@Override
	public void deleteCashdesk(long enterpriseId, long storeId, String cashDeskName) {
		try {
			eOrganizer.getActiveEnterprise(enterpriseId).getActiveStore(storeId).deleteCashdesk(cashDeskName);
		} catch (NullPointerException e) {
			// TODO: handle exception
		}
	}


	@Override
	public void startSale(long enterpriseId, long storeId, String cashDeskName) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void addDigittoBarcode(long enterpriseId, long storeId, String cashDeskName, char digit) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void removeLastDigitFromBarcode(long enterpriseId, long storeId, String cashDeskName) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void clearBarcode(long enterpriseId, long storeId, String cashDeskName) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void submitBarcode(long enterpriseId, long storeId, String cashDeskName) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public String getDisplayOutput(long enterpriseId, long storeId, String cashDeskName) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String[] getPrinterOutput(long enterpriseId, long storeId, String cashDeskName) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void finishSale(long enterpriseId, long storeId, String cashDeskName) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void setCashPayment(long enterpriseId, long storeId, String cashDeskName) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void enterCashPaymentAmount(long enterpriseId, long storeId, String cashDeskName, double amount) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void setCardPayment(long enterpriseId, long storeId, String cashDeskName) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void enterPin(long enterpriseId, long storeId, String cashDeskName, String pin) {
		// TODO Auto-generated method stub
		
	}



	
}
