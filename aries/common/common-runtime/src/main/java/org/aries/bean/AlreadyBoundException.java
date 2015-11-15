package org.aries.bean;


public class AlreadyBoundException extends Exception {

	public AlreadyBoundException() {
		//nothing
	}
	
	public AlreadyBoundException(String message) {
		super(message);
	}

}
