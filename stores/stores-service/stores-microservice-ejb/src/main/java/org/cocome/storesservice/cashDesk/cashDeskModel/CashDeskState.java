package org.cocome.storesservice.cashDesk.cashDeskModel;

public enum CashDeskState {

	/** Initial state. */
	EXPECTING_SALE,

	/** After a sale has started ("New Sale" button pushed). */
	EXPECTING_ITEMS,

	/**
	 * After a sale has finished (all products have been scanned) and
	 * "Finish Sale" button pushed.
	 */
	EXPECTING_PAYMENT,

	/** After the choice of cash payment was made. */
	PAYING_BY_CASH,

	/** After the cash payment. */
	PAID_BY_CASH,

	/** After the choice of credit card payment was made. */
	EXPECTING_CARD_INFO,

	/** After the credit card was scanned. */
	PAYING_BY_CREDIT_CARD;
}
