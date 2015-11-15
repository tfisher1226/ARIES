package org.aries.tx.service;

import java.io.Serializable;


public interface Listener {

	//public Message receive(Message message) throws Exception;

	public <T extends Serializable> T invoke(Serializable payload, String correlationId, long timeout) throws Exception;

}
