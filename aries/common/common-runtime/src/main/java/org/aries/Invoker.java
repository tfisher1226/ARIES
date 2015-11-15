package org.aries;

import java.io.Serializable;


public interface Invoker {

	public String getServiceId();
	
	public String getServiceUrl();
	
	//public void initialize() throws Exception;
	
	public void send(Serializable request) throws Exception;
	
	public <T extends Serializable> T invoke(Serializable request) throws Exception;	

}
