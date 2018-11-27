package org.cocome.storesservice.util;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.faces.context.FacesContext;
import javax.validation.constraints.NotNull;

public class Messages {
	public static String getLocalizedMessage(@NotNull String key) {
		FacesContext ctx = FacesContext.getCurrentInstance();
		Locale locale = ctx.getViewRoot().getLocale();

		ResourceBundle strings = ResourceBundle.getBundle("cocome.store.Strings", locale);

		return strings.getString(key);
	}
}
