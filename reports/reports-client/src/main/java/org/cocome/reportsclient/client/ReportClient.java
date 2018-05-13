package org.cocome.reportsclient.client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.cocome.reportsclient.config.Config;

public class ReportClient {
	private WebTarget webTarget;
	
	public ReportClient() {
		Client client = ClientBuilder.newClient();
		this.webTarget = client.target(Config.getBaseUri());
	}
	
	public String generateEnterpriseDeliveryReport(long enterpriseId) {
		// JAX-RS seems to not support POST requests without body content,
		// so we send text/plain with an empty string.
		Response response = this.webTarget.path("reports")
				.queryParam("type", "enterprise-delivery")
				.queryParam("id", enterpriseId)
				.request()
				.accept(MediaType.TEXT_HTML_TYPE)
				.post(Entity.entity("", MediaType.TEXT_PLAIN_TYPE));
		return response.readEntity(String.class);
	}
	
	public String generateStoreStockReport(long storeId) {
		// JAX-RS seems to not support POST requests without body content,
				// so we send text/plain with an empty string.
				Response response = this.webTarget.path("reports")
						.queryParam("type", "stock-report")
						.queryParam("id", storeId)
						.request()
						.accept(MediaType.TEXT_HTML_TYPE)
						.post(Entity.entity("", MediaType.TEXT_PLAIN_TYPE));
				return response.readEntity(String.class);
	}
	
	public String generateEnterpriseStockReport(long enterpriseId) {
		// JAX-RS seems to not support POST requests without body content,
				// so we send text/plain with an empty string.
				Response response = this.webTarget.path("reports")
						.queryParam("type", "enterprise-stock")
						.queryParam("id", enterpriseId)
						.request()
						.accept(MediaType.TEXT_HTML_TYPE)
						.post(Entity.entity("", MediaType.TEXT_PLAIN_TYPE));
				return response.readEntity(String.class);
	}
}
