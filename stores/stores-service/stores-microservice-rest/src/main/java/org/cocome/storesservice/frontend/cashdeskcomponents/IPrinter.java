package org.cocome.storesservice.frontend.cashdeskcomponents;

import java.util.List;

import org.cocome.storesservice.frontend.viewdata.StockItemViewData;

public interface IPrinter {
	List<String> getPrinterOutput();
	void addPrinterOutput(String output);
	void resetPrinterOutput();
	
}
