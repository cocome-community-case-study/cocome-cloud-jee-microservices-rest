package org.cocome.storesservice.events;

import java.io.Serializable;

public class InsufficientCashAmountEvent implements Serializable {
	private static final long serialVersionUID = -5441935251526952790L;
	//

	private final double __enteredAmount;
	
	private final double __requiredAmount;

	//

	public InsufficientCashAmountEvent(final double enteredAmount, final double requiredAmount) {
		__enteredAmount = enteredAmount;
		__requiredAmount = requiredAmount;
	}

	public double getEnteredAmount() {
		return __enteredAmount;
	}
	
	public double getRequiredAmount() {
		return __requiredAmount;
	}
}
