package org.cocome.external;

import javax.ejb.Local;
/**
 * Simple mockup for a bank server
 * @author Niko Benkler
 * @author Robert Heinrich
 *
 */
@Local
public interface IBankLocal {

	/**
	 * Validates and authenticates credit card information using the specified
	 * PIN and returns a transaction ID to use for operations on the card.
	 * 
	 * @param cardInfo
	 * @param pin
	 * @return
	 *         transaction ID associated with the (valid and authenticated)
	 *         creit card
	 */
	public TransactionID validateCard(String cardInfo, int pin);

	/**
	 * Charges the credit card associated with the given transaction ID.
	 * <p>
	 * TODO It would be nice to make money participate in the operation :-)
	 * 
	 * @param tid
	 *            the transaction ID
	 * @return
	 *         result of the debit operation
	 * 
	 */
	public DebitResult debitCard(TransactionID tid);

}