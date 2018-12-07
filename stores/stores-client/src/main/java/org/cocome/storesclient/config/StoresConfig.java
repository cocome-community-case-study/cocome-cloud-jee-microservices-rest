package org.cocome.storesclient.config;

import java.net.URI;

public final class StoresConfig {
	
	private final static URI baseUri = URI.create(StoresResourceLoader.loadResource("org.cocome.storesservice"));
	private StoresConfig() {}
	
	public static URI getBaseUri() {
		
		return baseUri;
		
	}
}
