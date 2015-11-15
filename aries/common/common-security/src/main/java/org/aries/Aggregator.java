package org.aries;


public interface Aggregator<Any> extends Handler<Any> {

	public Any getResult();
	
}
