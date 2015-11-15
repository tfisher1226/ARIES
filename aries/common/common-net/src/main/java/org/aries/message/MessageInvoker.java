package org.aries.message;

import java.util.concurrent.Future;

import org.aries.Handler;


public interface MessageInvoker<T> {

	public final long DEFAULT_TIMEOUT = 60000;

	public void initialize();

	public Message invoke(Message request);

	public Message invoke(Message request, long timeout);

	public Message invoke(Message request, String correlationId, long timeout);

	public Future<Message> invoke(Message request, Handler<T> handler);

	public Future<Message> invoke(Message request, String correlationId, Handler<T> handler);

}
