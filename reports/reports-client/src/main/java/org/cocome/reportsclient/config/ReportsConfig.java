package org.cocome.reportsclient.config;

import java.net.URI;

public final class ReportsConfig {
	
	private final static URI baseUri = URI.create(ReportsResourceLoader.loadResource("org.cocome.reportsservice"));
	private ReportsConfig() {}
	
	public static URI getBaseUri() {
		
		return baseUri;
		
	}
}
