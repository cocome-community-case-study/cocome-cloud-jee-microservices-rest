package org.cocome.storesservice.cashDesk.cashDeskModel.cashDeskSetup.display;

public interface IDisplay {
	void addDisplayLine(String line);
	
	void addToDisplayLine(char toAdd);
	
	void removeLastDigit();
	
	String getDisplayLine();
	
	void resetDisplayLine();
}
