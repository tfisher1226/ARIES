package org.aries.client;


public interface Endpoint {

	public void initialize() throws Exception;
	
	public void reset() throws Exception;

	public void close() throws Exception;

}
