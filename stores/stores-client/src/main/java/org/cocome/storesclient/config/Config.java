package org.cocome.storesclient.config;

import java.net.URI;

public final class Config {
	private final static URI baseUri = URI.create("http://localhost:8880/storesmicroservice/rest");
	
	private Config() {}
	
	public static URI getBaseUri() {
		return baseUri;
	}
}
