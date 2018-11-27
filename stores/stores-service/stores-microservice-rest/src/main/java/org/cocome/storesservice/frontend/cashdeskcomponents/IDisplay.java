package org.cocome.storesservice.frontend.cashdeskcomponents;

public interface IDisplay {
	void setDisplayLine(String line);
	
	void addToDisplayLine(char toAdd);
	
	void removeLastDigit();
	
	String getDisplayLine();
	
	void resetDisplayLine();
	
	void addDisplayLine(String line);
}
