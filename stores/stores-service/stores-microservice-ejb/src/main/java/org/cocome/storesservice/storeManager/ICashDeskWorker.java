package org.cocome.storesservice.storeManager;

import java.util.Collection;

import org.cocome.storesservice.cashDesk.ICashDesk;

interface ICashDeskWorker {
	ICashDesk getCashdesk(String cashDeskName);
	
	void deleteCashdesk(String cashDeskName);
	
	Collection<ICashDesk> getAll();
}
