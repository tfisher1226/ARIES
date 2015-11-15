package org.aries.client;

import java.io.Serializable;


public interface Client {

	public String getTransactionId();
	
	public void setTransactionId(String transactionId);
	
	public String getCorrelationId();

	public void setCorrelationId(String correlationId);
	
//	public void send(T message) throws Exception;
//	
//	public void send(T message, String correlationId) throws Exception;
	
	public <T extends Serializable> T invoke(Serializable message) throws Exception;
	
	public <T extends Serializable> T invoke(Serializable message, String correlationId, long timeout) throws Exception;

}
