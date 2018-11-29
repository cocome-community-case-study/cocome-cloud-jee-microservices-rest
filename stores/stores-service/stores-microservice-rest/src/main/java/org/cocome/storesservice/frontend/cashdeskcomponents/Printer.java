package org.cocome.storesservice.frontend.cashdeskcomponents;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 * Represents the printer component of the CashDesk
 * @author Niko Benkler
 * @author Robert Heinrich
 *
 */
@Named
@SessionScoped
public class Printer implements IPrinter, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8107111856677880113L;
	private List<String> outputLines;

	public Printer() {
		outputLines = new ArrayList<String>();
	}

	@Override
	public List<String> getPrinterOutput() {
		return outputLines;
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
