package org.aries;

import java.io.Serializable;


public interface Processor<Input extends Serializable, Output extends Serializable> {

	public Output process(Input object);
	
}
