package org.aries.tx.service.ejb;

import java.io.Serializable;


public interface EJBListener {

	//public Message receive(Message message) throws Exception;

	public <T extends Serializable> T invoke(Serializable payload, String correlationId, long timeout);

}
