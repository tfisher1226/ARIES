package org.aries.client;


public interface Delegate {

	public void initialize() throws Exception;
	
	public void reset() throws Exception;

	public void close() throws Exception;

}
