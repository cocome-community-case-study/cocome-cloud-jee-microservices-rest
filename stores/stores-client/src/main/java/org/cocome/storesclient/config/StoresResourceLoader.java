package org.cocome.storesclient.config;


import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class StoresResourceLoader {
	
	private final static String filename = "StoreConfig.properties";
	
	public static String loadResource(String key)  {
		
		Properties properties = new Properties();

		 
			//Config.properties
		
			InputStream stream = StoresConfig.class.getClassLoader().getResourceAsStream(filename);
			try {
				properties.load(stream);
			} catch (IOException e) {
				return e.getMessage();
			} 
		

	   

	    return properties.getProperty(key); 
			
		
	}

}
