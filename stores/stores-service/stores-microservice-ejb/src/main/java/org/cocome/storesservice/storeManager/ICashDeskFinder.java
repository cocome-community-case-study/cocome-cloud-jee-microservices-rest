package org.cocome.storesservice.storeManager;

import org.cocome.storesservice.cashDesk.cashDeskModel.CashDesk;

interface ICashDeskFinder {
	CashDesk getCashdesk(long storeId, String cashDeskName);
}
