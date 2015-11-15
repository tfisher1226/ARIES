package org.aries.tx.exception;

/**
 * Thrown if there is a fault during complete or compensation.
 */
public class FaultedException extends Exception {

	public FaultedException () {
		super();
	}

	public FaultedException(String message) {
		super(message);
	}

}

