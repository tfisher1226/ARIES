package org.aries;


public interface Processor<Input, Output> {

	public Output process(Input object);
	
}
