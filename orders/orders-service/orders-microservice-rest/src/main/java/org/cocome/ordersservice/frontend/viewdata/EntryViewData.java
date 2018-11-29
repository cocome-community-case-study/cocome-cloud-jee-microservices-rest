package org.cocome.ordersservice.frontend.viewdata;

import java.util.Collection;
import java.util.LinkedList;

import org.cocome.ordersservice.domain.OrderEntry;

/**
 * This class is used to differentiate between domain object "OrderEntry" that is
 * used for persistence and the view object "OrderEntry"
 * 
 * @author Niko Benkler
 * @author Robert Heinrich
 *
 */
public class EntryViewData {

	private long id;
	private long amount;
	private long productId;

	public EntryViewData(long id, long amount, long productId) {

		this.id = id;
		this.amount = amount;
		this.productId = productId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}
	
	/**
	 * Generate EntryViewData from OrderEntry Object
	 * @param entry
	 * @return
	 */
	public static EntryViewData fromEntry(OrderEntry entry) {
		return new EntryViewData(entry.getId(), entry.getAmount(), entry.getProductId());
	}
   
	/**
	 * Generate EntryViewData Colection from OrderEntry Collection
	 * @param entry
	 * @return
	 */
	public static Collection<EntryViewData> fromEntryCollection(Collection<OrderEntry> entries) {
		Collection<EntryViewData> entryViewDataCollection = new LinkedList<>();

		for (OrderEntry entry : entries) {
			entryViewDataCollection.add(fromEntry(entry));
		}
		return entryViewDataCollection;
	}

}
