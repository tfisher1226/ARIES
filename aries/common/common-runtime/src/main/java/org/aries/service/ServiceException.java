package org.aries.service;


/**
 * Represents an exception that is thrown across the wire.
 * Does not represent an original Exception but contains 1) either the 
 * original exception or 2) information regarding where the original 
 * exception occured.  So in short, this class represents an exception 
 * condition that occured during a client invocation to a remote service.
 *
 */
public class ServiceException extends RuntimeException {

	private static final long serialVersionUID = 1000L;

	
	public ServiceException() {
		//nothing for now
	}
	
	public ServiceException(String message) {
		super(message);
	}
	
	public ServiceException(Throwable cause) {
		super(cause);
	}

	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}

}
