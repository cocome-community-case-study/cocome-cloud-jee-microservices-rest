package org.cocome.productsclient.config;

import java.net.URI;

public final class Config {
	private final static URI baseUri = URI.create("http://localhost:8980/productsmicroservice/rest");
	
	private Config() {}
	
	public static URI getBaseUri() {
		return baseUri;
	}
}
