package org.cocome.storesservice.cashDesk.cashDeskModel.cashDeskSetup.printer;

import java.util.ArrayList;
import java.util.List;

public class Printer implements IPrinter{

	private List<String> outputLines;
	
	public Printer() {
		outputLines = new ArrayList<String>();
	}
	
	@Override
	public String[] getPrinterOutput() {
		return (String[]) outputLines.toArray();
	}

	@Override
	public void addPrinterOutput(String output) {
		outputLines.add(output);
	}

	@Override
	public void resetPrinterOutput() {
		outputLines.clear();
	}

}
