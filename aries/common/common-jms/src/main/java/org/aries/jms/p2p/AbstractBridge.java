package org.aries.jms.p2p;

import java.io.Serializable;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.NamingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.Assert;
import org.aries.jms.JmsMessageEndpointFactory;
import org.aries.jms.JmsMessageConsumer;
import org.aries.jms.JmsMessageProducer;
import org.aries.jms.JmsSessionAdapter;
import org.aries.jms.config.JmsConsumerDescripter;
import org.aries.jms.config.JmsProducerDescripter;
import org.aries.jndi.JndiContext;


public abstract class AbstractBridge {

	protected Log log = LogFactory.getLog(getClass());

	protected JmsProducerDescripter producerDescripter;
    
	protected JmsConsumerDescripter consumerDescripter;
    
	protected JmsSessionAdapter sessionAdapter;
	
	protected JmsMessageProducer messageProducer;
    
	protected JmsMessageConsumer messageConsumer;

	protected MessageListener messageListener; 
	
	
	public AbstractBridge(JmsSessionAdapter sessionAdaptor) {
		this.sessionAdapter = sessionAdaptor;
	}
    
	public JndiContext getJndiContext() {
		return sessionAdapter.getJndiContext();
	}

	public Session getSession() {
		return sessionAdapter.getSession();
	}

    public Destination getDestination(String name) throws NamingException {
        return (Destination) getJndiContext().lookupObject(name);
    }
    
	public void setProducerDescripter(JmsProducerDescripter producerDescripter) {
		this.producerDescripter = producerDescripter;
	}

	public void setConsumerDescripter(JmsConsumerDescripter consumerDescripter) {
		this.consumerDescripter = consumerDescripter;
	}
	

    protected JmsMessageProducer createProducer() throws JMSException {
    	JmsMessageProducer producer = createProducer(producerDescripter);
    	return producer;
    }

    protected JmsMessageProducer createProducer(JmsProducerDescripter producerDescripter) throws JMSException {
		JmsMessageProducer producer = JmsMessageEndpointFactory.createProducer(sessionAdapter, producerDescripter);
    	Assert.notNull(producer, "Incomplete configuration");
    	producer.setJndiContext(sessionAdapter.getJndiContext());
        return producer;
    }

    protected JmsMessageConsumer createConsumer() throws JMSException {
    	JmsMessageConsumer consumer = createConsumer(consumerDescripter);
    	return consumer;
    }

    protected JmsMessageConsumer createConsumer(JmsConsumerDescripter consumerDescripter) throws JMSException {
		JmsMessageConsumer consumer = JmsMessageEndpointFactory.createConsumer(sessionAdapter, consumerDescripter);
    	Assert.notNull(consumer, "Incomplete configuration");
    	consumer.setJndiContext(sessionAdapter.getJndiContext());
        consumer.setMessageListener(messageListener);
        return consumer;
    }

    protected JmsMessageConsumer createConsumer(Destination destination) throws JMSException {
		JmsMessageConsumer consumer = JmsMessageEndpointFactory.createConsumer(sessionAdapter, destination);
    	Assert.notNull(consumer, "Incomplete configuration");
    	consumer.setJndiContext(sessionAdapter.getJndiContext());
        consumer.setMessageListener(messageListener);
        return consumer;
    }

    public TextMessage createTextMessage() throws JMSException {
    	return sessionAdapter.createTextMessage();
    }

    public TextMessage createTextMessage(String text) throws JMSException {
    	return sessionAdapter.createTextMessage(text);
    }

    public ObjectMessage createObjectMessage() throws JMSException {
    	return sessionAdapter.createObjectMessage();
    }

    public ObjectMessage createObjectMessage(Serializable object) throws JMSException {
    	return sessionAdapter.createObjectMessage(object);
    }

    public MapMessage createMapMessage() throws JMSException {
    	return sessionAdapter.createMapMessage();
    }
    
//	public void join() throws Exception {
//	_exchanger.join();
//}

    public void reset() throws Exception {
    	messageProducer.reset();
    	messageConsumer.reset();
    }

	public void close() {
		closeProducer();
		closeConsumer();
	}

	public void closeProducer() {
		try {
			messageProducer.close();
		} catch (Exception e) {
		}
	}

	public void closeConsumer() {
		try {
			messageConsumer.close();
		} catch (Exception e) {
		}
	}

}
