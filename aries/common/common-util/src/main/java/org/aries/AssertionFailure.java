package org.aries;

import javax.ejb.ApplicationException;


/**
 * <code>AssertionFailedException</code> is a runtime exception thrown
 * by some of the methods in <code>Assert</code>.
 * 
 * This is an unchecked exception that will behave like an ApplicationException 
 * in that "it will NOT result in the throwing bean getting destroyed", but
 * that it will also NOT cause transaction rollback thereby allowing this behaviour 
 * to be context specific without interfering with any enclosing transaction.
 */
@ApplicationException(rollback = false)
public class AssertionFailure extends RuntimeException {

	private static final long serialVersionUID = 1L;


	public AssertionFailure() {
	}
	
	/** 
	 * Constructs a new exception with the given message.
	 * @param message detail on the specific condition that was detected.
	 */
	public AssertionFailure(String message) {
		super(message);
	}
	
	/** 
	 * Constructs a new exception with the given cause.
	 * @param cause an Exception that represents the cause for this Exception.
	 */
	public AssertionFailure(Throwable cause) {
		super(cause.getMessage(), cause);
	}
	
	/** 
	 * Constructs a new exception with the given message and cause.
	 * @param message detail on the specific condition that was detected.
	 * @param cause an Exception that represents the cause for this Exception.
	 */
	public AssertionFailure(String message, Throwable cause) {
		super(message, cause);
	}

	
	public boolean equals(Object object) {
		if (object instanceof AssertionFailure) {
			AssertionFailure other = (AssertionFailure) object;
			if (!other.getMessage().equals(getMessage()))
				return false;
			return true;
		}
		return false;
	}
	
}
