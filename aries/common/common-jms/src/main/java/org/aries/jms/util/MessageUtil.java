package org.aries.jms.util;

import java.io.Serializable;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;

import org.aries.Assert;
import org.aries.message.util.MessageConstants;
import org.aries.util.ExceptionUtil;


public class MessageUtil {
    
	@SuppressWarnings("unchecked")
	public static <T> T getPart(Serializable message, String name) {
		T payload = null;
		if (message instanceof Message) {
			try {
				payload = getPart((Message) message, name);
			} catch (JMSException e) {
				RuntimeException cause = org.aries.util.ExceptionUtil.rewrap(e);
				throw cause;
			}
		} else if (message instanceof org.aries.message.Message) {
			org.aries.message.Message ariesMessage = (org.aries.message.Message) message;
			payload = ariesMessage.getPart(name);
			if (payload == null)
				payload = ariesMessage.getPart(MessageConstants.PROPERTY_MESSAGE_PAYLOAD);
		} else {
			payload = (T) message;
		}
		return payload;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getPart(Message jmsMessage, String name) throws JMSException {
		Assert.notNull(jmsMessage, "Message must be specified");
		Object object = getObjectFromMessage(jmsMessage);
		Assert.notNull(object, "Object not found in message");
		if (object == null)
			return null;
		T part = null;
		if (object instanceof org.aries.message.Message) {
			org.aries.message.Message message = (org.aries.message.Message) object;
			part = message.getPart(name);
		} else part = (T) object;
		//Assert.notNull(part, "Part not found in message");
		return part;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getPayload(Message jmsMessage) throws JMSException {
		Assert.notNull(jmsMessage, "Message must be specified");
		Object object = getObjectFromMessage(jmsMessage);
		Assert.notNull(object, "Object not found in message");
		if (object == null)
			return null;
		T payload = null;
		if (object instanceof org.aries.message.Message) {
			org.aries.message.Message message = (org.aries.message.Message) object;
			payload = message.getPart(MessageConstants.PROPERTY_MESSAGE_PAYLOAD);
		} else payload = (T) object;
		Assert.notNull(payload, "Payload not found in message");
		return payload;
	}
	
	public static String getCorrelationIdFromMessage(Message jmsMessage) throws JMSException {
		String correlationId = jmsMessage.getStringProperty(MessageConstants.PROPERTY_CORRELATION_ID);
		return correlationId;
	}
	
	public static String getCorrelationIdFromMessageOLD(Message jmsMessage) throws JMSException {
		org.aries.message.Message message = getObjectFromMessage(jmsMessage);
		Assert.notNull(message, "CorrelationId not found in message");
		String correlationId  = message.getPart(MessageConstants.PROPERTY_CORRELATION_ID);
		//String correlationId = message.getStringProperty("CorrelationId");
		//Assert.notNull(correlationId, "CorrelationId not found");
		return correlationId;
	}

	public static String getTransactionIdFromMessageOLD(Message jmsMessage) throws JMSException {
		org.aries.message.Message message = getObjectFromMessage(jmsMessage);
		Assert.notNull(message, "TransactionId not found in message");
		String transactionId  = message.getPart(MessageConstants.PROPERTY_TRANSACTION_ID);
		//String transactionId = jmsMessage.getStringProperty("TransactionId");
		//Assert.notNull(transactionId, "TransactionId not found");
		return transactionId;
	}

	public static Object getTransactionContextFromMessageOLD(Message jmsMessage) throws JMSException {
		org.aries.message.Message message = getObjectFromMessage(jmsMessage);
		Assert.notNull(message, "TransactionContext not found in message");
		Object transactionContext = message.getPart(MessageConstants.PROPERTY_TRANSACTION_CONTEXT);
		//Object transactionContext = jmsMessage.getObjectProperty("TransactionContext");
		Assert.notNull(transactionContext, "TransactionContext not found");
		return transactionContext;
	}

	@SuppressWarnings("unchecked")
	public static <T> T getPayloadFromMessage(Message jmsMessage) throws JMSException {
		Assert.notNull(jmsMessage, "Message must be specified");
		Object object = getObjectFromMessage(jmsMessage);
		Assert.notNull(object, "Payload not found in message");
		T payload = null;
		if (object instanceof org.aries.message.Message)
			payload = ((org.aries.message.Message) object).getPart(MessageConstants.PROPERTY_MESSAGE_PAYLOAD);
		else payload = (T) object;
		if (payload == null)
			payload = (T) object;
		return payload;
	}
	
//	@SuppressWarnings("unchecked")
//	public static <T> T getPayloadFromMessage(Message jmsMessage) throws JMSException {
//		Assert.notNull(jmsMessage, "Message must be specified");
//		Object object = getObjectFromMessage(jmsMessage);
//		T payload = null;
//		if (object instanceof org.aries.message.Message)
//			payload = ((org.aries.message.Message) object).getPart(MessageConstants.PROPERTY_MESSAGE_PAYLOAD);
//		else payload = (T) object;
//		Assert.notNull(payload, "Payload not found in message");
//		return payload;
//	}
	
	public static <T> T getPayloadFromMessageNOTUSED(Message jmsMessage) throws JMSException {
		Assert.notNull(jmsMessage, "Message must be specified");
		org.aries.message.Message message = getObjectFromMessage(jmsMessage);
		Assert.notNull(message, "Payload not found in message");
		T payload = message.getPart(MessageConstants.PROPERTY_MESSAGE_PAYLOAD);
		return payload;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getObjectFromMessage(Message message) throws JMSException {
		Assert.notNull(message, "Message must be specified");
		if (message instanceof ObjectMessage) {
			ObjectMessage objectMessage = (ObjectMessage) message;
			T object = (T) objectMessage.getObject();
			return object;
		} else {
			T object = (T) message.getObjectProperty(MessageConstants.PROPERTY_MESSAGE_CONTENT);
			return object;
		}
	}

    public static Serializable extractPayload(Message message) throws JMSException {
    	Serializable payload = null;
        if (message != null && message instanceof TextMessage) {
        	TextMessage textMessage = (TextMessage) message;
        	payload = textMessage.getText();
        }
        if (message != null && message instanceof ObjectMessage) {
            ObjectMessage objectMessage = (ObjectMessage) message;
            payload = objectMessage.getObject();
        }
        return payload;
    }

	public static Destination getJMSReplyTo(Message message) {
		try {
			return message.getJMSReplyTo();
		} catch (JMSException e) {
			throw ExceptionUtil.createRuntimeException(e);
		}
	}
    
    public static String toString(Message message) throws JMSException {
    	StringBuffer buf = new StringBuffer();
    	buf.append("Priority="+message.getJMSPriority()+",");
    	buf.append("DeliveryMode="+message.getJMSDeliveryMode()+",");
    	buf.append("Expiration="+message.getJMSExpiration());
    	return buf.toString();
    }

}
