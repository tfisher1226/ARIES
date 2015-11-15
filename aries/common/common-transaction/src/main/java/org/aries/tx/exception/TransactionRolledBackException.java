package org.aries.tx.exception;

/**
 * Thrown if the transaction rolls back rather than commits.
 */
@SuppressWarnings("serial")
public class TransactionRolledBackException extends Exception {

	public TransactionRolledBackException() {
	}

	public TransactionRolledBackException(String message) {
		super(message);
	}

}

