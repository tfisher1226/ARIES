package org.aries.bean;

import java.io.Serializable;

import org.aries.Handler;


public interface Proxy<T> {

	public T getDelegate();
	
	public String getServiceId();
	
	public String getServiceUrl();
	
	public void initialize() throws Exception;
	
	public void send(Serializable message) throws Exception;
	
	//public void send(Serializable message, String correlationId, String transactionId) throws Exception;
	
	public <T extends Serializable> T invoke(Serializable request) throws Exception;
	
	public <T extends Serializable> void addResponseHandler(String responseId, Handler<T> handler);
	
}
