package org.aries.jms.p2p;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.management.ObjectName;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.AdapterListener;
import org.aries.Assert;
import org.aries.jms.JmsMessageEndpointFactory;
import org.aries.jms.JmsMessageProducer;
import org.aries.jms.JmsMessageConsumer;
import org.aries.jms.JmsSessionAdapter;
import org.aries.jms.JmsSessionPool;
import org.aries.jms.config.JmsExchangeDescripter;
import org.aries.jms.util.ExceptionUtil;

import common.jmx.MBeanUtil;


public class JmsMessageExchanger {

	private static final String MBEAN_NAME = "Messaging:name=MessageExchanger";

    protected final Log log = LogFactory.getLog(getClass());
    
	private JmsExchangeDescripter _specification;

//    protected JmsSessionAdapter _incomingSessionAdapter;
//
//    protected JmsSessionAdapter _outgoingSessionAdapter;
    
    //protected JmsSessionAdapter _sessionAdapter;

	protected List<AdapterListener> _sessionListeners;

    protected JmsMessageConsumer _consumer;

    protected JmsMessageProducer _producer;

    private MessageListener _listener; 

    protected ObjectName _objectName;

	private Object _mutex;

    
	public JmsMessageExchanger(JmsExchangeDescripter specification) {
		_specification = specification;
    	construct();
	}

//	public JmsMessageExchangeTransportImpl(JmsMessageExchangeSpec specification) {
//		setSpecification(specification);
//    	construct();
//	}
//
//    public JmsMessageExchangeTransportImpl(JmsSessionAdapter incomingSessionAdapter, JmsSessionAdapter outgoingSessionAdapter) {
////		_incomingSessionAdapter = incomingSessionAdapter;
////		_outgoingSessionAdapter = outgoingSessionAdapter;
//    	construct();
//    }

	private void construct() {
		_mutex = new Object();
		_objectName = createObjectName();
		_sessionListeners = new ArrayList<AdapterListener>();
	}

	public void initialize() throws Exception {
		_producer = createProducer();
		_consumer = createConsumer();
		_producer.initialize();
		_consumer.initialize();
        log.info("Started");
	}

	
    public String getMBeanName() {
        return MBEAN_NAME;
    }
    
    public ObjectName getObjectName() {
        return _objectName;
    }
    
    protected ObjectName createObjectName() {
    	return MBeanUtil.makeObjectName(MBEAN_NAME);
    }

	public JmsExchangeDescripter getSpecification() {
		return _specification;
	}

    public void setSpecification(JmsExchangeDescripter value) {
        _specification = value;
    }

//	protected JmsSessionAdapter getIncomingSessionAdapter() {
//		return _incomingSessionAdapter;
//	}
//
//	protected JmsSessionAdapter getOutgoingSessionAdapter() {
//		return _outgoingSessionAdapter;
//	}

//    public JmsSessionAdapter getSessionAdapter() {
//        return _sessionAdapter;
//    }
//
//    public void setSessionAdapter(JmsSessionAdapter value) {
//    	_sessionAdapter = value;
//    }

    public JmsMessageConsumer getConsumer() {
        return _consumer;
    }
    
    public void setConsumer(JmsMessageConsumer value) {
        _consumer = value;
    }
    
    public JmsMessageProducer getProducer() {
        return _producer;
    }

    public void setProducer(JmsMessageProducer value) {
        _producer = value;
    }

	public Destination getDestination() {
        return getProducer().getDestination();
	}

	public void setDestination(Destination value) {
    	getProducer().setDestination(value);
	}

    public Destination getReplyTo() {
        return getConsumer().getDestination();
    }

    public void setReplyTo(Destination value) {
    	getConsumer().setDestination(value);
    }
    
	public void addSessionListener(AdapterListener value) {
//		if (getIncomingSessionAdapter() != null)
//			getIncomingSessionAdapter().addAdapterListener(value);
//		if (getOutgoingSessionAdapter() != null)
//			getOutgoingSessionAdapter().addAdapterListener(value);
		synchronized (_sessionListeners) { 
			_sessionListeners.add(value);
		}
	}
	
	public void removeSessionListener(AdapterListener value) {
//		if (getIncomingSessionAdapter() != null)
//			getIncomingSessionAdapter().removeAdapterListener(value);
//		if (getOutgoingSessionAdapter() != null)
//			getOutgoingSessionAdapter().removeAdapterListener(value);
		synchronized (_sessionListeners) { 
			_sessionListeners.remove(value);
		}
	}

	protected void removeSessionListeners() {
		synchronized (_sessionListeners) { 
			List<AdapterListener> list = new ArrayList<AdapterListener>(_sessionListeners);
			Iterator<AdapterListener> iterator = list.iterator();
			while (iterator.hasNext()) {
				AdapterListener listener = iterator.next();
				_sessionListeners.remove(listener);
			}
		}
	}

	public MessageListener getMessageListener() throws Exception {
		if (getConsumer() != null)
			return getConsumer().getMessageListener();
		return _listener;
	}
	
	public void setMessageListener(MessageListener listener) throws Exception {
		if (getConsumer() != null && listener != null)
			getConsumer().setMessageListener(listener);
		_listener = listener;
	}

	protected void removeMessageListener() throws Exception {
		setMessageListener(null);
	}
	
//	public void removeMessageListener(MessageListener value) throws Exception {
//		if (getConsumer() != null)
//			getConsumer().removeMessageListener(value);
//		if (_listener == value)
//			_listener = null;
//	}

    protected JmsMessageConsumer createConsumer() throws Exception {
		String destination = _specification.getDestinationName();
		JmsSessionAdapter sessionAdapter = JmsSessionPool.getInstance().get();
		JmsMessageConsumer consumer = JmsMessageEndpointFactory.createConsumer(sessionAdapter, destination, true);
    	Assert.notNull(consumer, "Incomplete configuration");
    	consumer.setJndiContext(sessionAdapter.getJndiContext());
        consumer.setMessageListener(_listener);
        return consumer;
    }

    protected JmsMessageConsumer createConsumer(Destination destination) throws Exception {
		JmsSessionAdapter sessionAdapter = JmsSessionPool.getInstance().get();
		JmsMessageConsumer consumer = JmsMessageEndpointFactory.createConsumer(sessionAdapter, destination, true);
    	Assert.notNull(consumer, "Incomplete configuration");
    	consumer.setJndiContext(sessionAdapter.getJndiContext());
        consumer.setMessageListener(_listener);
        return consumer;
    }

    protected JmsMessageProducer createProducer() throws Exception {
		String destination = _specification.getDestinationName();
		JmsSessionAdapter sessionAdapter = JmsSessionPool.getInstance().get();
		JmsMessageProducer producer = JmsMessageEndpointFactory.createProducer(sessionAdapter, destination);
    	//else producer = JmsExecutorFactory.createProducer(getSpecification().getProducerSpec());
    	Assert.notNull(producer, "Incomplete configuration");
    	producer.setJndiContext(sessionAdapter.getJndiContext());
        return producer;
    }

    protected JmsMessageProducer createProducer(Destination destination) throws Exception {
		JmsSessionAdapter sessionAdapter = JmsSessionPool.getInstance().get();
		JmsMessageProducer producer = JmsMessageEndpointFactory.createProducer(sessionAdapter, destination);
    	Assert.notNull(producer, "Incomplete configuration");
    	producer.setJndiContext(sessionAdapter.getJndiContext());
        return producer;
    }

	public Message createMessage(String payload) throws Exception {
		JmsSessionAdapter sessionAdapter = JmsSessionPool.getInstance().get();
		return sessionAdapter.createTextMessage(payload);
	}

	public Message createMessage(Serializable payload) throws Exception {
		JmsSessionAdapter sessionAdapter = JmsSessionPool.getInstance().get();
		return sessionAdapter.createObjectMessage(payload);
	}

	//@Override
	public boolean isInitialized() {
		return getProducer().isInitialized() && getConsumer().isInitialized();
	}

	//@Override
	public boolean isValid() {
		return getProducer().isValid() && getConsumer().isValid();
	}
	
	//@Override
	public boolean isStarted() {
		return getProducer().isStarted() && getConsumer().isStarted();
	}

	//@Override
	public boolean isClosing() {
		return getProducer().isClosing() || getConsumer().isClosing();
	}
   
	//@Override
	public boolean isClosed() {
		return getProducer().isClosed() && getConsumer().isClosed();
	}

	//@Override
	public void validate() throws Exception {
    	getConsumer().validate();
    	getProducer().validate();
    }

	//@Override
	public void start() throws Exception {
		_consumer.start();
	}

	//@Override
	public void stop() throws Exception {
		_consumer.stop();
	}

	//@Override
	public void run() {
		//does nothing
	}

	//@Override
	public void join() {
		throw new UnsupportedOperationException();
	}
	
	
	/*
     * Receiving of Messages
     */

	public Message receiveMessage(long timeout) throws Exception {
		try {
			String text = "timeout="+timeout+", destination="+getConsumer().getDestination();
			if (log.isDebugEnabled() && false)
				log.debug("Waiting for message: "+text);
			Message message = getConsumer().receive(timeout);
			if (message != null)
				log.info("Received request: ID="+message.getJMSMessageID()+", "+text);
			return message;
		} catch (Exception e) {
			process(e);
            throw convert(e);
		}
	}


    /*
     * Sending of Messages
     */

	public void sendMessage(Serializable data, Long requestId) throws Exception {
		validate();
		try {
			Message message = createMessage(data);
			message.setLongProperty("RequestId", requestId);
			Destination replyto = getConsumer().getDestination();
			Assert.notNull(replyto, "ReplyTo must be specified");
			String text = "requestID="+requestId+", replyTo="+replyto;
	        log.debug("Sending message: "+text);
			message.setJMSReplyTo(replyto);
			sendMessage(message);
			log.debug("Sent message: "+text);
		} catch (Exception e) {
			process(e);
            throw convert(e);
		}
	}

	public void sendMessage(Message message) throws Exception {
		getProducer().send(message);
	}

	public void sendMessage(Message message, Destination destination) throws Exception {
		getProducer().send(message, destination);
	}


	
//    /*
//     * Sending of RESPONSE
//     */
//
//	public void sendResponse(Destination destination, Serializable output, Long requestId, String messageId) throws Exception {
//		validate();
//		try {
//	        Message response = createMessage(output);
//	        response.setJMSCorrelationID(messageId);
//			response.setObjectProperty("RequestId", requestId);
//			Assert.isNotNull(destination, "Destination must be specified");
//			String text = "requestID="+requestId+", destination="+destination;
//	        log.debug("Sending response: "+text);
//	        sendResponse(destination, response);
//	        log.debug("Sent response: "+text);
//		} catch (Exception e) {
//			process(e);
//            throw convert(e);
//		}
//	}


    protected void process(Throwable e) {
	    log.error(e);
	    try {
		    reset();
	    } catch (Throwable t) {
	    	log.error(t);
	    }
	}

	protected JMSException convert(Throwable e) {
		return ExceptionUtil.getAs(e);
	}

	//@Override
    public void reset() throws Exception {
    	synchronized (_mutex) {
    		close();
    	}
	}

	//@Override
    public void close() throws Exception {
    	synchronized (_mutex) {
	        JmsMessageProducer producer = getProducer();
	        JmsMessageConsumer consumer = getConsumer();
			removeMessageListener();
			removeSessionListeners();
			log.trace("Closing exchanger producer");
			if (producer != null)
	        	producer.close();
			log.trace("Closing exchanger consumer");
			if (consumer != null)
	        	consumer.close();
	        log.info("Closed exchanger all");
	    }
    }

}
