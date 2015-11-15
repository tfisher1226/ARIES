package org.aries.runtime.exception;


@SuppressWarnings("serial")
public class BeanNotFoundException extends RuntimeException {

	
	public BeanNotFoundException() {
		//nothing for now
	}

	public BeanNotFoundException(String message) {
		super(message);
	}

	public BeanNotFoundException(Class<?> classObject) {
		super("Bean not found for class: "+classObject.getName());
	}

}
