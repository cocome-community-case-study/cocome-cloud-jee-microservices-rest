package org.cocome.storesservice.frontend.cashdeskcomponents;



public interface ICashDesk {

	String getCashDeskName();

	void setCashDeskName(String cashDeskName);

	boolean isCashDeskNameNeeded();

	void setCashDeskNameNeeded(boolean cashDeskNameNeeded);

	void requestNewCashDesk();

	boolean isSaleSuccessful();


	void setInExpressMode(boolean inExpressMode);

	boolean isInExpressMode();


	void setCardPayment(boolean cardPayment);

	boolean isCardPayment();

	void setCashPayment(boolean cashPayment);

	boolean isCashPayment();

	void setSaleStarted(boolean saleStarted);

	boolean isSaleStarted();




	void startSale();
}