package org.cocome.ordersclient.config;

import java.net.URI;

public final class OrdersConfig {
	
	private final static URI baseUri = URI.create(OrdersResourceLoader.loadResource("org.cocome.ordersservice"));
	private OrdersConfig() {}
	
	public static URI getBaseUri() {
		
		return baseUri;
		
	}
}
