package microservice;

import java.util.Collection;

import javax.ejb.EJB;

import org.cocome.ordersservice.domain.OrderEntry;
import org.cocome.ordersservice.domain.ProductOrder;
import org.cocome.ordersservice.repository.OrderEntryRepository;

public class OrderEntryManager implements OrderEntryManagement{
	
	@EJB
	private OrderEntryRepository entryRepository;
	
	
	@Override
	public OrderEntry createOrderEntry(long productId, long amount, ProductOrder order) {
		OrderEntry oEntry = new OrderEntry();
		oEntry.setAmount(amount);
		oEntry.setProductId(productId);
		oEntry.setOrder(order);
		
		long id = entryRepository.create(oEntry);
		return entryRepository.find(id);
	}

	@Override
	public void updateOrderEntry(OrderEntry oEntry) {
		entryRepository.update(oEntry);	
	}

	@Override
	public OrderEntry getOEntry(long oEntryId) {
		return entryRepository.find(oEntryId);
	}

	@Override
	public void setOrder(OrderEntry oEntry, ProductOrder pOrder) {
		oEntry.setOrder(pOrder);
		entryRepository.update(oEntry);
	}

	@Override
	public Collection<OrderEntry> all() {
		return entryRepository.all();
	}

	@Override
	public void deleteOrderEntry(long oEntryId) {
		entryRepository.delete(oEntryId);;
	}
}
