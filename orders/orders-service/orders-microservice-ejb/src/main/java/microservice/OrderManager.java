package microservice;

import java.sql.Date;
import java.util.Collection;

import javax.ejb.EJB;
import org.cocome.ordersservice.domain.OrderEntry;
import org.cocome.ordersservice.domain.ProductOrder;
import org.cocome.ordersservice.repository.OrderEntryRepository;
import org.cocome.ordersservice.repository.ProductOrderRepository;


public class OrderManager implements Ordermanagement{
	@EJB
	private ProductOrderRepository orderRepository;
	
	@EJB
	private OrderEntryRepository entryRepository;
	
	
	
	@Override
	public Collection<ProductOrder> getAll() {
		return orderRepository.all();
	}

	@Override
	public ProductOrder findProduktOrder(long orderId) {
		return orderRepository.find(orderId);
	}

	@Override
	public void executeOrder(long orderId, Date date) {
		ProductOrder pOrder = orderRepository.find(orderId);
		pOrder.setDeliveryDate(date);
		orderRepository.update(pOrder);
		
		//TODO side effects at storage
	}

	@Override
	public OrderEntry createOrderEntry(long productId, long amount, ProductOrder order) {
		OrderEntry oEntry = new OrderEntry();
		oEntry.setAmount(amount);
		oEntry.setProductId(productId);
		oEntry.setOrder(order);
		
		long id = entryRepository.create(oEntry);
		oEntry.setId(id);
		
		entryRepository.update(oEntry);
		
		return oEntry;
	}

	@Override
	public ProductOrder createProductOrder(Date orderingDate, Collection<OrderEntry> products, long storeId) {
		ProductOrder pOrder = new ProductOrder();
		
		pOrder.setOrderingDate(orderingDate);
		
		long id = orderRepository.create(pOrder);
		
		pOrder.setId(id);
		
		for (OrderEntry productOrder : products) {
			productOrder.setOrder(pOrder);
			entryRepository.update(productOrder);
		}
		
		pOrder.setOrderEntries(products);
		orderRepository.update(pOrder);
		
		return pOrder;
	}

	@Override
	public void updateProductOrder(long orderId, Date orderingDate, Date deliveryDate, Collection<OrderEntry> products, long storeId) {
		
		ProductOrder productOrder = orderRepository.find(orderId);
		
		productOrder.setOrderingDate(orderingDate);
		productOrder.setStoreId(storeId);
		productOrder.setOrderEntries(products);
		
		if(productOrder.getDeliveryDate() == null) {
			orderRepository.update(productOrder);
			executeOrder(orderId, deliveryDate);
		} else {
			productOrder.setDeliveryDate(deliveryDate);
			orderRepository.update(productOrder);
		}
		
	}

	@Override
	public void addOrderEntry(OrderEntry entry, long orderId) {
		entry.setId(orderId);
		entryRepository.update(entry);
		
		ProductOrder pOrder = orderRepository.find(orderId);
		
		Collection<OrderEntry> oEntries = pOrder.getOrderEntries();
		
		oEntries.add(entry);
		pOrder.setOrderEntries(oEntries);
		orderRepository.update(pOrder);
	}

	@Override
	public void removeOrderEntry(long productId, long orderId) {
		ProductOrder pOrder = orderRepository.find(orderId);
		Collection<OrderEntry> orderCollection = pOrder.getOrderEntries();
		orderCollection.remove(entryRepository.find(productId));
		pOrder.setOrderEntries(orderCollection);
		
		orderRepository.update(pOrder);
		
		entryRepository.delete(productId);
		
	}

	@Override
	public void removeOrder(long orderId) {
		Collection<OrderEntry> oEntries = entryRepository.all();
		
		for (OrderEntry orderEntry : oEntries) {
			if(orderEntry.getOrder().getId() == orderId)
				entryRepository.delete(orderEntry.getId());
		}
		
		orderRepository.delete(orderId);
	}

}
