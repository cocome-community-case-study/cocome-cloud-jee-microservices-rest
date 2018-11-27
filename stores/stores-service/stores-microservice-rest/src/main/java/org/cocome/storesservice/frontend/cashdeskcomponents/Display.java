package org.cocome.storesservice.frontend.cashdeskcomponents;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named
@SessionScoped
public class Display implements IDisplay, Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2178536900761393334L;
	private String displayString = "Welcome";

	@Override
	public void setDisplayLine(String line) {
		displayString = line;
	}

	@Override
	public String getDisplayLine() {
		return displayString;
	}

	@Override
	public void resetDisplayLine() {
		displayString = "Welcome";
	}

	@Override
	public void addToDisplayLine(char toAdd) {
		displayString += toAdd;
	}

	@Override
	public void removeLastDigit() {
		 displayString = displayString.substring(0, displayString.length() - 1);
	}

	@Override
	public void addDisplayLine(String line) {
		this.displayString += "\n" + line;
		
	}
}
