package org.aries.ejb;

import java.io.Serializable;
import java.util.concurrent.Future;

import org.aries.Handler;
import org.aries.client.Client;


public interface EJBClient extends Client {

	public void initialize();

	//public void send(T message);

	//public void send(T message, String correlationId);

	//public T receive(long timeout);

	//public T receive(String correlationId, long timeout);

	//public Future<T> receive(Handler<T> handler);

	//public Future<T> receive(String correlationId, Handler<T> handler);

	public <T extends Serializable> T invoke(Serializable request, String correlationId, long timeout);

	public <T extends Serializable> Future<T> invoke(Serializable request, String correlationId, Handler<T> handler);

}
