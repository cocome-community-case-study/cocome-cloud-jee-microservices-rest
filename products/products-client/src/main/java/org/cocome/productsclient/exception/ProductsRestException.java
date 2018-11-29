package org.cocome.productsclient.exception;

/**
 * Exception that is thrown by Rest-Client
 * @author Niko Benkler
 * @author Robert Heinrich
 *
 */
public class ProductsRestException extends Exception  {


	private static final long serialVersionUID = -7500224905593796581L;

	
	public ProductsRestException(String message) {
		super(message);
	}
}
