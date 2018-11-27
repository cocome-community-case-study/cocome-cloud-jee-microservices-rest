package org.cocome.storesservice.events;

import java.io.Serializable;

public class RunningTotalChangedEvent implements Serializable {

	private static final long serialVersionUID = -300914931510566066L;

	//

	private final String __productName;

	private final double __productPrice;

	private final double __runningTotal;

	//

	public RunningTotalChangedEvent(
			final String productName, final double productPrice,
			final double runningTotal) {
		__productName = productName;
		__productPrice = productPrice;
		__runningTotal = runningTotal;
	}

	public String getProductName() {
		return __productName;
	}

	public double getProductPrice() {
		return __productPrice;
	}

	public double getRunningTotal() {
		return __runningTotal;
	}

	//

	@Override
	public String toString() {
		return String.format(
				"RunningTotalChangedEvent(%s, %s, %s)",
				__productName, __productPrice, __runningTotal
				);
	}

}
