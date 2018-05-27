package org.cocome.ordersservice.microservice;

import java.sql.Date;
import java.util.Collection;

import javax.ejb.EJB;
import javax.persistence.criteria.Order;

import org.cocome.ordersservice.domain.OrderEntry;
import org.cocome.ordersservice.domain.ProductOrder;
import org.cocome.ordersservice.repository.ProductOrderRepository;


public class OrderManager implements OrderManagement{
	@EJB
	private ProductOrderRepository orderRepository;
	
	private OrderEntryManagement oEntryManager = new OrderEntryManager();
	
	
	
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
	public ProductOrder createProductOrder(Date orderingDate, Collection<OrderEntry> products, long storeId) {
		ProductOrder pOrder = new ProductOrder();
		
		pOrder.setOrderingDate(orderingDate);
		
		long id = orderRepository.create(pOrder);
		pOrder = orderRepository.find(id);
		
		for (OrderEntry oEntry: products) {
			oEntryManager.setOrder(oEntry, pOrder);
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
	public void addOrderEntry(OrderEntry oEntry, long orderId) {
		ProductOrder pOrder = orderRepository.find(orderId);
		
		oEntryManager.setOrder(oEntry, pOrder);
		
		pOrder.addToOrder(oEntry);;
		orderRepository.update(pOrder);
	}

	@Override
	public void removeOrderEntry(long oEntryId, long orderId) {
		ProductOrder pOrder = orderRepository.find(orderId);
		
		pOrder.removeFromOrder(oEntryManager.getOEntry(oEntryId));
		oEntryManager.deleteOrderEntry(oEntryId);
		orderRepository.update(pOrder);
		
		
	}

	@Override
	public void removeOrder(long orderId) {
		Collection<OrderEntry> oEntries = oEntryManager.all();
		
		for (OrderEntry orderEntry : oEntries) {
			if(orderEntry.getOrder().getId() == orderId)
				oEntryManager.deleteOrderEntry(orderEntry.getId());
		}
		
		orderRepository.delete(orderId);
	}

}