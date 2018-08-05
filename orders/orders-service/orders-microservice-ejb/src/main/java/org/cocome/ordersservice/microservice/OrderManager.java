package org.cocome.ordersservice.microservice;

import java.security.KeyStore.Entry;
import java.sql.Date;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

import javax.ejb.EJB;
import javax.persistence.criteria.Order;
import javax.print.attribute.standard.Sides;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.cocome.ordersservice.domain.OrderEntry;
import org.cocome.ordersservice.domain.ProductOrder;
import org.cocome.ordersservice.repository.ProductOrderRepository;
import org.cocome.storesservice.domain.*;

import com.sun.mail.imap.protocol.Item;



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

	@SuppressWarnings("unchecked")
	@Override
	public void executeOrder(long orderId, Date date) {
		ProductOrder pOrder = orderRepository.find(orderId);
		pOrder.setDeliveryDate(date);
		orderRepository.update(pOrder);
		
		
		Client client = ClientBuilder.newClient();
		String targetString = "http://localhost:8980/storesmicroservice/rest/stores/" + Long.toString(pOrder.getStoreId()) + "/stock-items";
		Collection<StockItem> response = (Collection<StockItem>) client.target(targetString).request(MediaType.APPLICATION_XML).get();		
		
		HashMap<Long, OrderEntry> orderedItems = new HashMap<Long, OrderEntry>();
		for(OrderEntry  entry :pOrder.getOrderEntries()) {
			orderedItems.put(entry.getProductId(), entry);
		}
		
		for(StockItem item : response) {
			if(isOrdered(item.getProductId(), orderedItems)) {
				OrderEntry  entry = orderedItems.remove(item.getId());
				
				item.setAmount(item.getAmount() + entry.getAmount());
				
				targetString = "http://localhost:8980/storesmicroservice/rest/stock-items/"+ Long.toString(item.getId());
				/*Response response2 = */client.target(targetString).request(MediaType.APPLICATION_XML).put(Entity.entity(item, MediaType.APPLICATION_XML));			
				//TODO Response?
				
				if(orderedItems.isEmpty()) {
					break;
				}
			}
		}
		
		//TODO side effects at storage
	}
	private boolean isOrdered(Long itemId, HashMap<Long, OrderEntry> orderedItems) {
		return orderedItems.containsKey(itemId);
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
		

		// TODO side effects on StockItem?
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
