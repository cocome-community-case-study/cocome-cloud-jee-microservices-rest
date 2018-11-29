package org.cocome.storesservice.frontend.cashdeskcomponents;

import java.util.List;

public interface IPrinter {
	List<String> getPrinterOutput();
	void addPrinterOutput(String output);
	void resetPrinterOutput();
	
}
