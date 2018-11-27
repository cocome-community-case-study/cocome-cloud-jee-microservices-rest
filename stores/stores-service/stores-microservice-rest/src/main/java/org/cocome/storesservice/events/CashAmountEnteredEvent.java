package org.cocome.storesservice.events;

import java.io.Serializable;

public class CashAmountEnteredEvent implements Serializable {

	private static final long serialVersionUID = -5441935251526952790L;

	//

	private final double __amount;

	//

	public CashAmountEnteredEvent(final double amount) {
		__amount = amount;
	}

	public double getCashAmount() {
		return __amount;
	}

}
