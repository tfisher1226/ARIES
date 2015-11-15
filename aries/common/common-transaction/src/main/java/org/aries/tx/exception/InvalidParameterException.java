package org.aries.tx.exception;


@SuppressWarnings("serial")
public class InvalidParameterException extends Exception {
	
    public InvalidParameterException() {
    }

    public InvalidParameterException(String message) {
        super(message);
    }
    
}
