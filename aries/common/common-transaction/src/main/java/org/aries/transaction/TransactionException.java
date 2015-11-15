package org.aries.transaction;

import java.io.Serializable;


@SuppressWarnings("serial")
public class TransactionException extends Exception implements Serializable {

	public TransactionException() {
		//nothing for now
	}

	public TransactionException(String message) {
		super(message);
	}

}
