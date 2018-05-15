package microservice;

import java.sql.Date;
import java.util.Collection;

import javax.persistence.criteria.Order;

import org.cocome.ordersservice.domain.OrderEntry;
import org.cocome.ordersservice.domain.ProductOrder;

public interface Ordermanagement {

	/**
	 * Gets all Orders
	 * @return
	 */
	public Collection<ProductOrder> getAll();
	
	/**
	 * gets order with specified id
	 * @param Id
	 * @return
	 */
	public ProductOrder findProduktOrder(long Id);
	
	/**
	 * sets deliver date of specified Ordering and updates products in store
	 * @param produktOrderId
	 * @param date
	 */
	public void executeOrder(long produktOrderId, Date date); 
	
	/**
	 * creates db entry for complete order
	 * 
	 * @param productId
	 * @param amount
	 * @param order ggf nullable 
	 * @return
	 */
	public OrderEntry createOrderEntry(long productId, long amount, ProductOrder order);
	
	/**
	 * creates product order
	 * @param orderingDate
	 * @param products
	 * @param storeId
	 * @return
	 */
	public ProductOrder createProductOrder(Date  orderingDate, Collection<OrderEntry> products, long storeId);
	
	/**
	 * updates product order entry 
	 * @param orderId
	 * @param orderingDate
	 * @param deliveryDate
	 * @param products
	 * @param storeId
	 */
	public void updateProductOrder(long orderId, Date orderingDate, Date deliveryDate, Collection<OrderEntry> products, long storeId);
		
	/**
	 * adds order entry to ProduktOrder
	 * @param entry has to be in database
	 * @param orderId
	 */
	public void addOrderEntry(OrderEntry orderEntry, long produktOrderId);

	/**
	 * removes produktOrder from OrderEntry
	 * @param productOrderId
	 * @param OrderEntryId
	 */
	public void removeOrderEntry(long productOrderId, long OrderEntryId);
	
	/**
	 * removes specified order
	 * @param orderId
	 */
	public void removeOrder(long orderId);
}
