package org.aries.jms.p2p;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Session;

import org.aries.AbstractAdapter;
import org.aries.AdapterListener;
import org.aries.Assert;
import org.aries.jms.JmsMessageConsumer;
import org.aries.jms.JmsSessionAdapter;
import org.aries.jms.JmsSessionPool;
import org.aries.jms.config.JmsExchangeDescripter;
import org.aries.util.IdGenerator;
import org.aries.util.concurrent.FutureResult;


public class JmsMessageRequestor extends AbstractAdapter {

	private static final String MBEAN_NAME = "Messaging:name=MessageExchangeClient";

	private static final int RECEIVE_WAIT_TIME = 3600000;

	private static final int TRANSMIT_THREAD_POOL_SIZE = 2;

	private static final int RECEIVE_THREAD_POOL_SIZE = 2;

//	protected JmsSessionAdapter _sessionAdapter;

	protected JmsExchangeDescripter _specification;

	protected JmsMessageExchanger _exchanger;

	private IdGenerator _idGenerator;

//	private ExecutorService _transmitExecutor;

//	private ExecutorService _receiveExecutor;

	private Map<Long, FutureResult<Object>> _pendingRequests;

	private int _receiveWaitTime;


	public JmsMessageRequestor(JmsExchangeDescripter specification) throws Exception {
		_specification = specification;
		construct();
	}

//	public JmsMessageExchangeClientImpl(JmsSessionAdapter sessionAdapter) throws Exception {
//		construct(sessionAdapter, sessionAdapter);
//	}
//
//	public JmsMessageExchangeClientImpl(JmsSessionAdapter incomingSessionAdapter, JmsSessionAdapter outgoingSessionAdapter) throws Exception {
//		construct(incomingSessionAdapter, outgoingSessionAdapter);
//	}

//	public JmsMessageExchangeClientImpl(JmsSessionAdapter sessionAdapter, JmsMessageExchangeSpec specification) throws Exception {
//		construct(sessionAdapter, sessionAdapter);
//		_specification = specification;
//	}

//	private void construct(JmsSessionAdapter incomingSessionAdapter, JmsSessionAdapter outgoingSessionAdapter) throws Exception {
//		_exchanger = createExchanger(incomingSessionAdapter, outgoingSessionAdapter);
//		_transmitExecutor = createTransmitExecutor();
//		_receiveExecutor = createReceiveExecutor();
//		_pendingRequests = new HashMap<Long, FutureResult<Object>>();
//		_idGenerator = createRequestIdGenerator();
//		setReceiveWaitTime(RECEIVE_WAIT_TIME);
//	}

	private void construct() throws Exception {
		_exchanger = createExchanger();
//		_transmitExecutor = createTransmitExecutor();
//		_receiveExecutor = createReceiveExecutor();
		_pendingRequests = new HashMap<Long, FutureResult<Object>>();
		_idGenerator = createRequestIdGenerator();
		setReceiveWaitTime(RECEIVE_WAIT_TIME);
	}

	public String getMBeanName() {
		return MBEAN_NAME;
	}

	public JmsExchangeDescripter getDescripter() {
		return _specification;
	}

	public void setDescripter(JmsExchangeDescripter value) {
		if (_exchanger != null)
			_exchanger.setSpecification(value);
		_specification = value;
	}

//	public Session getIncomingSession() {
//		return _exchanger.getIncomingSessionAdapter().getSession();
//	}
//
//	public Session getOutgoingSession() {
//		return _exchanger.getOutgoingSessionAdapter().getSession();
//	}

//	public JmsSessionAdapter getSessionAdapter() {
//		return _sessionAdapter;
//	}

//	public void setSessionAdapter(JmsSessionAdapter value) {
//		_sessionAdapter = value;
//		if (_exchanger != null)
//			_exchanger.setSessionAdapter(value);
//		setJndiContext(value.getJndiContext());
//	}

	protected Map<Long, FutureResult<Object>> getPendingRequests() {
		return _pendingRequests;
	}

	public IdGenerator getRequestIdGenerator() {
		return _idGenerator;
	}

	public void setRequestIdGenerator(IdGenerator idGenerator) {
		_idGenerator = idGenerator;
	}

	public int getReceiveWaitTime() {
		return _receiveWaitTime;
	}

	public void setReceiveWaitTime(int waitTime) {
		_receiveWaitTime = waitTime;
	}


	public void initialize() throws Exception {
		_exchanger.initialize();
		_exchanger.addSessionListener(createSessionListener());
		_exchanger.setMessageListener(createResponseHandler());
	}

//	protected JmsMessageExchangeTransportImpl createExchanger() {
//		JmsMessageExchangeTransportImpl exchanger = new JmsMessageExchangeTransportImpl() {
//			protected JmsMessageConsumer createConsumer() throws Exception {
//				JmsMessageConsumer consumer = createConsumer(createReplyTo());
//				return consumer;
//			}
//		};
//		//exchanger.setSessionAdapter(getSessionAdapter());
//		exchanger.setSpecification(getSpecification());
//		return exchanger;
//	}
//
//	protected JmsMessageExchangeTransport createExchanger(JmsMessageExchangeSpec specification) {
//		return new JmsMessageExchangeTransportImpl(specification) {
//			@Override
//			protected JmsMessageConsumer createConsumer() throws Exception {
//				JmsMessageConsumer consumer = createConsumer(createReplyTo());
//				return consumer;
//			}
//		};
//	}

//	protected JmsMessageExchangeTransportImpl createExchanger(JmsSessionAdapter incomingSessionAdapter, JmsSessionAdapter outgoingSessionAdapter) {
//		return new JmsMessageExchangeTransportImpl(incomingSessionAdapter, outgoingSessionAdapter) {
//
//			@Override
//			protected JmsMessageConsumer createConsumer() throws Exception {
//				JmsMessageConsumer consumer = createConsumer(createReplyTo());
//				return consumer;
//			}
//		};
//	}

	protected JmsMessageExchanger createExchanger() {
		return new JmsMessageExchanger(getDescripter()) {

			@Override
			protected JmsMessageConsumer createConsumer() throws Exception {
				JmsMessageConsumer consumer = createConsumer(createReplyTo());
				return consumer;
			}
		};
	}

	protected AdapterListener createSessionListener() {
		return new LocalSessionListener();
	}

//	protected ExecutorService createReceiveExecutor() {
//		return createExecutor("Receive", RECEIVE_THREAD_POOL_SIZE);
//	}
//
//	protected ExecutorService createTransmitExecutor() {
//		return createExecutor("Transmit", TRANSMIT_THREAD_POOL_SIZE);
//	}
//
//	protected ExecutorService createExecutor(final String name, int poolSize) {
//		//TODO use keep alive var.
//		final ThreadGroup threadGroup = new ThreadGroup("org.aries.jms");
//		SynchronousQueue<Runnable> workQueue = new SynchronousQueue<Runnable>();
//		ThreadPoolExecutor executor = new ThreadPoolExecutor(1, poolSize, 1, TimeUnit.MINUTES, workQueue, new ThreadFactory() {
//			private int counter = 0;
//			public Thread newThread(Runnable runnable) {
//				Thread thread = new Thread(threadGroup, runnable);
//				thread.setName("JmsMessageExchangeClient."+name+"."+counter);
//				//TODO do we need this?
//				//thread.setDaemon(true);
//				counter++;
//				return thread;
//			}
//		});
//		executor.prestartAllCoreThreads();
//		return executor;
//	}

	//TODO move this sessionAdapter
	public Destination createReplyTo() throws JMSException {
		JmsSessionAdapter sessionAdapter = JmsSessionPool.getInstance().get();
		try {
			Session session = sessionAdapter.getSession();
			Destination destination = session.createTemporaryQueue();
			return destination;
		} catch (Throwable e) {
			//ignore and reset
		}
		try {
			sessionAdapter.reset();
			Session session = sessionAdapter.getSession();
			Destination destination = session.createTemporaryQueue();
			return destination;
		} catch (Exception e) {
			throw new JMSException(e.getMessage());
		} finally {
			JmsSessionPool.getInstance().release(sessionAdapter);
		}
	}

	protected MessageListener createResponseHandler() throws JMSException {
		ResponseHandler handler = new ResponseHandler();
		return handler;
	}

	protected IdGenerator createRequestIdGenerator() {
		return new IdGenerator();
	}

	protected Long createRequestId(Object data) {
		return _idGenerator.createRequestId(data);
	}


	public void start() throws Exception {
		throw new UnsupportedOperationException();
	}

	public void stop() throws Exception {
		throw new UnsupportedOperationException();
	}
	
	public void validate() throws Exception {
		_exchanger.validate();
	}


	/*
	 * Transmission of outgoing message.
	 */

//	public void send(Message message) throws Exception {
//		queueOutgoingMessage(message);
//	}


	/*
	 * Transmission of invocation request.
	 */

	public Message invoke(Message request) throws Exception {
//		queueOutgoingMessage(outgoingMessage);
		
    	Destination replyTo = _exchanger.getReplyTo();
        Assert.notNull(replyTo, "ReplyTo must be specified");
        request.setJMSReplyTo(replyTo);
		Long requestId = null;

		try {
			requestId = request.getLongProperty("RequestId");
			_exchanger.sendMessage(request);
		} catch (Throwable e) {
			log.error("Problem sending: "+e);
			notifyPendingRequest(e, requestId);
		}

		Object result = waitForIncomingResult(requestId);
		Message incomingMessage = null;
		if (result instanceof Throwable)
			throw (Exception) result;
		if (result instanceof Message)
			incomingMessage = (Message) result;
		return incomingMessage;
	}

	protected Object waitForIncomingResult(Long requestId) throws Exception {
		FutureResult<Object> futureResult = new FutureResult<Object>();
		_pendingRequests.put(requestId, futureResult);
//		ClassLoader localClassLoader = getClass().getClassLoader();
//		ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
//		Thread.currentThread().setContextClassLoader(localClassLoader);
		log.debug("Response pending: requestID="+requestId);
		//TODO Object result = futureResult.timedGet(_receiveWaitTime);
		Object result = futureResult.get();
		log.debug("Response received: requestID="+requestId+", result="+result);
		Assert.isSerializable(result);
		return (Serializable) result;
	}


//	public Serializable invoke(Serializable data) throws Exception {
//		Long requestId = createRequestId(data);
//		queueOutgoingMessage(data, requestId);
//		Serializable result = waitIncomingResponse(requestId);
//		if (result instanceof Exception)
//			throw (Exception) result;
//		return result;
//	}

	protected Serializable waitIncomingResponse(Long requestId) throws Exception {
		FutureResult<Object> futureResult = new FutureResult<Object>();
		_pendingRequests.put(requestId, futureResult);
		log.debug("Response pending: requestID="+requestId);
		//TODO Object result = futureResult.timedGet(_receiveWaitTime);
		Object result = futureResult.get();
		log.debug("Response received: requestID="+requestId+", result="+result);
		Assert.isSerializable(result);
		return (Serializable) result;
	}

//	protected void queueOutgoingMessage(Message message) throws Exception {
//		synchronized (_transmitExecutor) {
//	    	Destination replyTo = _exchanger.getReplyTo();
//	        Assert.notNull(replyTo, "ReplyTo must be specified");
//	    	message.setJMSReplyTo(replyTo);
//			Runnable sender = createOutgoingMessageSender(message);
//			_transmitExecutor.execute(sender);
//		}
//	}

//	protected Runnable createOutgoingMessageSender(final Message message) {
//		return new Runnable() {
//			public void run() {
//				Long requestId = null;
//				try {
//					requestId = message.getLongProperty("RequestId");
//					_exchanger.sendMessage(message);
//				} catch (Throwable e) {
//					log.error("Problem sending: "+e);
//					notifyPendingRequest(e, requestId);
//				}
//			}
//		};
//	}

//	protected void queueOutgoingMessage(Serializable data, final Long requestId) {
//		synchronized (_transmitExecutor) {
//			Runnable sender = createOutgoingMessageSender(data, requestId);
//			_transmitExecutor.execute(sender);
//		}
//	}

//	protected Runnable createOutgoingMessageSender(final Serializable data, final Long requestId) {
//		return new Runnable() {
//			public void run() {
//				try {
//					_exchanger.sendMessage(data, requestId);
//				} catch (Throwable e) {
//					log.error("Problem sending: "+e);
//					notifyPendingRequest(e, requestId);
//				}
//			}
//		};
//	}


	/*
	 * Receipt / handling of invocation response.
	 */

	class ResponseHandler implements MessageListener {
		public void onMessage(Message message) {
			logIncomingMessage(message);
//			queueIncomingMessage(message);
		}
	}

	protected void logIncomingMessage(Message message) {
		try {
			log.debug("Received message: "+message.getJMSMessageID());
		} catch (JMSException e) {
			log.error(e);
		}
	}

//	protected void queueIncomingMessage(Message message) {
//		synchronized(_receiveExecutor) {
//			Runnable handler = createIncomingMessageHandler(message);
//			_receiveExecutor.execute(handler);
//		}
//	}

	protected Runnable createIncomingMessageHandler(final Message message) {
		return new Runnable() {
			public void run() {
				Long requestId = null;
				try {
					requestId = (Long) message.getLongProperty("RequestId");
					//Serializable data = MessageUtil.extractPayload(message);
					processIncomingMessage(message, requestId);
				} catch (Exception e) {
					log.error(e);
					notifyPendingRequest(e, requestId);
				}
			}
		};
	}

	protected void processIncomingMessage(Message message, Long requestId) {
		notifyPendingRequest(message, requestId);
	}

	protected void processIncomingMessage(Serializable data, Long requestId) {
		notifyPendingRequest(data, requestId);
	}

	protected void notifyPendingRequest(Serializable data, Long requestId) {
		FutureResult<Object> futureResult = _pendingRequests.get(requestId);
		if (futureResult != null)
			futureResult.set(data);
	}

	protected void notifyPendingRequest(Message message, Long requestId) {
		FutureResult<Object> futureResult = _pendingRequests.get(requestId);
		if (futureResult != null)
			futureResult.set(message);
	}


	public void reset() throws Exception {
		_exchanger.reset();
	}

	public void close() throws Exception {
		log.trace("Closing exchanger");
		_exchanger.close();
//		log.trace("Closing receive executor");
//		_receiveExecutor.shutdownNow();
//		log.trace("Closing transmit executor");
//		_transmitExecutor.shutdownNow();
		log.trace("Closed all");
	}


	class LocalSessionListener implements AdapterListener {

		public void initialized() {
			try {
				Destination replyTo = createReplyTo();
				_exchanger.setReplyTo(replyTo);
			} catch (JMSException e) {
				log.error(e);
			}
		}

		public void closed() {
			//nothing for now
		}

		public void reset() {
			//nothing for now
		}
	}

}
