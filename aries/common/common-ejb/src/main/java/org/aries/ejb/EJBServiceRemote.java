package org.aries.ejb;

import java.io.Serializable;


public interface EJBServiceRemote {

	//public void send(T message, String correlationId) throws Exception;

	public <T extends Serializable> T invoke(Serializable message, String serviceId, String correlationId) throws Exception;

	//public void invokeAsync(T message, String correlationId) throws Exception;

	//public void addSubscriber(SubscriberDescripter subscriberDescripter) throws Exception;

	//public void removeSubscriber(SubscriberDescripter subscriberDescripter) throws Exception;

	//public void reset() throws Exception;

}
