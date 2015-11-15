package org.aries.jms.client;

import java.io.Serializable;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.XAConnectionFactory;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.aries.client.AbstractEndpoint;
import org.aries.message.util.MessageConstants;
import org.aries.runtime.BeanContext;
import org.aries.runtime.Initializer;
import org.aries.util.ExceptionUtil;


public abstract class JmsListener extends AbstractEndpoint {

	protected Properties initialContextProperties;
	
	protected String providerUrl;

	protected String initialContextFactoryClass;

	protected String connectionFactoryName;
	
	protected ConnectionFactory connectionFactory;

	@Resource(mappedName = "java:/JmsXA")
    private XAConnectionFactory xaConnectionFactory;
	
	protected String destinationName;

	protected Destination destination;
	

	public boolean isInitialized(String moduleId) {
		Initializer initializer = BeanContext.get(moduleId+".initializer");
		if (initializer != null)
			return true;
		return false;
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

	public String getInitialContextFactoryClass() {
		return initialContextFactoryClass;
	}

	public void setInitialContextFactoryClass(String initialContextFactoryClass) {
		this.initialContextFactoryClass = initialContextFactoryClass;
	}

	public String getConnectionFactoryName() {
		return connectionFactoryName;
	}

	public void setConnectionFactoryName(String connectionFactoryName) {
		this.connectionFactoryName = connectionFactoryName;
	}

	public ConnectionFactory getConnectionFactory() {
		return connectionFactory;
	}

	public void setConnectionFactory(ConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
	}

	public String getDestinationName() {
		return destinationName;
	}

	public void setDestinationName(String destinationName) {
		this.destinationName = destinationName;
	}

	public Destination getDestination() {
		return destination;
	}

	public void setDestination(Destination destination) {
		this.destination = destination;
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
	
	
	@PostConstruct
	public void initialize() {
		//nothing for now
	}

	public void reset() {
		//nothing for now
	}

	@PreDestroy
	public void close() {
		//nothing for now
	}
	

	public void sendMessage(Serializable message) throws JMSException {
		Connection connection = null;
		Session session = null;

		try {
			InitialContext initialContext = new InitialContext(initialContextProperties);
			//InitialContext initialContext = new InitialContext();

			if (connectionFactory == null) 
				connectionFactory = (ConnectionFactory) initialContext.lookup(connectionFactoryName);

			if (destination == null)
				destination = (Destination) initialContext.lookup(destinationName);

			if (userName == null || password == null)
				connection = connectionFactory.createConnection();
			else connection = connectionFactory.createConnection(userName, password);
			
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			//session.
			
			MessageProducer producer = session.createProducer(destination);
			connection.start();

			ObjectMessage objectMessage = session.createObjectMessage(message);
			producer.send(objectMessage);

		} catch (NamingException e) {
			connectionFactory = null;
			destination = null;
			throw ExceptionUtil.rewrap(e);
			
		} catch (JMSException e) {
			connectionFactory = null;
			destination = null;
			throw e;

		} finally {
			// it is important to close session
			if (session != null) 
				session.close();
			try { 
				// Closing a connection automatically returns the connection and
				// its session plus producer to the resource reference pool.
				if (connection != null) 
					connection.close();
			} catch (JMSException e) {
				//ignore
			};
		}
	}
	
	public void sendResponse(Destination destination, Serializable message, String correlationId, String transactionId) throws JMSException {
		Connection connection = null;
		Session session = null;

		try {
			InitialContext initialContext = new InitialContext(initialContextProperties);
			//InitialContext initialContext = new InitialContext();

			connectionFactoryName = "/ConnectionFactory";
			
			if (connectionFactory == null) 
				connectionFactory = (ConnectionFactory) initialContext.lookup(connectionFactoryName);

			if (destination == null)
				destination = (Destination) initialContext.lookup(destinationName);

			if (userName == null || password == null)
				connection = connectionFactory.createConnection();
			else connection = connectionFactory.createConnection(userName, password);
			
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			//session.
			
			MessageProducer producer = session.createProducer(destination);
			connection.start();

			ObjectMessage objectMessage = session.createObjectMessage(message);
			//objectMessage.setLongProperty(MessageConstants.PROPERTY_REQUEST_ID, requestId);
			objectMessage.setStringProperty(MessageConstants.PROPERTY_CORRELATION_ID, correlationId);
			objectMessage.setStringProperty(MessageConstants.PROPERTY_TRANSACTION_ID, transactionId);
			//objectMessage.setObjectProperty(MessageConstants.PROPERTY_MESSAGE_CONTENT, message);
			objectMessage.setJMSReplyTo(null);

			producer.send(objectMessage);
			
		} catch (NamingException e) {
			connectionFactory = null;
			destination = null;
			throw ExceptionUtil.rewrap(e);
			
		} catch (JMSException e) {
			connectionFactory = null;
			destination = null;
			throw e;

		} finally {
			// it is important to close session
			if (session != null) 
				session.close();
			try { 
				// Closing a connection automatically returns the connection and
				// its session plus producer to the resource reference pool.
				if (connection != null) 
					connection.close();
			} catch (JMSException e) {
				//ignore
			};
		}
	}
	
}
