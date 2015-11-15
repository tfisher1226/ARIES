package org.aries.message;

import java.util.concurrent.Future;

import org.aries.Handler;


public interface MessageReceiver<T> {

	public final long DEFAULT_TIMEOUT = 60000;
	
	public void initialize();

	public void start();

	public Message receive(long timeout);

	public Message receive(String conversationId, long timeout);

	public Future<Message> receive();

	public Future<Message> receive(String conversationId);

	public Future<Integer> receive(Handler<T> handler);

	public Future<Integer> receive(String conversationId, Handler<T> handler);

//	public int poll(long timeout, Handler<T> handler);
	
//	public Future<T> getFuture();
	
}
