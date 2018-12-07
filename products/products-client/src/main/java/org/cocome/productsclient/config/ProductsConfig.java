package org.cocome.productsclient.config;

import java.net.URI;

public final class ProductsConfig {
	
	private final static URI baseUri = URI.create(ProductsResourceLoader.loadResource("org.cocome.productsservice"));
	private ProductsConfig() {}
	
	public static URI getBaseUri() {
		
		return baseUri;
		
	}
}
