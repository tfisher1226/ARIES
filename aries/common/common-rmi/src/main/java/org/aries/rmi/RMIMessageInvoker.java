package org.aries.rmi;

import java.util.concurrent.Future;

import org.aries.Assert;
import org.aries.Handler;
import org.aries.message.Message;
import org.aries.message.MessageInvoker;
import org.aries.util.IdGenerator;
import org.aries.util.concurrent.FutureResult;


public class RMIMessageInvoker<T> implements MessageInvoker<T> {

	private RMIEndpointContext context;

	
    public RMIMessageInvoker(RMIEndpointContext context) {
    	this.context = context;
    }

	@Override
	public void initialize() {
		context.initialize();
	}

	@Override
	public Message invoke(Message request) {
		Message message = invoke(request, MessageInvoker.DEFAULT_TIMEOUT);
		return message;
	}
	
	@Override
	public Message invoke(Message request, long timeout) {
		String correlationId = IdGenerator.createId();
		Message message = invoke(request, correlationId, timeout);
		return message;
	}

	@Override
	public Message invoke(Message request, String correlationId, long timeout) {
		String requestXml = context.toXML(request);
		String responseXml = context.getClient().invoke(requestXml, correlationId, timeout);
		Message message = context.fromXML(responseXml);
		//TODO check for exception and throw it
		return message;
	}

	
	@Override
	public Future<Message> invoke(Message request, Handler<T> handler) {
		String correlationId = IdGenerator.createId();
		Future<Message> future = invoke(request, correlationId, handler);
		return future;
	}

	@Override
	public Future<Message> invoke(Message request, String correlationId, Handler<T> handler) {
		FutureResult<Message> future = new FutureResult<Message>();
		Handler<String> delegator = createDelegator(future, handler);
		String requestXml = context.toXML(request);
		context.getClient().invoke(requestXml, correlationId, delegator);
		//TODO check for exception and throw it
		return future;
	}
	
	protected Handler<String> createDelegator(final FutureResult<Message> future, final Handler<T> handler) {
		return new Handler<String>() {
			public void handle(String xml) {
				try {
					Message message = context.fromXML(xml);
					T response = message.getPart("result");
					Assert.notNull(response, "Response is null");
					handler.handle(response);
					future.set(message);
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
				Message message = context.fromXML(xml);
				if (Thread.currentThread().isInterrupted())
					break;
				if (message == null) {
					continue;
				}

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

