package microservice;

import java.util.Collection;

import org.cocome.ordersservice.domain.OrderEntry;
import org.cocome.ordersservice.domain.ProductOrder;

public interface OrderEntryManagement {
	/**
	 * creates db entry for new order entry
	 * 
	 * @param amount
	 * @param order null if no order given
	 * @return the created orderEntry
	 */
	public OrderEntry createOrderEntry(long productId, long amount, ProductOrder order);
	
	public Collection<OrderEntry> all();
	
	/**
	 * returns order entry with given id
	 * 
	 * @param oEntryId
	 * @return
	 */
	public OrderEntry getOEntry(long oEntryId);
	
	/**
	 * sets Order to orderEntry
	 * 
	 * @param oEntryId
	 * @param pOrder
	 */
	public void setOrder(OrderEntry oEntry, ProductOrder pOrder);
	
	/**
	 * updates new order Entry
	 * 
	 * @param oEntry
	 */
	public void updateOrderEntry(OrderEntry oEntry);
	
	/**
	 * removes order Entry
	 * 
	 * @param oEntryId
	 */
	public void deleteOrderEntry(long oEntryId);
	
}
