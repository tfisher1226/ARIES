package org.aries.exception;


public class BeanNotFoundException extends RuntimeException {

	
	public BeanNotFoundException() {
		//nothing for now
	}

	public BeanNotFoundException(String message) {
		super(message);
	}

}
