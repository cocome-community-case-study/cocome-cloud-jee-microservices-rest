package org.cocome.frontendservice.util;

import java.util.ResourceBundle;


import javax.validation.constraints.NotNull;

public class ResourceLoader {
	public static String getProperty(@NotNull String key) {
		

		ResourceBundle strings = ResourceBundle.getBundle("cocome.frontend.Config");

		return strings.getString(key);
	}
}
