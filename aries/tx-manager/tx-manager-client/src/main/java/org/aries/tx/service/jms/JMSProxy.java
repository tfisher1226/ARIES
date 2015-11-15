package org.aries.tx.service.jms;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.naming.NamingException;

import org.aries.Assert;
import org.aries.Handler;
import org.aries.common.AbstractMessage;
import org.aries.jms.client.JmsClient;
import org.aries.jms.util.MessageUtil;
import org.aries.message.util.MessageConstants;
import org.aries.runtime.BeanContext;


public class JMSProxy extends JmsClient {

	private String serviceId;

	
	public JMSProxy() {
		//nothing for now
	}
	
	public JMSProxy(String serviceId) {
		this.serviceId = serviceId;
	}
	
	//@Override
	public String getServiceId() {
		return serviceId;
	}

	//@Override
	public String getServiceUrl() {
		return getProviderUrl();
	}


	@Override
	public void send(Serializable payload) throws NamingException, JMSException {
		String correlationId = getCorrelationId(payload);
		String transactionId = getTransactionId(payload);
		if (payload instanceof AbstractMessage) {
			AbstractMessage contentMessage = (AbstractMessage) payload;
			correlationId = contentMessage.getCorrelationId();
			transactionId = contentMessage.getTransactionId();
		}
		
		//org.aries.message.Message applicationMessage = createMessage("applicationMessage");
		//applicationMessage.setCorrelationId(correlationId);
		send(payload, correlationId, transactionId);
	}
	
	protected void send(Object replyToDestination, Serializable payload) throws NamingException, JMSException {
		Assert.notNull(replyToDestination, "ReplyTo destination is null");
		if (replyToDestination instanceof String) {
			super.send((String) replyToDestination, payload);
			
		} else if (replyToDestination instanceof Destination) {
			super.send((Destination) replyToDestination, payload);
		}
	}
	

	protected org.aries.message.Message createMessage(String operationName) {
		org.aries.message.Message message = new org.aries.message.Message();
		//message.addPart("operationName", operationName);
		message.addPart(MessageConstants.PROPERTY_SERVICE_ID, getServiceId());
		message.addPart(MessageConstants.PROPERTY_OPERATION_NAME, operationName);
		message.addPart(MessageConstants.PROPERTY_USER_NAME, BeanContext.get("org.aries.userName"));
		return message;
	}
	
	protected String getReplyToDestination(AbstractMessage message, String serviceName) {
		Map<String, String> replyToDestinations = message.getReplyToDestinations();
//		if (replyToDestinations == null)
//			System.out.println();
		Assert.notNull(replyToDestinations, "ReplyTo map not found");
		String replyToDestination = replyToDestinations.get(serviceName);
		if (replyToDestination == null)
			System.out.println();
		Assert.notNull(replyToDestination, "ReplyTo destination not found: "+serviceName);
		String jndiName = "queue/" + replyToDestination;
		return jndiName;
	}

//	@Override
//	public Message invoke(Message request) throws Exception {
//		return super.invoke(request);
//	}

	
	private Map<String, Handler<?>> handlers;
	
	public <T extends Serializable> void addResponseHandler(String messageClass, Handler<T> handler) {
		if (handlers == null)
			handlers = new HashMap<String, Handler<?>>();
		handlers.put(messageClass, handler);
		if (getMessageListener2() == null)
			setMessageListener2(createMessageListener());
	}

	protected MessageListener createMessageListener() {
		return new MessageListener() {
			public void onMessage(Message message) {
				try {
					String jmsMessageID = message.getJMSMessageID();
					Object response = MessageUtil.getPart(message, "response");
					Assert.notNull(response, "Response not found for: "+getServiceId());
					Class<?> classObject = response.getClass();
					String key = classObject.getSimpleName();
					Handler handler = handlers.get(key);
					Assert.notNull(handler, "Handler not found: "+getServiceId()+": "+key);
					handler.handle(classObject.cast(response));
					
				} catch (JMSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
	}

	
	protected String getCorrelationId(Serializable data) {
		if (data instanceof org.aries.message.Message) {
			//org.aries.message.Message message = (org.aries.message.Message) data;
			//return (String) message.getCorrelationId();
			String correlationId = MessageUtil.getPart(data, "correlationId");
			return correlationId;
		}
		if (data instanceof AbstractMessage) {
			AbstractMessage message = (AbstractMessage) data;
			return message.getCorrelationId();
		}
		return super.getCorrelationId(data);
	}

	protected String getTransactionId(Serializable data) {
		if (data instanceof AbstractMessage) {
			AbstractMessage message = (AbstractMessage) data;
			return message.getTransactionId();
		}
		return super.getTransactionId(data);
	}
	
}
