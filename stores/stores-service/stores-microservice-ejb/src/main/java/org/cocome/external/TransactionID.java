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

import java.io.Serializable;

/**
 * Represents a transaction id issued to validate a credit card PIN.
 * @author Niko Benkler
 * @author Robert Heinrich
 *
 */
public class TransactionID implements Serializable {

	private static final long serialVersionUID = 1275459601912497741L;

	

	private final long id;



	/**
	 * New transaction ID object.
	 * 
	 * @param id
	 */
	public TransactionID(final long id) {
		this.id = id;
	}

	@Override
	public boolean equals(final Object object) {
		if (object instanceof TransactionID) {
			final TransactionID that = (TransactionID) object;
			return this.id == that.id;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return (int) (this.id ^ (this.id >>> 32));
	}

}
