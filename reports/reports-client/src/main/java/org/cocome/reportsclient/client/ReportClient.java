package org.cocome.reportsclient.client;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.cocome.reportsclient.config.Config;
import org.cocome.reportsclient.exceptions.ReportsRestException;

public class ReportClient {
	private WebTarget webTarget;

	public ReportClient() {
		Client client = ClientBuilder.newClient();
		this.webTarget = client.target(Config.getBaseUri());
	}

	public String generateEnterpriseDeliveryReport(long enterpriseId) throws ReportsRestException {
		try {
			Response response = this.webTarget.path("reports").queryParam("type", "enterprise-delivery")
					.queryParam("id", enterpriseId).request().accept(MediaType.TEXT_HTML_TYPE)
					.post(Entity.entity("", MediaType.TEXT_PLAIN_TYPE));
			return response.readEntity(String.class);
		} catch (NotFoundException e) {
			throw new ReportsRestException(e.getResponse().readEntity(String.class));
		} catch (ProcessingException e) {
			throw new ReportsRestException("Connection to reportsservice refused!");
		}

	}

	public String generateStoreStockReport(long storeId) throws ReportsRestException {
		try {
			Response response = this.webTarget.path("reports").queryParam("type", "stock-report")
					.queryParam("id", storeId).request().accept(MediaType.TEXT_HTML_TYPE)
					.post(Entity.entity("", MediaType.TEXT_PLAIN_TYPE));
			return response.readEntity(String.class);
		} catch (NotFoundException e) {
			throw new ReportsRestException(e.getResponse().readEntity(String.class));
		} catch (ProcessingException e) {
			throw new ReportsRestException("Connection to reportsservice refused!");
		}

	}

	public String generateEnterpriseStockReport(long enterpriseId) throws ReportsRestException {
		try {
			Response response = this.webTarget.path("reports").queryParam("type", "enterprise-stock")
					.queryParam("id", enterpriseId).request().accept(MediaType.TEXT_HTML_TYPE)
					.post(Entity.entity("", MediaType.TEXT_PLAIN_TYPE));
			return response.readEntity(String.class);
		} catch (NotFoundException e) {
			throw new ReportsRestException(e.getResponse().readEntity(String.class));
		} catch (ProcessingException e) {
			throw new ReportsRestException("Connection to reportsservice refused!");
		}

	}
}
