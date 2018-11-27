package org.cocome.storesservice.events;

public class ProductOutOfStockEvent {
	
	private final String text ="The Product is not in stock anymore";
	
	public ProductOutOfStockEvent() {
		
	}
	
	public String getEvetText() {
		return text;
	}

}
