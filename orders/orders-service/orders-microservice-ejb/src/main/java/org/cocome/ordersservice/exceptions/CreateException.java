package org.cocome.ordersservice.exceptions;

/**
 * Thrown if Entity cannot be created
 * @author Niko Benkler
 * @author Robert Heinrich
 *
 */
public class CreateException extends Exception{

	private static final long serialVersionUID = 2888055053172446301L;

	public CreateException(String message) {
		super(message);
	}
}
