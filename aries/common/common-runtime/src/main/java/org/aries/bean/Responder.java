package org.aries.bean;

import java.io.Serializable;


public interface Responder {

	public void sendResponse(Serializable message);
	
	public void sendResponse(Serializable message, String correlationId, String transactionId);
	
}
