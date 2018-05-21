package org.cocome.storesservice.cashDesk.cashDeskModel;

import java.util.Set;

import org.apache.log4j.spi.LoggingEvent;

public class CashDesk{
	
	
	private final String cashDeskName;
	
	private final long enterpriseId;
	
	private final long storeId;
	
	
	public String getCashDeskName() {
		return cashDeskName;
	}


	public long getEnterpriseId() {
		return enterpriseId;
	}


	public long getStoreId() {
		return storeId;
	}


	public CashDesk(Long enterpriseId, long storeId, String name) {
		this.enterpriseId = enterpriseId;
		this.storeId = storeId;
		cashDeskName = name;
	}
/*	private static final Set<CashDeskState> START_SALE_STATES = CashDesk.setOfStates(
			CashDeskState.EXPECTING_SALE,
			CashDeskState.EXPECTING_ITEMS,
			CashDeskState.EXPECTING_PAYMENT,
			CashDeskState.PAYING_BY_CASH,
			CashDeskState.EXPECTING_CARD_INFO,
			CashDeskState.PAYING_BY_CREDIT_CARD);
*/

}
