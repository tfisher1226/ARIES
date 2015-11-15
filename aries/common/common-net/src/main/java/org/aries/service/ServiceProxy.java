package org.aries.service;

import java.util.concurrent.Future;

import org.aries.Handler;
import org.aries.message.Message;


public interface ServiceProxy {
	
	public void dispatch(Message message);
	
	public Message invoke(Message request);

	public Future<Message> invoke(Message request, Handler<Message> handler);

	public Message receive(long messageTimeout);

}
