package org.aries.tx.exception;

/**
 * Thrown if the transaction is unknown.
 */
@SuppressWarnings("serial")
public class UnknownTransactionException extends Exception {
    
	public UnknownTransactionException() {
	}

	public UnknownTransactionException(String message) {
		super(message);
	}

}

