package org.aries.jms.xa;

import javax.jms.Destination;
import javax.jms.JMSException;

import org.aries.jms.JmsMessageEndpointFactory;
import org.aries.jms.config.JmsConsumerDescripter;
import org.aries.jms.config.JmsProducerDescripter;
import org.aries.jndi.JndiContext;
import org.aries.jndi.JndiProxy;


public class JmsXATransportFactory {

	//private static Log log = LogFactory.lookup(XAJmsExecutorFactory.class);	
	
	//for now just initialize the _instance at class load time
	private static JmsXATransportFactory _instance = new JmsXATransportFactory();
	
	public static JmsXATransportFactory getInstance() {
		return _instance;
	}
	
	
	public JmsXATransportFactory() {
		//nothing for now
	}

	public static JndiContext createJNDIContext(String url, String factory) {
    	JndiProxy jndiContext = new JndiProxy();
    	jndiContext.setConnectionUrl(url);
    	jndiContext.setContextFactory(factory);
    	return jndiContext;
    }
	
	public JmsXAMessageProducer createXAProducer(JmsXASessionAdapter sessionAdapter, JmsProducerDescripter specification) throws JMSException {
		JmsXAMessageProducer fixture = new JmsXAMessageProducer(sessionAdapter, specification);
		return fixture;
	}

	public JmsXAMessageConsumer createXAConsumer(JmsXASessionAdapter sessionAdapter, JmsConsumerDescripter specification) throws JMSException {
		JmsXAMessageConsumer fixture = new JmsXAMessageConsumer(sessionAdapter, specification);
		return fixture;
	}
	
//	public JmsXAMessageProducer createXAProducer(JmsMessageProducerDescripter specification, Destination destination) throws JMSException {
//		JmsXAMessageProducer producer = new JmsXAMessageProducer(specification, destination);
//		return producer;
//	}

//	public JmsXAMessageConsumer createXAConsumer(JmsMessageConsumerDescripter specification, Destination destination) throws JMSException {
//		JmsXAMessageConsumer consumer = new JmsXAMessageConsumer(specification, destination);
//		return consumer;
//	}

    public JmsXAMessageProducer createXAProducer(JmsXASessionAdapter sessionAdapter) throws JMSException {
    	JmsProducerDescripter specification = JmsMessageEndpointFactory.createProducerDescripter(sessionAdapter.getDescripter());
		return createXAProducer(sessionAdapter, specification);
    }

    public JmsXAMessageConsumer createXAConsumer(JmsXASessionAdapter sessionAdapter) throws JMSException {
    	JmsConsumerDescripter specification = JmsMessageEndpointFactory.createConsumerDescripter(sessionAdapter.getDescripter());
		return createXAConsumer(sessionAdapter, specification);
    }

    public JmsXAMessageProducer createXAProducer(JmsXASessionAdapter sessionAdapter, String destination) throws JMSException {
    	JmsProducerDescripter specification = JmsMessageEndpointFactory.createProducerDescripter(sessionAdapter.getDescripter(), destination);
		return createXAProducer(sessionAdapter, specification);
    }

    public JmsXAMessageConsumer createXAConsumer(JmsXASessionAdapter sessionAdapter, String destination) throws JMSException {
    	JmsConsumerDescripter specification = JmsMessageEndpointFactory.createConsumerDescripter(sessionAdapter.getDescripter(), destination);
		return createXAConsumer(sessionAdapter, specification);
    }

    public JmsXAMessageProducer createXAProducer(JmsXASessionAdapter sessionAdapter, Destination destination) throws JMSException {
    	JmsProducerDescripter specification = JmsMessageEndpointFactory.createProducerDescripter(sessionAdapter.getDescripter(), destination);
		return createXAProducer(sessionAdapter, specification);
    }

    public JmsXAMessageConsumer createXAConsumer(JmsXASessionAdapter sessionAdapter, Destination destination) throws JMSException {
		JmsConsumerDescripter specification = JmsMessageEndpointFactory.createConsumerDescripter(sessionAdapter.getDescripter(), destination);
		return createXAConsumer(sessionAdapter, specification);
    }

//    public JmsXAMessageProducer createXAProducer(JndiContext jndiContext, JmsMessageProducerDescripter specification) throws JMSException {
//    	JmsXAMessageProducer producer = createXAProducer(specification);
//    	producer.setJndiContext(jndiContext);
//		return producer;
//    }
//
//    public JmsXAMessageConsumer createXAConsumer(JndiContext jndiContext, JmsMessageConsumerDescripter specification) throws JMSException {
//    	JmsXAMessageConsumer consumer = createXAConsumer(specification);
//		consumer.setJndiContext(jndiContext);
//		return consumer;
//    }

}
