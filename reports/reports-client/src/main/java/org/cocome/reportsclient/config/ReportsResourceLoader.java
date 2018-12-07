package org.cocome.reportsclient.config;


import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ReportsResourceLoader {
	
	private final static String filename = "ReportsConfig.properties";
	
	public static String loadResource(String key)  {
		
		Properties properties = new Properties();

		 
			//Config.properties
		
			InputStream stream = ReportsConfig.class.getClassLoader().getResourceAsStream(filename);
			try {
				properties.load(stream);
			} catch (IOException e) {
				return e.getMessage();
			} 
		

	   

	    return properties.getProperty(key); 
			
		
	}

}
