package org.cocome.ordersclient.config;

import java.net.URI;

public final class Config {
	private final static URI baseUri = URI.create("http://localhost:8780/ordersmicroservice/rest");
	
	private Config() {}
	
	public static URI getBaseUri() {
		return baseUri;
	}
}
