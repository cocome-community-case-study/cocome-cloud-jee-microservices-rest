package org.cocome.ordersservice.frontend.viewdata;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;

import org.cocome.ordersservice.domain.ProductOrder;
import org.cocome.productsclient.domain.ProductTO;

public class OrderViewData {

	private long id;
	private long storeId;
	private Date deliveryDate;
	private Date orderingDate;
	private Collection<EntryViewData> entries;

	public OrderViewData(long id, long storeId, Collection<EntryViewData> entries, Date deliveryDate,
			Date orderingDate) {
		this.id = id;
		this.storeId = storeId;
		this.entries = entries;
		this.deliveryDate = deliveryDate;
		this.orderingDate = orderingDate;

	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getStoreId() {
		return storeId;
	}

	public void setStoreId(long storeId) {
		this.storeId = storeId;
	}

	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public Date getOrderingDate() {
		return orderingDate;
	}

	public void setOrderingDate(Date orderingDate) {
		this.orderingDate = orderingDate;
	}

	public Collection<EntryViewData> getEntries() {
		return entries;
	}

	public void setEntries(Collection<EntryViewData> entries) {
		this.entries = entries;
	}

	public static OrderViewData fromOrder(ProductOrder order) {
		return new OrderViewData(order.getId(), order.getStoreId(),
				EntryViewData.fromEntryCollection(order.getOrderEntries()), order.getDeliveryDate(),
				order.getOrderingDate());
	}

	
    public static Collection<OrderViewData> fromOrderCollection(Collection<ProductOrder> orders){
    	Collection<OrderViewData> orderViewDataCollection = new LinkedList<>();
    	
    	for(ProductOrder order : orders) {
    		orderViewDataCollection.add(fromOrder(order));
    	}
    	return orderViewDataCollection;
    	
    }
    
    

	

}
