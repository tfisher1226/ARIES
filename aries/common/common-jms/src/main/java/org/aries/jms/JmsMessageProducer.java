package org.aries.jms;

import javax.jms.Destination;
import javax.jms.InvalidDestinationException;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.naming.NamingException;

import org.aries.jms.config.JmsProducerDescripter;


public class JmsMessageProducer extends AbstractEndpoint {

    private static final String MBEAN_NAME = "MessageEndpoint:name=Producer";
    
    private MessageProducer messageProducer; 
	

    public JmsMessageProducer(JmsSessionAdapter sessionAdapter, JmsProducerDescripter descriptor) {
    	super(sessionAdapter, descriptor);
    	construct();
    }

	private void construct() {
		//nothing for now
	}

    public String getMBeanName() {
    	return MBEAN_NAME;
    }
    
    public MessageProducer getProducer() {
    	return messageProducer;
    }
    
    public JmsProducerDescripter getDescripter() {
    	return (JmsProducerDescripter) endpointDescripter;
    }
	
	
	@Override
    protected void assureEndpoint() throws JMSException, NamingException {
		JmsProducerDescripter descriptor = getDescripter();
		if (forceResetRequested) {
			messageProducer.close();
			messageProducer = null;
		}
		if (messageProducer == null || !isInitialized()) {
			if (destination != null) {
				messageProducer = sessionAdapter.createProducer(descriptor, destination);
			} else if (descriptor.getDestinationName() != null) {
				messageProducer = sessionAdapter.createProducer(descriptor);
			}
	    }
	}
	
	
//    public void send(String data) throws Exception {
//        send(data, null);
//    }
//
//    public void send(Serializable data) throws Exception {
//        send(data, null);
//    }
//    
//    public void send(String data, String serviceId) throws Exception {
//        //_serviceId = serviceId;
//    	sendData(data);
//    }
//
//    public void send(Serializable data, String serviceId) throws Exception {
//        //_serviceId = serviceId;
//    	sendData(data);
//    }
//
//    protected void sendData(String data) throws Exception {
//        TextMessage message = sessionAdapter.createTextMessage(data);
//        //message.setStringProperty(getSelectorKey(), getSelectorValue());
//        sendMessage(message);
//    }
//
//    protected void sendData(Serializable data) throws Exception {
//        ObjectMessage message = sessionAdapter.createObjectMessage(data);
//        //message.setStringProperty(getSelectorKey(), getSelectorValue());
//        sendMessage(message);
//    }

    
    public void send(Message message) throws Exception {
        try {
        	validate();
        	if (messageProducer == null)
        		reset();
        	if (replyto != null && message.getJMSReplyTo() == null)
        		message.setJMSReplyTo(replyto);
            //if (log.isTraceEnabled())
                log.trace("Sending message");
            messageProducer.send(message);
            //if (log.isTraceEnabled())
                log.trace("Sent message");
        } catch (Throwable e) {
        	if (isClosing())
        		return;
        	process(e);
            throw convert(e);
        }
    }
	
    public void send(Message message, Destination destination) throws Exception {
        try {
        	validate();
//        	if (_producer == null)
//        		reset();
        	if (replyto != null && message.getJMSReplyTo() == null)
        		message.setJMSReplyTo(replyto);
            if (log.isTraceEnabled())
                log.trace("Sending message");
            
            Session session = sessionAdapter.getSession();
            MessageProducer producer = JmsMessageEndpointFactory.createMessageProducer(session, getDescripter(), destination);
            producer.send(message);
            producer.close();
            
            if (log.isTraceEnabled())
                log.trace("Sent message");
            
        } catch (InvalidDestinationException e) {
        	//processExpiredMessage(e);
        
        } catch (Throwable e) {
        	if (isClosing())
        		return;
        	process(e);
            throw convert(e);
        }
    }

    public void close() throws JMSException {
		setClosing(true);
		MessageProducer producer = getProducer();
		if (producer != null) {
			producer.close();
			this.messageProducer = null;
		}
		super.close();
    }
    
}
