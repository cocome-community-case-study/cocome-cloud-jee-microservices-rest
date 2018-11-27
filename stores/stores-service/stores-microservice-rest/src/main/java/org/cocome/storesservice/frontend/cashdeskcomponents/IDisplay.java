package org.cocome.storesservice.frontend.cashdeskcomponents;

public interface IDisplay {
	void addDisplayLine(String line);
	
	void addToDisplayLine(char toAdd);
	
	void removeLastDigit();
	
	String getDisplayLine();
	
	void resetDisplayLine();
}
