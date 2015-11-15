package org.aries.tx.exception;


/**
 * Marker interface for exceptions e.g. timeouts, in the client/server communication.
 */
@SuppressWarnings("serial")
public class CommunicationException extends SystemException {

	public CommunicationException() {
	}

	public CommunicationException(String message) {
		super(message);
	}
	
}
