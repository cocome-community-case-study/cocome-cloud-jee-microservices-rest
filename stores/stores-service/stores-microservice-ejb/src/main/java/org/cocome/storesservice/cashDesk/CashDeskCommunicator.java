package org.cocome.storesservice.cashDesk;

import org.cocome.enterpriseservice.EnterpriseOrganizer;
import org.cocome.enterpriseservice.IEnterpriseOrganizer;
import org.cocome.storesservice.cashDesk.cashDeskModel.CashDesk;

public class CashDeskCommunicator {

	private CashDesk asctiveCashDesk;
	
	private final IEnterpriseOrganizer eOrganizer;
	
	public CashDeskCommunicator() {
		eOrganizer = new EnterpriseOrganizer();
	}
	
	
	private void checkActiveStore(long enterpriseId, long storeId, String cashDeskName) {
		if(asctiveCashDesk == null || equalCashDesk(enterpriseId, storeId, cashDeskName) )
		eOrganizer.getActiveEnterprise(enterpriseId).getActiveStore(storeId).getCashdesk(storeId, cashDeskName);
	}
	
	private boolean equalCashDesk(long enterpriseId, long storeId, String cashDeskName) {
		return	(asctiveCashDesk.getEnterpriseId() == enterpriseId 
				&& asctiveCashDesk.getStoreId() == storeId 
				&& asctiveCashDesk.getCashDeskName() == cashDeskName);
	}
	
}
