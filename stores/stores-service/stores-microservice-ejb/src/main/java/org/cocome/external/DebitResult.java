

package org.cocome.external;

/**
 * Possible return values for credit card operation
 * @author Niko Benkler
 * @author Robert Heinrich
 *
 */
public enum DebitResult {
	OK,
	INSUFFICIENT_BALANCE,
	INVALID_TRANSACTION_ID
}
