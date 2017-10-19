package org.cocome.reportsclient.config;

import java.net.URI;

public final class Config {
	private final static URI baseUri = URI.create("http://localhost:8680/reportsmicroservice/rest");
	
	private Config() {}
	
	public static URI getBaseUri() {
		return baseUri;
	}
}
