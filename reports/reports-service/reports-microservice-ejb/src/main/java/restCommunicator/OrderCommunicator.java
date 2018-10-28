package restCommunicator;

import java.util.Collection;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

import org.cocome.ordersclient.domain.ProductOrder;

public class OrderCommunicator {
	
	
	public static Collection<ProductOrder> getOrderReport(long storeId) {
		Client client = ClientBuilder.newClient();
		String targetString = "http://localhost:8780/storesmicroservice/rest/product-orders/"+Long.toString(storeId)+"/order-entries";
		Collection<ProductOrder> response = (Collection<ProductOrder>) client.target(targetString).request(MediaType.APPLICATION_XML).get();
		return response;
	}
}
