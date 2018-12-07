package org.cocome.productsclient.config;


import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ProductsResourceLoader {
	
	private final static String filename = "ProductsConfig.properties";
	
	public static String loadResource(String key)  {
		
		Properties properties = new Properties();

		 
			//Config.properties
		
			InputStream stream = ProductsConfig.class.getClassLoader().getResourceAsStream(filename);
			try {
				properties.load(stream);
			} catch (IOException e) {
				return e.getMessage();
			} 
		

	   

	    return properties.getProperty(key); 
			
		
	}

}
