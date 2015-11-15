package org.aries.jms.client;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TemporaryQueue;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.naming.spi.InitialContextFactory;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.Assert;
import org.aries.bean.Responder;
import org.aries.client.AbstractEndpoint;
import org.aries.client.Client;
import org.aries.jms.util.MessageUtil;
import org.aries.message.util.MessageConstants;
import org.aries.util.ExceptionUtil;
import org.aries.util.IdGenerator;
import org.aries.util.concurrent.FutureResult;


public class JmsClient extends AbstractEndpoint implements Client, Responder {

	private static final int DEFAULT_RECEIVE_WAIT_TIME = 3600000;
	
	protected Log log = LogFactory.getLog(getClass());
	
	
	/* CONFIGURATION */

	private String providerUrl;

	private Properties initialContextProperties;
	
	private InitialContextFactory initialContextFactory;

	private String connectionFactoryName;

	private String destinationName;
	
	private int receiveWaitTime = DEFAULT_RECEIVE_WAIT_TIME;

	private boolean createTemporaryQueue;
	
	private boolean createTemporaryTopic;
	

	/* STATE */

	private AtomicBoolean started;
	
	private Context initialContext;

	private ConnectionFactory connectionFactory;

	//@Resource(mappedName = "java:/JmsXA")
    //private XAConnectionFactory xaConnectionFactory;

	private Connection connection;
	
	private Session session;
	
	private Destination destination;
	
	private MessageProducer producer;
	
	private MessageConsumer consumer;
	
	private Destination replyToQueue;

	private Destination replyToTopic;
	
	//for synchronous response
	private MessageListener messageListener;
	
	//for asynchronous response
	private MessageListener messageListener2;
	
	private Map<String, FutureResult<Object>> pendingRequests;


	public JmsClient() {
		clearState();
	}
	
	public Properties getInitialContextProperties() {
		return initialContextProperties;
	}

	public void setInitialContextProperties(Properties initialContextProperties) {
		this.initialContextProperties = initialContextProperties;
	}

	public String getProviderUrl() {
		return providerUrl;
	}

	public void setProviderUrl(String providerUrl) {
		this.providerUrl = providerUrl;
	}

	public InitialContextFactory getInitialContextFactory() {
		return initialContextFactory;
	}

	public void setInitialContextFactory(InitialContextFactory initialContextFactory) {
		this.initialContextFactory = initialContextFactory;
	}

	public String getConnectionFactoryName() {
		return connectionFactoryName;
	}

	public void setConnectionFactoryName(String connectionFactoryName) {
		this.connectionFactoryName = connectionFactoryName;
	}

	public String getDestinationName() {
		return destinationName;
	}

	public void setDestinationName(String destinationName) {
		this.destinationName = destinationName;
	}
	
	public void setCreateTemporaryQueue(boolean value) {
		this.createTemporaryQueue = value;
	}

	public void setCreateTemporaryTopic(boolean value) {
		this.createTemporaryTopic = value;
	}

	public Destination getReplyToQueue() {
		return replyToQueue;
	}

	public void setReplyToQueue(Destination replyToQueue) {
		this.replyToQueue = replyToQueue;
	}

	public Destination getReplyToTopic() {
		return replyToTopic;
	}

	public void setReplyToTopic(Destination replyToTopic) {
		this.replyToTopic = replyToTopic;
	}

	
	protected void clearState() {
		synchronized (mutex) {
			initialContext = null;
			connectionFactory = null;
			connection = null;
			session = null;
			destination = null;
			producer = null;
			consumer = null;
			replyToQueue = null;
			replyToTopic = null;
			started = new AtomicBoolean(false);
			pendingRequests = new HashMap<String, FutureResult<Object>>();
		}
	}

	protected void clearState(Long requestId) {
		synchronized (mutex) {
			clearState();
			pendingRequests.remove(requestId);
		}
	}
	

//	protected InitialContext getInitialContext() throws NamingException {
//        Properties properties = new Properties();
//        if (initialContextFactoryClass != null)
//        	properties.put(Context.INITIAL_CONTEXT_FACTORY, initialContextFactoryClass);
//        if (providerUrl != null)
//        	properties.put(Context.PROVIDER_URL, System.getProperty(Context.PROVIDER_URL, providerUrl));
//        if (username != null)
//        	properties.put(Context.SECURITY_PRINCIPAL, System.getProperty("username", username));
//        if (password != null)
//        	properties.put(Context.SECURITY_CREDENTIALS, System.getProperty("password", password));
//        InitialContext initialContext = new InitialContext(properties);
//        return initialContext;
//	}
	
	protected Context getInitialContext(Properties properties) throws NamingException {
		synchronized (mutex) {
			if (initialContext == null) {
				if (initialContextFactory != null)
					initialContext = initialContextFactory.getInitialContext(properties);
				else initialContext = new InitialContext(properties);
			}
			return initialContext;
		}	
	}
	

	/*
	 * Initialization
	 * --------------
	 */
	
	public void initialize() throws NamingException, JMSException {
		close();
		
		try {
			connection = createConnection();
			session = createSession();
			destination = getDestination();
			producer = createProducer();
			if (replyToQueue != null)
				consumer = createConsumer(replyToQueue);
			else if (replyToTopic != null)
				consumer = createConsumer(replyToTopic);
			else if (messageListener != null)
				consumer = createConsumer(destination);

			connection.start();
			started.set(true);
			
		} catch (NamingException e) {
			clearState();
			throw e;
			
		} catch (JMSException e) {
			clearState();
			throw e;
		}
	}
	
	protected Connection createConnection() throws NamingException, JMSException {
		ConnectionFactory connectionFactory = getConnectionFactory();
		Connection connection = null;
		if (userName == null && password == null)
			connection = connectionFactory.createConnection();
		else connection = connectionFactory.createConnection(userName, password);
		return connection;
	}

	protected ConnectionFactory getConnectionFactory() throws NamingException {
		synchronized (mutex) {
			if (connectionFactory == null) {
				Context initialContext = getInitialContext(initialContextProperties);
				connectionFactory = (ConnectionFactory) initialContext.lookup(connectionFactoryName);
			}
			return connectionFactory; 
		}
	}
	
	public void setConnectionFactory(ConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
	}

	protected Session createSession() throws JMSException {
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		if (createTemporaryQueue)
			replyToQueue = session.createTemporaryQueue();
		if (createTemporaryTopic)
			replyToTopic = session.createTemporaryTopic();
		return session;
	}
	
	protected MessageProducer createProducer() throws JMSException {
		MessageProducer producer = session.createProducer(destination);
		return producer;
	}
	
	protected MessageConsumer createConsumer(Destination destination) throws JMSException {
		MessageConsumer consumer = session.createConsumer(destination);
		consumer.setMessageListener(messageListener);
		return consumer;
	}
	
	protected MessageListener createHandler() throws JMSException {
		return new MessageListener() {
			public void onMessage(Message message) {
				String correlationId = null;
				try {
					correlationId = message.getStringProperty(MessageConstants.PROPERTY_CORRELATION_ID);
					log.info("Message received: "+MessageUtil.toString(message));
					notifyPendingRequest(correlationId, message);
				} catch (Exception e) {
		            log.error("****** Exception: "+ExceptionUtils.getRootCauseMessage(e));
		            if (correlationId != null)
		            	notifyPendingRequest(correlationId, e);
				}
			}
		};
	}
	
	protected Destination getDestination() throws NamingException, JMSException {
		synchronized (mutex) {
			if (destination != null)
				return destination;
			if (destinationName == null)
				destination = session.createTemporaryQueue();
			if (destination == null)
				destination = getDestination(destinationName);
			Assert.notNull(destination, "Destination must be specified");
			return destination; 
		}
	}

	protected Destination getDestination(String jndiName) throws NamingException {
		Context initialContext = getInitialContext(initialContextProperties);
		Destination destination = (Destination) initialContext.lookup(jndiName);
		return destination;
	}

	public void setDestination(Destination destination) {
		this.destination = destination;
	}

	protected ObjectMessage createObjectMessage() throws JMSException {
		ObjectMessage objectMessage = session.createObjectMessage();
		if (replyToQueue != null)
			objectMessage.setJMSReplyTo(replyToQueue);
		if (replyToTopic != null)
			objectMessage.setJMSReplyTo(replyToTopic);
		return objectMessage;
	}

	protected ObjectMessage createObjectMessage(Serializable payload) throws JMSException {
		ObjectMessage objectMessage = session.createObjectMessage(payload);
		if (replyToQueue != null)
			objectMessage.setJMSReplyTo(replyToQueue);
		if (replyToTopic != null)
			objectMessage.setJMSReplyTo(replyToTopic);
		return objectMessage;
	}
	
	
	protected String getCorrelationId(Serializable message) {
		//ignore message by default
		return getCorrelationId();
	}

	protected String getTransactionId(Serializable message) {
		//ignore message by default
		return getTransactionId();
	}


	/*
	 * Send operations
	 * ---------------
	 */
	
	public void send(Serializable message) throws NamingException, JMSException {
		send((String) null, message, getCorrelationId(message), getTransactionId(message));
	}
	
	public void send(Serializable message, String correlationId) throws NamingException, JMSException {
		send((String) null, message, correlationId, getTransactionId(message));
	}
	
	public void send(Serializable message, String correlationId, String transactionId) throws NamingException, JMSException {
		send((String) null, message, correlationId, transactionId);
	}
	
	public void send(String destinationName, Serializable message) throws NamingException, JMSException {
		send(destinationName, message, getCorrelationId(message), getTransactionId(message));
	}
	
	public void send(String destinationName, Serializable message, String correlationId) throws NamingException, JMSException {
		send(destinationName, message, correlationId, getTransactionId(message));
	}
	
	public void send(String destinationName, Serializable payload, String correlationId, String transactionId) throws NamingException, JMSException {
		boolean needInitialize = !started.get();
		if (destinationName != null) {
			if (this.destinationName == null || !this.destinationName.equals(destinationName)) {
				this.destinationName = destinationName;
				this.destination = null;
				needInitialize = true;
			}
		}
		
		if (needInitialize)
			initialize();

		//Message message = new Message();
		//message.addPart(MessageConstants.PROPERTY_MESSAGE_PAYLOAD, payload);
		//message.addPart(MessageConstants.PROPERTY_CORRELATION_ID, correlationId);
		//message.addPart(MessageConstants.PROPERTY_TRANSACTION_ID, transactionId);
		//message.addPart(MessageConstants.PROPERTY_TRANSACTION_CONTEXT, getTransactionContext());

		try {
			//objectMessage.setStringProperty(MessageConstants.PROPERTY_CORRELATION_ID, correlationId);
			//objectMessage.setStringProperty(MessageConstants.PROPERTY_TRANSACTION_ID, transactionId);
			//objectMessage.setObjectProperty(MessageConstants.PROPERTY_TRANSACTION_CONTEXT, getTransactionContext());
			//objectMessage.setObjectProperty(MessageConstants.PROPERTY_MESSAGE_PAYLOAD, payload);

			ObjectMessage objectMessage = createObjectMessage(payload);
			objectMessage.setStringProperty(MessageConstants.PROPERTY_CORRELATION_ID, correlationId);
			objectMessage.setStringProperty(MessageConstants.PROPERTY_TRANSACTION_ID, transactionId);
			
			if (messageListener2 != null) {
				TemporaryQueue temporaryQueue = session.createTemporaryQueue();
				MessageConsumer consumer = session.createConsumer(temporaryQueue);
				consumer.setMessageListener(messageListener2);
				objectMessage.setJMSReplyTo(temporaryQueue);
			}
			
			producer.send(objectMessage);

		} catch (JMSException e) {
			clearState();
			throw e;

		} catch (Exception e) {
			clearState();
			throw ExceptionUtil.rewrap(e);
		}
	}

	//typically for a reply to specific Destination
	public void send(Destination destination, Serializable message) throws NamingException, JMSException {
		send(destination, message, getCorrelationId(message), getTransactionId(message));
	}
	
	//typically for a reply to specific Destination
	public void send(Destination destination, Serializable message, String correlationId) throws NamingException, JMSException {
		send(destination, message, correlationId, getTransactionId(message));
	}
	
	//typically for a reply to specific Destination
	public void send(Destination destination, Serializable payload, String correlationId, String transactionId) throws NamingException, JMSException {
		Assert.notNull(destination, "Destination is null");
		boolean needInitialize = !started.get();
		
		if (needInitialize)
			initialize();

		try {
			ObjectMessage objectMessage = createObjectMessage(payload);
			objectMessage.setStringProperty(MessageConstants.PROPERTY_CORRELATION_ID, correlationId);
			objectMessage.setStringProperty(MessageConstants.PROPERTY_TRANSACTION_ID, transactionId);
			
			MessageProducer producer = session.createProducer(destination);
			producer.send(destination, objectMessage);

		} catch (JMSException e) {
			clearState();
			throw e;

		} catch (Exception e) {
			clearState();
			throw ExceptionUtil.rewrap(e);
		}
	}

	public void sendResponse(Serializable message) {
		sendResponse(null, message);
	}
	
	public void sendResponse(String destinationName, Serializable message) {
		try {
			if (destinationName != null)
				send(destinationName, message);
			send(message);
		} catch (Exception e) {
			log.error("Error", e);
			throw ExceptionUtil.rewrap(e);
		}
	}

	public void sendResponse(Serializable message, String correlationId, String transactionId) {
		try {
			send(message, correlationId, transactionId);
			
		} catch (Exception e) {
			log.error("Error", e);
			throw ExceptionUtil.rewrap(e);
		}
	}

	public void sendResponse(Destination destination, Serializable message, String correlationId, String transactionId) {
		try {
			send(message, correlationId, transactionId);
			
		} catch (Exception e) {
			log.error("Error", e);
			throw ExceptionUtil.rewrap(e);
		}
	}

	
	/*
	 * Invoke operations
	 * -----------------
	 */
	
	@Override
	public <T extends Serializable> T invoke(Serializable message) throws NamingException, JMSException {
		return invoke(message, getCorrelationId());
	}

	public <T extends Serializable> T invoke(Serializable message, String correlationId) throws NamingException, JMSException {
		return invoke(message, correlationId, getTransactionId(), 0L);
	}

	@Override
	public <T extends Serializable> T invoke(Serializable message, String correlationId, long timeout) throws NamingException, JMSException {
		return invoke(message, correlationId, getTransactionId(), timeout);
	}
	
	public <T extends Serializable> T invoke(Collection<? extends Serializable> message) throws NamingException, JMSException {
		return invoke((Serializable) message, getCorrelationId());
	}
	
	@SuppressWarnings("unchecked")
	public <T extends Serializable> T invoke(Serializable message, String correlationId, String transactionId, long timeout) throws NamingException, JMSException {
		if (!started.get())
			initialize();

		if (correlationId == null)
			correlationId = "correlationId";
		Long requestId = IdGenerator.createRequestId(message);
		FutureResult<Object> futureResult = new FutureResult<Object>();

		synchronized (mutex) {
			pendingRequests.put(correlationId, futureResult);
		}
		
		try {
			ObjectMessage objectMessage = session.createObjectMessage(message);
			objectMessage.setLongProperty(MessageConstants.PROPERTY_REQUEST_ID, requestId);
			objectMessage.setStringProperty(MessageConstants.PROPERTY_CORRELATION_ID, correlationId);
			objectMessage.setStringProperty(MessageConstants.PROPERTY_TRANSACTION_ID, transactionId);
			//objectMessage.setObjectProperty(MessageConstants.PROPERTY_MESSAGE_CONTENT, message);
			Destination replyTo = session.createTemporaryQueue();
			objectMessage.setJMSReplyTo(replyTo);

			//setup response listener
			messageListener = createHandler();
			consumer = createConsumer(replyTo);
			
			//do the send here
			producer.send(objectMessage);

		} catch (JMSException e) {
			clearState(requestId);
			throw e;

		} catch (Exception e) {
			clearState(requestId);
            log.error("****** Exception: "+ExceptionUtils.getRootCauseMessage(e));
			log.debug("", e);
			throw ExceptionUtil.rewrap(e);
			
		} finally {
		}
		
    	//wait for response message (or exception)
		Object result = waitForIncomingResult(requestId, futureResult, receiveWaitTime);
		if (result instanceof ObjectMessage) {
			ObjectMessage responseMessage = (ObjectMessage) result;
			T response = (T) responseMessage.getObject();
			return response;
		}
		
		//checkForAndThrowException(result);
		return null;
	}

	protected Object waitForIncomingResult(Long requestId, FutureResult<Object> futureResult, long timeout) throws JMSException {
		log.debug("Response pending: timeout="+timeout);

		try {
			Object result = futureResult.get(timeout);
			log.debug("Response received");
			pendingRequests.remove(requestId);
			//Assert.isSerializable(result);
			return result;

		} catch (Exception e) {
			//throw a JMSException here?
			throw new RuntimeException(e);

		} finally {
			pendingRequests.remove(requestId);
		}
	}
	
	protected void notifyPendingRequest(String correlationId, Object result) {
		log.info("Pending-request count: "+pendingRequests.size());
		Iterator<String> iterator = pendingRequests.keySet().iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			if (key.equals(correlationId)) {
				log.info("Pending-request found");
				break;
			}
		}
		
		FutureResult<Object> futureResult = pendingRequests.get(correlationId);
		pendingRequests.remove(correlationId);

		if (futureResult == null)
			log.error("FutureResult is null");
		if (futureResult != null)
			futureResult.set(result);
	}
	

	public MessageListener getMessageListener() {
		return messageListener;
	}
	
	public void setMessageListener(MessageListener messageListener) {
		this.messageListener = messageListener;
	}
	
	public MessageListener getMessageListener2() {
		return messageListener2;
	}
	
	public void setMessageListener2(MessageListener messageListener2) {
		this.messageListener2 = messageListener2;
	}
	
	
	/*
	 * Shutdown
	 * --------
	 */
	
	public void reset() throws JMSException {
		close();
		clearState();
	}
	
	public void close() throws JMSException {
		try { 
			// it is important to close session
			if (session != null) 
				session.close();
			
		} catch (JMSException e) {
			//ignore
		}
		
		try { 
			// Closing a connection automatically returns the connection and
			// its session plus producer to the resource reference pool.
			if (connection != null) 
				connection.close();
			
		} catch (JMSException e) {
			//ignore
			
		} finally {
			//clearState();
		}
	}

}
