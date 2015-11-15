package org.aries.jms;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.naming.NamingException;

import org.aries.jms.config.JmsConsumerDescripter;


public class JmsMessageConsumer extends AbstractEndpoint {

	private static final String MBEAN_NAME = "MessageEndpoint:name=Consumer";

	private MessageConsumer messageConsumer; 

	private MessageListener messageListener;

	private boolean autoRecoveryEnabled; 

	
	public JmsMessageConsumer(JmsSessionAdapter sessionAdapter, JmsConsumerDescripter descriptor) {
    	super(sessionAdapter, descriptor);
		construct();
	}

	public JmsMessageConsumer(JmsSessionAdapter sessionAdapter, JmsConsumerDescripter descriptor, Destination destination) {
    	super(sessionAdapter, descriptor);
    	this.destination = destination;
    	construct();
    }

	private void construct() {
		setAutoRecoveryEnabled(true);
	}

	public String getMBeanName() {
		return MBEAN_NAME;
	}

	public MessageConsumer getConsumer() {
		return messageConsumer;
	}

    public JmsConsumerDescripter getDescripter() {
    	return (JmsConsumerDescripter) endpointDescripter;
    }

	public boolean isAutoRecoveryEnabled() {
		return autoRecoveryEnabled;
	}

	public void setAutoRecoveryEnabled(boolean value) {
		autoRecoveryEnabled = value;
	}

	public MessageListener getMessageListener() throws Exception {
		if (getConsumer() != null)
			return getConsumer().getMessageListener();
		return messageListener;
	}

	public void setMessageListener(MessageListener listener) throws JMSException {
		//log.info(">>>"+this+", "+listener);
		if (getConsumer() != null)
			getConsumer().setMessageListener(listener);
		this.messageListener = listener;
	}

	protected void assureEndpoint() throws JMSException, NamingException {
		JmsConsumerDescripter descriptor = getDescripter();
		if (forceResetRequested) {
			messageConsumer.close();
			messageConsumer = null;
		}
		if (messageConsumer == null || !isInitialized()) {
			if (destination != null) {
				messageConsumer = sessionAdapter.createConsumer(descriptor, destination);
			} else {
				messageConsumer = sessionAdapter.createConsumer(descriptor);
			}
		}
		if (messageConsumer != null) {
			messageConsumer.setMessageListener(messageListener);
			//log.info(">>>"+this+", "+messageListener);
		}
	}
	

//	public Serializable receive() throws Exception {
//		return receive(0);
//	}
//
//	public Serializable receive(long timeout) throws Exception {
//		Message message = receiveMessage(timeout);
//		Serializable payload = MessageUtil.extractPayload(message);
//		return payload;
//	}


	public Message receive() throws JMSException {
		return receive(0);
	}

	public Message receive(long timeout) throws JMSException {
		//int retryInterval = 1000;
		//for (int i=0;; i += retryInterval) {
		//	if (log.isTraceEnabled())
		//		log.trace("Polling for messages...");
		
			Message message = getConsumer().receive(timeout);
			return message;

		//	//Message message = poll(retryInterval);
		//	if (message != null)
		//		return message;
		//	if (timeout > 0 && i > timeout)
		//		return null;
		//	if (forceStopRequested)
		//		return null;
		//}
	}

	protected Message poll(int timeout) throws JMSException {
		synchronized (mutex) {
			try {
				validate();
				assureEndpoint();
				if (getConsumer() == null)
					throw new Exception("Not initialized");
				if (log.isTraceEnabled())
					log.trace("Waiting for messages...");
				Message message = getConsumer().receive(timeout);
				if (log.isTraceEnabled() && message != null)
					log.trace("Received message: "+message.getClass());
//				if (message != null) //TEMP only
//					log.trace("Received message: "+message.getClass());
				return message;
			} catch (Throwable e) {
				if (isClosed() || isClosing())
					return null;
				if (isAutoRecoveryEnabled())
					process(e);
				throw convert(e);
			}
		}
	}

	
	public void close() throws JMSException {
		if (isClosed())
			return;
		setClosing(true);
		synchronized (mutex) {
			MessageConsumer consumer = getConsumer();
			if (consumer != null) {
				//consumer.setMessageListener(null);
				consumer.close();
				this.messageConsumer = null;
			}
			super.close();
		}
	}
	

}
