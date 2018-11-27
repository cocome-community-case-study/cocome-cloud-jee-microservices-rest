package org.cocome.storesservice.events;

import java.io.Serializable;

public class ChangeAmountCalculatedEvent implements Serializable {

	private static final long serialVersionUID = 3529515546013136702L;

	//

	private final double __changeAmount;

	//

	public ChangeAmountCalculatedEvent(final double changeAmount) {
		__changeAmount = changeAmount;
	}

	public double getChangeAmount() {
		return __changeAmount;
	}

}
