package org.cocome.storesservice.events;

import java.io.Serializable;

public class CheckExpressModeEvent implements Serializable{

	private static final long serialVersionUID = 3424361535888492044L;
	
	private int numberOfSaleItems;

	public CheckExpressModeEvent(int numberOfSaleItems) {
		
		this.numberOfSaleItems = numberOfSaleItems;
	}

	public int getNumberOfSaleItems() {
		return numberOfSaleItems;
	}

	public void setNumberOfSaleItems(int numberOfSaleItems) {
		this.numberOfSaleItems = numberOfSaleItems;
	}
	

}
