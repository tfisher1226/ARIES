package org.aries.tx.exception;

/**
 * Thrown if the state of the transaction is incompatible with the
 * operation attempted. For example, asking the transaction to rollback
 * if it is already committing.
 */
@SuppressWarnings("serial")
public class WrongStateException extends Exception {

	public WrongStateException() {
		super();
	}

	public WrongStateException(String message) {
		super(message);
	}

}

