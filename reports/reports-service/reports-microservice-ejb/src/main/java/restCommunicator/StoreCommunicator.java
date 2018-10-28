package restCommunicator;

import java.util.Collection;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

import org.cocome.ordersclient.domain.ProductOrder;
import org.cocome.storesclient.domain.StockItem;

public class StoreCommunicator {
	
	public static Collection<StockItem> getStoreReport(long storeId) {
		Client client = ClientBuilder.newClient();
		String targetString = "http://localhost:8880/storesmicroservice/rest/stores/"+Long.toString(storeId)+"/stock-items";
		Collection<StockItem> response = (Collection<StockItem>) client.target(targetString).request(MediaType.APPLICATION_XML).get();
		return response;
	}
}
