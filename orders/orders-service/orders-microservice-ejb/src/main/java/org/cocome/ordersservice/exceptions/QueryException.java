package org.cocome.ordersservice.exceptions;

/**
 * Thrown if an error occurs during any query (but create)
 * @author Niko Benkler
 * @author Robert Heinrich
 *
 */
public class QueryException extends Exception {
	
	private static final long serialVersionUID = -7115138404245665205L;

	public QueryException(String message) {
		super(message);
	}
}
