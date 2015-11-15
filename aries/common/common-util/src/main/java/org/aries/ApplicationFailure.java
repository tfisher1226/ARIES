package org.aries;

import javax.ejb.ApplicationException;


/**
 * <code>ApplicationFailure</code> is a runtime exception thrown from application logic.
 * 
 * This is an unchecked exception that will behave like an ApplicationException 
 * in that "it will NOT result in the throwing bean getting destroyed".
 */
@ApplicationException(rollback = true)
public class ApplicationFailure extends RuntimeException {

	private static final long serialVersionUID = 1L;


	public ApplicationFailure() {
	}
	
	/** 
	 * Constructs a new exception with the given message.
	 * @param message detail on the specific condition that was detected.
	 */
	public ApplicationFailure(String message) {
		super(message);
	}
	
	/** 
	 * Constructs a new exception with the given cause.
	 * @param cause an Exception that represents the cause for this Exception.
	 */
	public ApplicationFailure(Throwable cause) {
		super(cause.getMessage(), cause);
	}
	
	/** 
	 * Constructs a new exception with the given message and cause.
	 * @param message detail on the specific condition that was detected.
	 * @param cause an Exception that represents the cause for this Exception.
	 */
	public ApplicationFailure(String message, Throwable cause) {
		super(message, cause);
	}
	
}
