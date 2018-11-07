package org.cocome.tobiAlt;

import java.util.Collection;

import org.cocome.storesservice.cashDesk.cashDeskModel.ICashDesk;

interface ICashDeskWorker {
	ICashDesk getCashdesk(String cashDeskName);
	
	void deleteCashdesk(String cashDeskName);
	
	Collection<ICashDesk> getAllCashDesks();
}
