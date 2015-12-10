package org.aries.rmi;

import java.util.concurrent.Future;

import org.aries.Assert;
import org.aries.Handler;
import org.aries.HandlerCompleteException;
import org.aries.message.Message;
import org.aries.message.MessageReceiver;
import org.aries.util.IdGenerator;
import org.aries.util.concurrent.FutureResult;


public class RMIMessageReceiver<T> implements MessageReceiver<T> {

	private RMIEndpointContext context;

	
    public RMIMessageReceiver(RMIEndpointContext context) {
    	this.context = context;
    }

	@Override
	public void initialize() {
		context.initialize();
	}

	@Override
	public void start() {
		//NO-OP for RMI
	}

	
	@Override
	public Message receive(long timeout) {
		String xml = context.getClient().receive(timeout);
		Message mesage = context.fromXML(xml);
		return mesage;
	}
	
	@Override
	public Message receive(String correlationId, long timeout) {
		String xml = context.getClient().receive(correlationId, timeout);
		Message mesage = context.fromXML(xml);
		return mesage;
	}

	@Override
	public Future<Message> receive() {
		FutureResult<Message> future = new FutureResult<Message>();
		Handler<String> delegator = createReceiver(future);
		context.getClient().receive(delegator);
		return future;
	}
	
	@Override
	public Future<Message> receive(String correlationId) {
		FutureResult<Message> future = new FutureResult<Message>();
		Handler<String> delegator = createReceiver(future);
		context.getClient().receive(correlationId, delegator);
		return future;
	}
	
	protected Handler<String> createReceiver(final FutureResult<Message> future) {
		return new Handler<String>() {
			public void handle(String xml) {
				try {
					Message message = context.fromXML(xml);
					Assert.notNull(message, "Message is null");
					future.set(message);
				} catch (Exception e) {
					future.setException(e);
				}
			}
		};
	}
	

	@Override
	public Future<Integer> receive(Handler<T> handler) {
		String correlationId = IdGenerator.createId();
		Future<Integer> future = receive(correlationId, handler);
		return future;
	}
	
	@Override
	public Future<Integer> receive(String correlationId, Handler<T> handler) {
		FutureResult<Integer> future = new FutureResult<Integer>();
		Handler<String> delegator = createDelegator(future, handler);
		context.getClient().receive(correlationId, delegator);
		return future;
	}

	protected Handler<String> createDelegator(final FutureResult<Integer> future, final Handler<T> handler) {
		return new Handler<String>() {
			private int count = 0;
			
			public void handle(String xml) {
				count++;
				try {
					Message message = context.fromXML(xml);
					T response = message.getPart("response");
					Assert.notNull(response, "Response is null");
					handler.handle(response);
				} catch (HandlerCompleteException e) {
					future.set(count);
				} catch (Exception e) {
					future.setException(e);
				}
			}
		};
	}
	
	//loop:
	//poll for results
	//check for exception --> throw it
	//check for result --> pass to handler
	//check for timeout
	//end
	//@Override
	public int poll(long timeout, Handler<T> handler) {
		int responseCount = 0;

		long messageTimeout = 1000;
		long receiveTimeout = timeout;
		long startTime = System.currentTimeMillis();

		while (!Thread.currentThread().isInterrupted()) {
			try {
				//timed out yet?
				long currentTime = System.currentTimeMillis();
				if (currentTime - startTime > receiveTimeout)
					break;

				//System.out.println(">>> Attempting to receive...");
				
				String xml = context.getClient().receive(messageTimeout);
				if (Thread.currentThread().isInterrupted())
					break;
				if (xml == null) {
					continue;
				}

				Message message = context.fromXML(xml);
				T response = message.getPart("response");
				handler.handle(response);
				responseCount++;

			} catch (Throwable e) {
				e.printStackTrace();
				break;
			}

			if (Thread.currentThread().isInterrupted())
				break;
		}

		return responseCount;
	}

	public void close() throws Exception {
		context.shutdown();
	}
	
}

