package org.cocome.storesservice.cashDesk.cashDeskModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.cocome.storesservice.cashDesk.cashDeskModel.cashDeskSetup.IScannerAdapter;
import org.cocome.storesservice.cashDesk.cashDeskModel.cashDeskSetup.cashBox.CashBox;
import org.cocome.storesservice.cashDesk.cashDeskModel.cashDeskSetup.cashBox.ICashBox;
import org.cocome.storesservice.cashDesk.cashDeskModel.cashDeskSetup.display.Display;
import org.cocome.storesservice.cashDesk.cashDeskModel.cashDeskSetup.display.IDisplay;
import org.cocome.storesservice.cashDesk.cashDeskModel.cashDeskSetup.printer.IPrinter;
import org.cocome.storesservice.cashDesk.cashDeskModel.cashDeskSetup.printer.Printer;
import org.cocome.storesservice.cashDesk.cashDeskModel.cashDeskSetup.scanner.IScanner;
import org.cocome.storesservice.cashDesk.cashDeskModel.cashDeskSetup.scanner.Scanner;
import org.cocome.storesservice.domain.StockItem;
import org.cocome.structures.Pair;

import storageOrganizer.IStorageOrganizer;

public class CashDesk implements ICashDesk, IScannerAdapter{
	
	
	private final String cashDeskName;
	
	private final long enterpriseId;
	
	private final long storeId;
	
	private final IPrinter printer;
	private final IScanner scanner;
	private final IDisplay display;
	private final ICashBox cashBox;
	
	private IStorageOrganizer storageOrg;
	
	CashDeskState state;
	HashMap<Long, Pair<Integer, StockItem>> saleProducts;
	double runningTotal;
	
	boolean expressModeEnabled;


	public CashDesk(Long enterpriseId, long storeId, String name, IStorageOrganizer storageOrganizer) {
		this.enterpriseId = enterpriseId;
		this.storeId = storeId;
		cashDeskName = name;
		this.storageOrg = storageOrganizer;
		
		printer = new Printer();
		scanner = new Scanner(this);
		display = new Display();
		cashBox = new CashBox();
		
		state = CashDeskState.EXPECTING_SALE;
		expressModeEnabled = false;
	}	
	
	public String getCashDeskName() {
		return cashDeskName;
	}


	public long getEnterpriseId() {
		return enterpriseId;
	}


	public long getStoreId() {
		return storeId;
	}
	/**
	 * New sale can be started (and thus current sale aborted) in almost
	 * all cash desk states except when already paid by cash.
	 */
	private static final Set<CashDeskState> START_SALE_STATES = new HashSet<CashDeskState>() {{
		add(CashDeskState.EXPECTING_SALE);
		add(CashDeskState.EXPECTING_ITEMS);
		add(CashDeskState.EXPECTING_PAYMENT);
		add(CashDeskState.PAYING_BY_CASH);
		add(CashDeskState.EXPECTING_CARD_INFO);
		add(CashDeskState.PAYING_BY_CREDIT_CARD);
		add(CashDeskState.PAID_BY_CREDIT_CARD);
	}};

	/**
	 * Items can be only added to the sale after it has been started and
	 * has not been indicated by the cashier as finished.
	 */
	private static final Set<CashDeskState> ADD_ITEM_TO_SALE_STATES = new HashSet<CashDeskState>() {{
		add(CashDeskState.EXPECTING_ITEMS);}};
			
	/**
	 * Sale can be only finished when scanning items.
	 */
	private static final Set<CashDeskState> FINISH_SALES_STATES = new HashSet<CashDeskState>() {{
		add(CashDeskState.EXPECTING_ITEMS);
	}};

	/**
	 * Payment mode can be selected either when a sale has been finished or
	 * when switching from credit card payment) to cash.
	 */
	private static final Set<CashDeskState> SELECT_PAYMENT_MODE_STATES =new HashSet<CashDeskState>() {{
		add(CashDeskState.EXPECTING_PAYMENT);
		add(CashDeskState.EXPECTING_CARD_INFO);
		add(CashDeskState.PAYING_BY_CREDIT_CARD);
	}};

	/**
	 * Cash payment can only proceed when the cashier selected the cash
	 * payment mode.
	 */
	private static final Set<CashDeskState> START_CASH_PAYMENT_STATES =new HashSet<CashDeskState>() {{
		add(CashDeskState.PAYING_BY_CASH);
	}};

	/**
	 * In cash payment mode, sale is finished when it has been paid for
	 * and the cash box has been closed.
	 */
	private static final Set<CashDeskState> FINISH_CASH_PAYMENT_STATES =new HashSet<CashDeskState>() {{
		add(CashDeskState.PAID_BY_CASH);
	}};

	/**
	 * Credit card payment can be made only when the cashier selected the credit
	 * card payment mode or when rescanning the card.
	 */
	private static final Set<CashDeskState> START_CREADIT_CARD_PAYMENT_STATES =new HashSet<CashDeskState>() {{
		add(CashDeskState.EXPECTING_CARD_INFO);
		add(CashDeskState.PAYING_BY_CREDIT_CARD);
	}};
	
	/**
	 * In credit card payment mode, sale is finished only when we have the
	 * credit card info.
	 */
	private static final Set<CashDeskState> FINISH_CREDIT_CARD_PAYMENT_STATES = new HashSet<CashDeskState>() {{
		add(CashDeskState.PAYING_BY_CREDIT_CARD);
	}};
	


	

	@Override
	public void startSale() {
		if(this.actionInStateAvailable(START_SALE_STATES)) {
			this.resetSale();
			state = CashDeskState.EXPECTING_ITEMS;
		}
	}
	
	private void resetSale() {
		if(saleProducts != null) {
			for (Pair<Integer, StockItem> saleStat : saleProducts.values()) {
				storageOrg.incrementInventory(saleStat.getRight().getId(), saleStat.getLeft());
			}
			saleProducts.clear();
		}else {
			saleProducts = new HashMap<Long, Pair<Integer, StockItem>>();
		}
		runningTotal = 0.0;	
		}

	@Override
	public void addDigitToBarcode(char digit) {
		if(this.actionInStateAvailable(ADD_ITEM_TO_SALE_STATES)){
			scanner.addToDigit(digit);
			display.addToDisplayLine(digit);
		}
	}

	@Override
	public void removeLastDigitFromBarcode() {
		if(this.actionInStateAvailable(ADD_ITEM_TO_SALE_STATES)){
			scanner.removeLastDigit();
			display.removeLastDigit();
		}
		
	}

	@Override
	public void clearBarcode() {
		if(this.actionInStateAvailable(ADD_ITEM_TO_SALE_STATES)){
			scanner.resetBarcode();
			display.resetDisplayLine();
		}
	}

	@Override
	public void submitBarcode() {
		if(this.actionInStateAvailable(ADD_ITEM_TO_SALE_STATES)){
			scanner.submitBarcode();
		}
	}
	

	@Override
	public String getDisplayOutput() {
		return display.getDisplayLine();
	}

	@Override
	public String[] getPrinterOutput() {
		return printer.getPrinterOutput();
	}

	@Override
	public void finishSale() {
		if(actionInStateAvailable(FINISH_SALES_STATES)) {
			display.addDisplayLine("TO Pay: " + runningTotal);
			state = CashDeskState.EXPECTING_PAYMENT;
		}
	}

	@Override
	public void setCashPayment() {
		if(actionInStateAvailable(START_CASH_PAYMENT_STATES)) {
			state = CashDeskState.PAYING_BY_CASH;
		}
	}

	@Override
	public boolean enterCashPaymentAmount(double amount) {
		if(actionInStateAvailable(START_CASH_PAYMENT_STATES)) {
			if(amount > runningTotal) {
				printer.addPrinterOutput("Paid " + amount);
				printer.addPrinterOutput("Return: " + (amount - runningTotal));
				state = CashDeskState.PAID_BY_CASH;
				endSaleProcess();
				return true;
			} else {
				runningTotal -= amount;
				return false;
			}
		}
	}

	@Override
	public void setCardPayment() {
		if(actionInStateAvailable(START_CREADIT_CARD_PAYMENT_STATES)) {
			state = CashDeskState.PAYING_BY_CREDIT_CARD; 
		}
	}

	@Override
	public void enterBankinformation(String pin, String accountNumber) {
		if(actionInStateAvailable(START_CREADIT_CARD_PAYMENT_STATES)) {
			// TODO: Validate bank information, paiment amount etc.
		
			//end of sale process, reset products
			state = CashDeskState.PAID_BY_CREDIT_CARD;
			endSaleProcess();
		}
	}

	public void barcodeScanned(long id) {
		//get item from repo, reduce storage amount, add to list
		if(saleProducts.containsKey(id)) {
			if(saleProducts.get(id).getRight().getAmount() > saleProducts.get(id).getLeft()) {
				storageOrg.reduceInventory(id, 1);
				saleProducts.get(id).setLeft(saleProducts.get(id).getLeft() +1);
				updateAmount(saleProducts.get(id).getRight().getSalesPrice());
			} else {
				// TODO what if not enought products unavailable
				return;
			}
		}else if(storageOrg.containStockItem(id)) {
			if(storageOrg.reduceInventory(id, 1)) {
				saleProducts.put(id, new Pair<Integer, StockItem>(1, storageOrg.getItem(id)));
				updateAmount(saleProducts.get(id).getRight().getSalesPrice());
			} else {
				// TODO what if not enought products unavailable
				return;
			}
		} else {
		// TODO what if barcode unavailable
		return;
		}
		printer.addPrinterOutput(display.getDisplayLine());
		display.resetDisplayLine();
	}

	private void updateAmount(double amount) {
		this.runningTotal += amount;
	}
	
	private boolean actionInStateAvailable(Set<CashDeskState> stateSet) {
		return stateSet.contains(this.state);
	}
	
	private void endSaleProcess() {
		saleProducts.clear();	
		}

	@Override
	public void enterBarcode(String id) {
		if(this.actionInStateAvailable(ADD_ITEM_TO_SALE_STATES)){
			scanner.enterBarcode(id);
		}
	}


	}
}