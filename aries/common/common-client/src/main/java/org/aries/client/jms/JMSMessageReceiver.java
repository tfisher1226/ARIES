package org.aries.client.jms;

import java.util.concurrent.Future;

import javax.jms.JMSException;
import javax.jms.MessageListener;
import javax.naming.NamingException;
import javax.xml.bind.JAXBException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.Handler;
import org.aries.message.Message;
import org.aries.message.MessageReceiver;
import org.aries.message.util.MessageUtil;
import org.aries.service.ServiceProxy;
import org.aries.util.IdGenerator;
import org.aries.util.concurrent.FutureResult;


public class JMSMessageReceiver<T> implements MessageReceiver<T> {

	private static Log log = LogFactory.getLog(ServiceProxy.class);

	private JMSEndpointContext context;

	private FutureResult<T> future;

	private Handler<T> handler;

	//option to receive asynch/synch
	private boolean asynchronous;
	

	public JMSMessageReceiver(JMSEndpointContext context, Handler<T> handler) {
		this.future = new FutureResult<T>();
		this.context = context;
		this.handler = handler;
	}

	@Override
	public void initialize() {
		try {
			context.initialize();
			context.getConsumer().initialize();
//			if (asynchronous)
//				context.getConsumer().setMessageListener(createMessageListener());
		} catch (NamingException e) {
			throw new RuntimeException(e);
		} catch (JMSException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void start() {
		try {
			context.start();
		} catch (JMSException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Message receive(long timeout) {
		String correlationId = IdGenerator.createId();
		Message message = receive(correlationId, timeout);
		return message;
	}

	@Override
	public Message receive(String conversationId, long timeout) {
		try {
			context.initialize();
			javax.jms.Message jmsMessage = context.getConsumer().receive(timeout);
			Message message = context.fromJMSMessage(jmsMessage);
			return message;
		} catch (NamingException e) {
			throw new RuntimeException(e);
		} catch (JMSException e) {
			throw new RuntimeException(e);
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
	}

	
	@Override
	public Future<Message> receive() {
		String correlationId = IdGenerator.createId();
		Future<Message> future = receive(correlationId);
		return future;
	}
	
	@Override
	public Future<Message> receive(String correlationId) {
		try {
			context.initialize();
			FutureResult<Message> future = new FutureResult<Message>();
			context.getConsumer().setMessageListener(createMessageListener(future, correlationId));
			return future;
		} catch (NamingException e) {
			throw new RuntimeException(e);
		} catch (JMSException e) {
			throw new RuntimeException(e);
		}
	}

	protected MessageListener createMessageListener(final FutureResult<Message> future, final String correlationId) {
		return new MessageListener() {
			public void onMessage(javax.jms.Message jmsMessage) {
				try {
					System.out.println("***"+jmsMessage);
					Message message = context.fromJMSMessage(jmsMessage);
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
		try {
			context.initialize();
			FutureResult<Integer> future = new FutureResult<Integer>();
			context.getConsumer().setMessageListener(createMessageListener(correlationId, future, handler));
			return future;
		} catch (NamingException e) {
			throw new RuntimeException(e);
		} catch (JMSException e) {
			throw new RuntimeException(e);
		}
	}


//	@Override
//	public Future<T> getFuture() {
//		return future;
//	}

//	public void setMessageListener(MessageListener messageListener) {
//		try {
//			context.getConsumer().setMessageListener(messageListener);
//		} catch (JMSException e) {
//			throw new RuntimeException(e);
//		}		
//	}

//	protected MessageListener createMessageListener() {
//		return createMessageListener(handler);
//	}

	protected MessageListener createMessageListener(final String correlationId, final FutureResult<Integer> future, final Handler<T> handler) {
		return new MessageListener() {
			private int responseCount = 0;

			public void onMessage(javax.jms.Message jmsMessage) {
				//TODO check for correlationId, drop if no match
				try {
					System.out.println("***"+jmsMessage);
					Message message = context.fromJMSMessage(jmsMessage);
					if (message != null) {
						T response = MessageUtil.getPart(message, "response");
						//TODO care about null here?
						responseCount++;
						
						try {
							handler.handle(response);
						} catch (Exception e) {
							//TODO need to implement completion condition
							future.set(responseCount);
						}
					}
				
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
				javax.jms.Message jmsMessage = context.getConsumer().receive(messageTimeout);
				if (Thread.currentThread().isInterrupted())
					break;
				if (jmsMessage == null) {
					continue;
				}

				Message message = context.fromJMSMessage(jmsMessage);
				if (message != null) {
					T response = MessageUtil.getPart(message, "response");
					handler.handle(response);
					responseCount++;
				}
			} catch (Throwable e) {
				e.printStackTrace();
				break;
			}

			if (Thread.currentThread().isInterrupted())
				break;
		}

		return responseCount;
	}

}
