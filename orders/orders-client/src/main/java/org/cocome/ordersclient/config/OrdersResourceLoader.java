package org.cocome.ordersclient.config;


import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class OrdersResourceLoader {
	
	private final static String filename = "OrdersConfig.properties";
	
	public static String loadResource(String key)  {
		
		Properties properties = new Properties();

		 
			//Config.properties
		
			InputStream stream = OrdersConfig.class.getClassLoader().getResourceAsStream(filename);
			try {
				properties.load(stream);
			} catch (IOException e) {
				return e.getMessage();
			} 
		

	   

	    return properties.getProperty(key); 
			
		
	}

}
