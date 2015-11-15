package org.aries.jms.xa;

import java.io.Serializable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.naming.NamingException;
import javax.transaction.Status;

import org.aries.jms.JmsMessageConsumer;
import org.aries.jms.config.JmsConsumerDescripter;
import org.aries.jms.util.MessageUtil;


public class JmsXAMessageConsumer extends AbstractXAEndpoint {    

	private static final String MBEAN_NAME = "MessageEndpoint:name=XAConsumer";

	private static final int DEFAULT_TIMEOUT = 10;  //seconds

	protected JmsMessageConsumer messageConsumer;

	protected MessageListener messageListener;

	private boolean autoRecoveryEnabled; 

	private int timeout;

	private Thread thread;


	public JmsXAMessageConsumer(JmsXASessionAdapter sessionAdapter, JmsConsumerDescripter specification) {
		super(sessionAdapter, specification);
		construct();
	}

	private void construct() {
		setTimeout(DEFAULT_TIMEOUT);
		setAutoRecoveryEnabled(true);
	}

	public String getMBeanName() {
		return MBEAN_NAME;
	}

	public JmsConsumerDescripter getDescripter() {
		return (JmsConsumerDescripter) endpointDescripter;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int value) {
		timeout = value;
	}

	public MessageListener getMessageListener() {
		return messageListener;
	}

	public void setMessageListener(MessageListener value) { 
		messageListener = value;
	}

	public boolean isAutoRecoveryEnabled() {
		return autoRecoveryEnabled;
	}

	public void setAutoRecoveryEnabled(boolean value) {
		autoRecoveryEnabled = value;
	}


	protected void assureEndpoint() throws JMSException, NamingException {
		messageConsumer = createConsumer();
		//_consumer.initialize();
	}

	protected JmsMessageConsumer createConsumer() throws JMSException {
		return createConsumer(getDescripter());
	}

	protected JmsMessageConsumer createConsumer(JmsConsumerDescripter specification) throws JMSException {
		JmsMessageConsumer consumer = new JmsMessageConsumer(getXASessionAdapter(), specification);
		consumer.setAutoRecoveryEnabled(false);
		return consumer;
	}


	public void initialize() throws NamingException, JMSException {
		synchronized (mutex) {
			super.initialize();
			messageConsumer.initialize();
		}
	}

	public void start() throws JMSException {
		synchronized (mutex) {
			//TODO use an executor
			//_consumer.start();
			//Assert.isNotNull(_listener);
			thread = new Thread(this);
			thread.start();
			setStarted(true);
		}
	}

//	public void run() {
//		try {
//			setStarted(true);
//			log.trace("Consumer receiver thread started");
//			while (isValid() && !Thread.currentThread().isInterrupted()) {
//				try {
//					poll(timeout);
//				} catch (Exception e) {
//					//TODO handle this
//					log.error(e);
//					break;
//				}
//			}
//		} finally {
//			setStopped(true);
//			thread = null;
//		}
//	}

	private ExecutorService executor;

	public void run() {
		try {
			setStarted(true);
			log.trace("Consumer receiver thread started");
			executor = Executors.newFixedThreadPool(10);
			while (isValid() && !Thread.currentThread().isInterrupted()) {
				Future<?> future = executor.submit(new Runnable() {
					public void run() {
						try {
							poll(timeout);
						} catch (Exception e) {
							//TODO handle this
							log.error(e);
						}
					}
				});
				try {
					future.get();
				} catch (InterruptedException e) {
					//e.printStackTrace();
					return;
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
			}
		} finally {
			setStopped(true);
			thread = null;
		}
	}
	
	//TODO figure what kind of exception(s) should be thrown here
	public void poll(int timeout) throws JMSException, Exception {
		boolean localTransactionStarted = assureTransactionStarted();

		//wait on message only half the timeout
		long receiveTimeout = timeout * 1000 / 2;

		//spend only at most this much in wait (because we are holding the mutex)
		int pauseTime = 600;

		for (int i=0; i < receiveTimeout; i += pauseTime) {
			validate();
			if (!isValid()) {
				processTransactionFailure(localTransactionStarted);
				return;
			}

			try {
				if (log.isTraceEnabled())
					log.trace("Waiting for incoming message...");

				/* attempt to receive a message */
				Message message = receiveMessageInternal(pauseTime);

				/* check closed? */
				if (!isValid() || messageListener == null) {
					processTransactionFailure(localTransactionStarted);
					return;
				}

				//TODO we need this? what do we do with transaction here? how to cancel it?
				if (message == null) {
					//log.trace("No message yet");
					continue;
				}

				/* process the message */
				if (log.isTraceEnabled())
					log.trace("Received incoming message: "+message);
				messageListener.onMessage(message);

				/* validate transaction state */
				//if (!transaction.isOpen())
				//	throw new Exception("Transaction not active");

				//TODO handle other states
				/* check for rollback request */
				if (getTransactionManager().getStatus() == Status.STATUS_MARKED_ROLLBACK) {
					//TODO need counter and limit here to prevent looping 
					processTransactionFailure(localTransactionStarted);
					return;
				}

				assureTransactionComplete(localTransactionStarted);
				return;

			} catch (Throwable e) {
				log.error(e);
				processTransactionFailure(localTransactionStarted);
				//TODO take this out or not?
				//throw new Exception(e.getMessage());
				return;
			}
		}
	}

	public void validate() throws NamingException, JMSException {
		if (messageConsumer != null)
			messageConsumer.validate();
		//if (_transactionManager != null)
		//    validate(_transactionManager);
	}

	//	protected void validate(TransactionManager transactionManager) throws Exception {
	//		Assert.isNotNull(transactionManager, "Transaction manager must be specified");
	//		Assert.isTrue(transactionManager.getStatus() == Status.STATUS_ACTIVE, "Transaction manager is in unexpected state: "+_transactionManager.getStatus());
	//		Assert.isTrue(transactionManager.getTransaction() == null, "A previous transaction is pending");
	//	}


	public void stop() throws JMSException {
		setStopping(true);
		synchronized (mutex) {
			try {
				if (thread != null)
					thread.interrupt();
			} finally {
				setStopped(true);
			}
		}
	}
	
	public void join() throws Exception {
		if (thread != null) {
			thread.join();
		}
	}

	public void close() throws JMSException {
		stop();
		setClosing(false);
		synchronized (mutex) {
			if (messageConsumer != null) {
				log.trace("closing");
				messageConsumer.close();
				log.trace("closed");
				messageConsumer = null;
			}
			super.close();
			setClosed(false);
		}
	}



	/*
	 * Receive methods:
	 * ----------------
	 */

	public Serializable receive() throws Exception {
		return receive(0);
	}

	public Serializable receive(long timeout) throws Exception {
		//TODO create transaction here
		Message message = receiveMessage(timeout);
		Serializable payload = MessageUtil.extractPayload(message);
		return payload;
	}

	public Message receiveMessage() throws Exception {
		//TODO create transaction here
		Message message = receiveMessage(0);
		return message;
	}

	public Message receiveMessage(long timeout) throws Exception {
		boolean localTransactionStarted = assureTransactionStarted();
		
		//wait on message only half the timeout
		long receiveTimeout = timeout * 1000 / 2;

		//spend only at most this much in wait (because we are holding the mutex)
		int pauseTime = 600;

		for (int i=0; i < receiveTimeout; i += pauseTime) {
			validate();
			if (!isValid()) {
				processTransactionFailure(localTransactionStarted);
				return null;
			}

			Message message = null;
			Exception exception = null;
			
			try {
				message = receiveMessageInternal(pauseTime);
				if (message == null) {
					continue;
				}
			} catch (Exception e) {
				exception = e;
			}

			if (exception != null) {
				processTransactionFailure(localTransactionStarted);
				//TODO convert this to JMSException?
				//throw convert(exception);
				//TODO do we wish to rollback here or forward the message 
				//to another destination after some number of attempts?
				//sessionAdapter.rollback();
				throw exception;
			}
			
			assureTransactionComplete(localTransactionStarted);
			//sessionAdapter.commit();
			return message;
		}
		
		return null;
	}

	
	protected Message receiveMessageInternal(long timeout) throws Exception {
		//TODO verify needed synchronization exists
		synchronized (mutex) {
			if (!isValid())
				return null;
			validate();

			Message message = null;
			Exception exception = null;
			
			try {
				if (messageConsumer == null)
					messageConsumer = createConsumer();

				if (log.isTraceEnabled())
					log.trace("Waiting for message...");
				message = messageConsumer.receive(timeout);
				if (log.isTraceEnabled() && message != null)
					log.trace("Received message: "+message.getClass());

				//TODO we need this? what do we do with transaction here? how to cancel it?
				//if (message == null)
				//	return null;

			} catch (Exception e) {
				exception = e;
				if (isAutoRecoveryEnabled())
					process(e);
			}

			if (exception != null) {
				//TODO convert this to JMSException?
				//throw convert(exception);
				throw exception;
			}
			
			return message;
		}
	}
	
}

