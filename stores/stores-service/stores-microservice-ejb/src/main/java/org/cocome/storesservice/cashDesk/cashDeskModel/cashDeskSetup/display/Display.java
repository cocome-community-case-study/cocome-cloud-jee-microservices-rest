package org.cocome.storesservice.cashDesk.cashDeskModel.cashDeskSetup.display;

public class Display implements IDisplay{
	
	private String displayString = "Hello Word";

	@Override
	public void addDisplayLine(String line) {
		displayString = line;
	}

	@Override
	public String getDisplayLine() {
		return displayString;
	}

	@Override
	public void resetDisplayLine() {
		displayString = "Hello Word";
	}

	@Override
	public void addToDisplayLine(char toAdd) {
		displayString += toAdd;
	}

	@Override
	public void removeLastDigit() {
		 displayString = displayString.substring(0, displayString.length() - 1);
	}
}
