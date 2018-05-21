package org.cocome.storesservice.cashDesk;

import java.util.Collection;


public interface ICashDeskManaging extends ICashDeskFunctionality{
	
	public void createCashdesk(long enterpriseId, long storeId, String cashDeskName);
	
	public Collection<ICashDesk> showCashdesks(long enterpriseId, long storeId);
	
	public void deleteCashdesk(long enterpriseId, long storeId, String cashDeskName);
	
	
	
}
