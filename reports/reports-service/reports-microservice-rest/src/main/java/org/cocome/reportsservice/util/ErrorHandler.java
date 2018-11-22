package org.cocome.reportsservice.util;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

/**
 * This class beautifies the HTTP Error Code message which is displayed in case
 * an error occurs. <br>
 * The error.xhtml uses the information
 * 
 * @author Niko Benkler
 * @author Robert Heinrich
 *
 */
@ManagedBean
@RequestScoped
public class ErrorHandler {

	public String getStatusCode() {
		String val = String.valueOf((Integer) FacesContext.getCurrentInstance().getExternalContext().getRequestMap()
				.get("javax.servlet.error.status_code"));
		return val;
	}

	public String getMessage() {
		String val = (String) FacesContext.getCurrentInstance().getExternalContext().getRequestMap()
				.get("javax.servlet.error.message");
		return val;
	}

	public String getExceptionType() {
		String val = FacesContext.getCurrentInstance().getExternalContext().getRequestMap()
				.get("javax.servlet.error.exception_type").toString();
		return val;
	}

	public String getException() {
		String val = (String) ((Exception) FacesContext.getCurrentInstance().getExternalContext().getRequestMap()
				.get("javax.servlet.error.exception")).toString();
		return val;
	}

	public String getRequestURI() {
		return (String) FacesContext.getCurrentInstance().getExternalContext().getRequestMap()
				.get("javax.servlet.error.request_uri");
	}

	public String getServletName() {
		return (String) FacesContext.getCurrentInstance().getExternalContext().getRequestMap()
				.get("javax.servlet.error.servlet_name");
	}

}
