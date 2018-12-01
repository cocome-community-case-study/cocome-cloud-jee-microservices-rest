/***************************************************************************
 * Copyright 2013 DFG SPP 1593 (http://dfg-spp1593.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ***************************************************************************/

package org.cocome.external;

import javax.ejb.Stateless;

/**
 * Simple bank server implementation
 * @author Niko Benkler
 * @author Robert Heinrich
 *
 */
@Stateless
public class TrivialBankServer implements IBankLocal {

	/**
	 * 
	 */

	// TODO Consider making the credit card details externally configurable
	private static final String CREDIT_CARD_INFO = "1234";

	private static final int CREDIT_CARD_PIN = 7777;

	private static final TransactionID CREDIT_CARD_TXID = new TransactionID(1234L);

	/**
	 * Construct a new bank server.
	 * 
	 */
	public TrivialBankServer() {
		super();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TransactionID validateCard(final String cardInfo, final int cardPin) {
		if (cardInfo.equals(CREDIT_CARD_INFO) && (cardPin == CREDIT_CARD_PIN)) {
			return CREDIT_CARD_TXID;
		} else {
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DebitResult debitCard(final TransactionID id) {
		if(id == null) {
			return DebitResult.INVALID_TRANSACTION_ID;
		}
		
		if (!id.equals(CREDIT_CARD_TXID)) {
			return DebitResult.INVALID_TRANSACTION_ID;
		} else if (true) { 
			return DebitResult.OK;
		} else {
			/*
			 * We do not provide any mechanism to check balance. 
			 */
			return DebitResult.INSUFFICIENT_BALANCE;
		}
	}

}
