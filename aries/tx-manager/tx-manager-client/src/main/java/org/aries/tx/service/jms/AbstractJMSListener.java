package org.aries.tx.service.jms;

import java.io.Serializable;
import java.util.Properties;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.aries.Assert;
import org.aries.jms.util.MessageUtil;
import org.aries.message.util.MessageConstants;
import org.aries.tx.service.AbstractListener;
import org.aries.util.ExceptionUtil;


public abstract class AbstractJMSListener extends AbstractListener {

	protected Properties initialContextProperties;
	
	protected String initialContextFactoryClass;

	protected String connectionFactoryName;
	
	protected ConnectionFactory connectionFactory;

	//@Resource(mappedName = "java:/JmsXA")
    //private XAConnectionFactory xaConnectionFactory;
	
	protected String providerUrl;

	protected String destinationName;

	protected Destination destination;

	
	protected String getCorrelationId(Message jmsMessage) {
		try {
			String jmsMessageID = jmsMessage.getJMSMessageID();
			String correlationId = jmsMessage.getStringProperty(MessageConstants.PROPERTY_CORRELATION_ID);
			if (correlationId == null) {
				Assert.notNull(jmsMessage, "Message must be specified");
				Object object = MessageUtil.getObjectFromMessage(jmsMessage);
				if (object != null && object instanceof org.aries.message.Message) {
					org.aries.message.Message message = (org.aries.message.Message) object;
					correlationId = message.getPart("correlationId");
				}
			}
			Assert.notNull(correlationId, "CorrelationId not found in message: "+jmsMessageID);
			return correlationId;
		} catch (JMSException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void initialize() throws Exception {
	}

	@Override
	public void reset() throws Exception {
	}

	@Override
	public void close() throws Exception {
	}

	public void sendResponse(Destination destination, Serializable payload) throws JMSException {
		sendResponse(destination, payload, null, null);
	}
	
	public void sendResponse(Destination destination, Serializable payload, String correlationId, String transactionId) throws JMSException {
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

			ObjectMessage objectMessage = session.createObjectMessage(payload);
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

	

	@Override
	public <T extends Serializable> T invoke(Serializable payload,
			String correlationId, long timeout) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String getModuleId() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String getServiceId() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Object getDelegate() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
