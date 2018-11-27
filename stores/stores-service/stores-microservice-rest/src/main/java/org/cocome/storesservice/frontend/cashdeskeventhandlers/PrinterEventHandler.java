package org.cocome.storesservice.frontend.cashdeskeventhandlers;

import java.io.Serializable;
import java.util.Date;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.cocome.storesservice.events.RunningTotalChangedEvent;
import org.cocome.storesservice.events.SaleStartedEvent;
import org.cocome.storesservice.frontend.cashdeskcomponents.IPrinter;

@SessionScoped
@Named
public class PrinterEventHandler implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3962609445630426209L;

	private static final String DELIMITER = "----------------------------\n";

	@Inject
	IPrinter printer;

	private static final Logger LOG = Logger.getLogger(PrinterEventHandler.class);

	public void onEvent(@Observes SaleStartedEvent event) {
		LOG.debug("FRONTEND: Printer start new Printout");
		startPrintout();
	}

	public void onEvent(@Observes RunningTotalChangedEvent event) {
		final double runningTotal = event.getRunningTotal();
		LOG.debug("FRONTEND: Printer new runningTotal: " + runningTotal);

		this.printProductInfo(event.getProductName(), event.getProductPrice());

	}

	private void printProductInfo(final String productName, final double productPrice) {
		this.printer.addPrinterOutput(productName + ": " + productPrice + "\n");
	}

	private void startPrintout() {
		this.printer.resetPrinterOutput();
		this.printer.addPrinterOutput(new Date().toString());
		this.printer.addPrinterOutput("\n");
		this.printer.addPrinterOutput(DELIMITER);
	}

}
