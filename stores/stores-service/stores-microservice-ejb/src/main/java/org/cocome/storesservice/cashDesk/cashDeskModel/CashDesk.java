package org.cocome.storesservice.cashDesk.cashDeskModel;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.IconifyAction;



//public class CashDesk implements ICashDesk, IScannerAdapter{
	public class CashDesk {
	
	
//	private final String cashDeskName;
//	
//	private final long enterpriseId;
//	
//	private final StoreAdminManager storeAdminManager;
//	
//	private final IPrinter printer;
//	private final IScanner scanner;
//	private final IDisplay display;
//	private final ICashBox cashBox;
//	private final IExpressLight expressLight;
//	
//	private IStorageOrganizerSystem storageOrg;
//	
//	CashDeskState state;
//	HashMap<Long, Pair<Integer, StockItem>> saleProducts;
//	double runningTotal;
//	
//	
//	//Express mode stuff
//	private int expressModeRange = 5;
//	private int[] salesAmount;
//	int nextsalesAmountIndes;
//	private int expressModeLimit;
//
//
//	public CashDesk(long enterpriseId, StoreAdminManager storeAdminManager, String name, IStorageOrganizerSystem storageOrganizer) {
//		this.enterpriseId = enterpriseId;
//		this.storeAdminManager = storeAdminManager;
//		cashDeskName = name;
//		this.storageOrg = storageOrganizer;
//		
//		printer = new Printer();
//		scanner = new Scanner(this);
//		display = new Display();
//		cashBox = new CashBox(this);
//		expressLight = new ExpressLight();
//		
//		state = CashDeskState.EXPECTING_SALE;
//		salesAmount = new int[expressModeRange];
//		nextsalesAmountIndes = 0;
//		expressModeLimit = 8;
//		for(int i =0; i < expressModeRange; i++) {
//			salesAmount[i]= -1; 
//		}
//	}	
//	
//	public String getCashDeskName() {
//		return cashDeskName;
//	}
//
//
//	public long getEnterpriseId() {
//		return enterpriseId;
//	}
//
//
//	public long getStoreId() {
//		return storeAdminManager.getStoreId();
//	}
//	/**
//	 * New sale can be started (and thus current sale aborted) in almost
//	 * all cash desk states except when already paid by cash.
//	 */
//	@SuppressWarnings("serial")
//	private static final Set<CashDeskState> START_SALE_STATES = new HashSet<CashDeskState>() {{
//		add(CashDeskState.EXPECTING_SALE);
//		add(CashDeskState.EXPECTING_ITEMS);
//		add(CashDeskState.EXPECTING_PAYMENT);
//		add(CashDeskState.PAYING_BY_CASH);
//		add(CashDeskState.EXPECTING_CARD_INFO);
//		add(CashDeskState.PAYING_BY_CREDIT_CARD);
//		add(CashDeskState.PAID_BY_CREDIT_CARD);
//	}};
//
//	/**
//	 * Items can be only added to the sale after it has been started and
//	 * has not been indicated by the cashier as finished.
//	 */
//	@SuppressWarnings("serial")
//	private static final Set<CashDeskState> ADD_ITEM_TO_SALE_STATES = new HashSet<CashDeskState>() {
//	{
//		add(CashDeskState.EXPECTING_ITEMS);}};
//			
//	/**
//	 * Sale can be only finished when scanning items.
//	 */
//		@SuppressWarnings("serial")
//	private static final Set<CashDeskState> FINISH_SALES_STATES = new HashSet<CashDeskState>() {{
//		add(CashDeskState.EXPECTING_ITEMS);
//	}};
//
//	/**
//	 * Payment mode can be selected either when a sale has been finished or
//	 * when switching from credit card payment) to cash.
//	 */
//	@SuppressWarnings("serial")
//	private static final Set<CashDeskState> SELECT_PAYMENT_MODE_STATES =new HashSet<CashDeskState>() {{
//		add(CashDeskState.EXPECTING_PAYMENT);
//		add(CashDeskState.EXPECTING_CARD_INFO);
//		add(CashDeskState.PAYING_BY_CREDIT_CARD);
//	}};
//
//	/**
//	 * Cash payment can only proceed when the cashier selected the cash
//	 * payment mode.
//	 */
//	@SuppressWarnings("serial")
//	private static final Set<CashDeskState> START_CASH_PAYMENT_STATES =new HashSet<CashDeskState>() {{
//		add(CashDeskState.EXPECTING_PAYMENT);
//	}};
//
//	/**
//	 * In cash payment mode, sale is finished when it has been paid for
//	 * and the cash box has been closed.
//	 */
//	@SuppressWarnings("serial")
//	private static final Set<CashDeskState> FINISH_CASH_PAYMENT_STATES =new HashSet<CashDeskState>() {{
//		add(CashDeskState.PAYING_BY_CASH);
//	}};
//
//	/**
//	 * Credit card payment can be made only when the cashier selected the credit
//	 * card payment mode or when rescanning the card.
//	 */
//	@SuppressWarnings("serial")
//	private static final Set<CashDeskState> START_CREADIT_CARD_PAYMENT_STATES =new HashSet<CashDeskState>() {{
//		add(CashDeskState.EXPECTING_CARD_INFO);
//		add(CashDeskState.PAYING_BY_CREDIT_CARD);
//	}};
//	
//	/**
//	 * In credit card payment mode, sale is finished only when we have the
//	 * credit card info.
//	 */
//	@SuppressWarnings("serial")
//	private static final Set<CashDeskState> FINISH_CREDIT_CARD_PAYMENT_STATES = new HashSet<CashDeskState>() {{
//		add(CashDeskState.PAYING_BY_CREDIT_CARD);
//	}};
//	
//
//
//	
//
//	@Override
//	public void startSale() {
//		if(this.actionInStateAvailable(START_SALE_STATES)) {
//			this.resetSale();
//			state = CashDeskState.EXPECTING_ITEMS;
//		}
//	}
//	
//	private void resetSale() {
//		if(saleProducts != null) {
//			for (Pair<Integer, StockItem> saleStat : saleProducts.values()) {
//				storageOrg.incrementInventory(saleStat.getRight().getId(), saleStat.getLeft());
//			}
//			saleProducts.clear();
//		}else {
//			saleProducts = new HashMap<Long, Pair<Integer, StockItem>>();
//		}
//		runningTotal = 0.0;	
//		printer.resetPrinterOutput();
//		}
//
//	@Override
//	public void addDigitToBarcode(char digit) {
//		if(this.actionInStateAvailable(ADD_ITEM_TO_SALE_STATES)){
//			cashBox.addToDigit(digit);
//			display.addToDisplayLine(digit);
//		}
//	}
//
//	@Override
//	public void removeLastDigitFromBarcode() {
//		if(this.actionInStateAvailable(ADD_ITEM_TO_SALE_STATES)){
//			cashBox.removeLastDigit();
//			display.removeLastDigit();
//		}
//		
//	}
//
//	@Override
//	public void clearBarcode() {
//		if(this.actionInStateAvailable(ADD_ITEM_TO_SALE_STATES)){
//			cashBox.resetBarcode();
//			display.resetDisplayLine();
//		}
//	}
//
//	@Override
//	public void submitBarcode() {
//		if(this.actionInStateAvailable(ADD_ITEM_TO_SALE_STATES)){
//			cashBox.submitBarcode();
//		}
//	}
//	
//	@Override
//	public void submitBarcode(String id) {
//		if(this.actionInStateAvailable(ADD_ITEM_TO_SALE_STATES)){
//			scanner.submitBarcode(id);
//		}
//	}
//
//	@Override
//	public String getDisplayOutput() {
//		return display.getDisplayLine();
//	}
//
//	@Override
//	public String[] getPrinterOutput() {
//		return printer.getPrinterOutput();
//	}
//	
//	@Override
//	public void finishSale() {
//		cashBox.pressButtonFinischSale();
//	}
//	
//	public void finischSaleButtompressed() {
//		if(actionInStateAvailable(FINISH_SALES_STATES)) {
//			display.addDisplayLine("TO Pay: " + runningTotal);
//			state = CashDeskState.EXPECTING_PAYMENT;
//		}
//	}
//
//	@Override
//	public void setCashPayment() {
//		cashBox.selectCashPayment();
//	}
//	
//	public void setCashPaymentButtonPressed() {
//		if(actionInStateAvailable(START_CASH_PAYMENT_STATES) && expressLight.getExpressLight() == ExpressLightStates.Express_LIGHT_BLACK) {
//			state = CashDeskState.PAYING_BY_CASH;
//		}
//	}
//
//	@Override
//	public boolean enterCashPaymentAmount(double amount) {
//		if(actionInStateAvailable(FINISH_CASH_PAYMENT_STATES)) {
//			if(amount > runningTotal) {
//				printer.addPrinterOutput("Paid " + amount);
//				printer.addPrinterOutput("Return: " + (amount - runningTotal));
//				state = CashDeskState.PAID_BY_CASH;
//				endSaleProcess();
//				return true;
//			} else {
//				runningTotal -= amount;
//				return false;
//			}
//		}
//		return false;
//	}
//
//	@Override
//	public void setCardPayment() {
//		cashBox.selectCardPayment();
//	}
//	
//	public void setCardPaymentButtomPressed(){
//		if(actionInStateAvailable(START_CREADIT_CARD_PAYMENT_STATES)) {
//			state = CashDeskState.PAYING_BY_CREDIT_CARD; 
//		}		
//	}
//
//	@Override
//	public void enterBankinformation(String pin, String accountNumber) {
//		if(actionInStateAvailable(FINISH_CREDIT_CARD_PAYMENT_STATES)) {
//			// TODO: Validate bank information, payment amount etc.
//		
//			//end of sale process, reset products
//			state = CashDeskState.PAID_BY_CREDIT_CARD;
//			endSaleProcess();
//		}
//	}
//
//	public void barcodeScanned(long Barcode) {
//		//get item from repo, reduce storage amount, add to list
//		display.addDisplayLine("Item Scanned " + Barcode);
//		long productId = BarcodeToStockItemId(Barcode);
//		if(saleProducts.containsKey(Barcode)) {
//			if(saleProducts.get(Barcode).getRight().getAmount() > saleProducts.get(Barcode).getLeft()) {
//				storageOrg.reduceInventory(productId, 1);
//				saleProducts.get(Barcode).setLeft(saleProducts.get(Barcode).getLeft() +1);
//				updateAmount(saleProducts.get(Barcode).getRight().getSalesPrice());
//			} else {
//				// TODO what if not enought products unavailable
//				return;
//			}
//		} else { 
//			if(storageOrg.containStockItem(productId)) {
//				if(storageOrg.reduceInventory(productId, 1)) {
//					saleProducts.put(Barcode, new Pair<Integer, StockItem>(1, storageOrg.getItem(productId)));
//					updateAmount(saleProducts.get(Barcode).getRight().getSalesPrice());
//			} else {
//					// TODO what if not enought products unavailable
//					return;
//					}
//			} else {
//				// TODO what if barcode unavailable
//				return;
//			}
//		}
//		printer.addPrinterOutput(display.getDisplayLine());
//		display.resetDisplayLine();
//	}
//	
//	private long BarcodeToStockItemId(long barcode) {
//		return storageOrg.getIDByBarcode(barcode);
//	}
//
//	private void updateAmount(double amount) {
//		this.runningTotal += amount;
//	}
//	
//	private boolean actionInStateAvailable(Set<CashDeskState> stateSet) {
//		return stateSet.contains(this.state);
//	}
//	
//	private void endSaleProcess() {
//		addProductHistory();
//		for (Pair<Integer, StockItem> itemPair : saleProducts.values()) {
//			checkAmount(itemPair.getRight());
//		}
//		saleProducts.clear();	
//		updateExpressLight();
//	}
//	
//	private void checkAmount(StockItem storageProduct) {
//		if(storageProduct.getAmount() == storageProduct.getMinStock()) {
//			storeAdminManager.runningOutOfItem(storageProduct.getProductId());
//		}
//	}
//
//	@Override
//	public void setExpressLight(boolean expressLightValue) {
//		expressLight.updateExpressLight(expressLightValue);
//	}
//
//	@Override
//	public ExpressLightStates getExpressLight() {
//		return expressLight.getExpressLight();
//	}
//	
//	private void updateExpressLight() {
//		expressLight.updateExpressLight(checkNewExpressLightState());
//	}
//	
//	private void addProductHistory() {
//		salesAmount[nextsalesAmountIndes] = saleProducts.size();
//		nextsalesAmountIndes = (nextsalesAmountIndes + 1) % expressModeRange;
//	}
//	
//	private boolean checkNewExpressLightState() {
//		int fullfillLimits = 0;
//		int salesRegistrated = 0;
//		for (int salesSize : salesAmount) {
//			if(salesSize >= 0) {
//				salesRegistrated ++;
//				if(salesSize < expressModeLimit) {
//					fullfillLimits ++;
//				}
//			}
//		}
//		if(salesRegistrated > 3)
//			return (fullfillLimits > (expressModeRange /2)); 
//		return false;
//	}
	
	
}	