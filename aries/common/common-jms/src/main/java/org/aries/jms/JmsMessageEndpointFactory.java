package org.aries.jms;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.Assert;
import org.aries.jms.config.JmsConnectionDescripter;
import org.aries.jms.config.JmsConsumerDescripter;
import org.aries.jms.config.JmsProducerDescripter;
import org.aries.jms.config.JmsSessionDescripter;


public class JmsMessageEndpointFactory {

	private static Log log = LogFactory.getLog(JmsMessageEndpointFactory.class);	
	
	private static String DEFAULT_DESTINATION = "queue/A";
	
	private static String DEFAULT_CONNECTION_FACTORY = "/ConnectionFactory";
	
	//private static String DEFAULT_DESTINATION = "topic/testTopic";
	
//	//for now just initialize the _instance at class load time
//	private static JmsExecutorFactory _instance = new JmsExecutorFactory();
//	
//	public static JmsExecutorFactory getInstance() {
//		return _instance;
//	}
//	
//	
//	private JmsExecutorFactory() {
//		if (_instance != null)
//			throw new IllegalStateException("Already initialized");
//		_instance = this;
//	}
	
    public static JmsConnectionDescripter createConnectionDescripter(String clientId) {
    	JmsConnectionDescripter connectionDescripter = new JmsConnectionDescripter();
    	connectionDescripter.setConnectionFactory(DEFAULT_CONNECTION_FACTORY);
    	connectionDescripter.setClientId(clientId);
    	//connectionDescripter.setUsername("username");
    	//connectionDescripter.setPassword("password");
    	return connectionDescripter;
    }
    
    public static JmsSessionDescripter createSessionDescripter() {
    	JmsSessionDescripter sessionDescripter = new JmsSessionDescripter();
    	return sessionDescripter;
    }

    public static JmsSessionDescripter createSessionDescripter(JmsConnectionDescripter connectionDescripter) {
    	JmsSessionDescripter sessionDescripter = new JmsSessionDescripter();
    	sessionDescripter.setConnectionDescripter(connectionDescripter);
    	return sessionDescripter;
    }
    
    public static JmsProducerDescripter createProducerDescripter() {
    	JmsProducerDescripter specification = new JmsProducerDescripter();
    	//specification.setDestinationName(DEFAULT_DESTINATION);
        return specification;
    }

    public static JmsConsumerDescripter createConsumerDescripter() {
        JmsConsumerDescripter specification = new JmsConsumerDescripter();
    	//specification.setDestinationName(DEFAULT_DESTINATION);
        return specification;
    }

    public static JmsProducerDescripter createProducerDescripter(String destination) {
    	JmsSessionDescripter sessionDescripter = createSessionDescripter();
    	return createProducerDescripter(sessionDescripter, destination);
    }
    
    public static JmsConsumerDescripter createConsumerDescripter(String destination) {
    	JmsSessionDescripter sessionDescripter = createSessionDescripter();
    	return createConsumerDescripter(sessionDescripter, destination);
    }
    
    public static JmsProducerDescripter createProducerDescripter(JmsSessionDescripter sessionDescripter) {
    	JmsProducerDescripter producerDescripter = createProducerDescripter();
    	producerDescripter.setSessionDescripter(sessionDescripter);
    	return producerDescripter;
    }

    public static JmsConsumerDescripter createConsumerDescripter(JmsSessionDescripter sessionDescripter) {
    	JmsConsumerDescripter specification = createConsumerDescripter();
    	specification.setSessionDescripter(sessionDescripter);
    	return specification;
    }

    public static JmsProducerDescripter createProducerDescripter(JmsSessionDescripter sessionDescripter, String destination) {
    	JmsProducerDescripter specification = createProducerDescripter(sessionDescripter);
    	specification.setDestinationName(destination);
    	return specification;
    }

    public static JmsProducerDescripter createProducerDescripter(JmsSessionDescripter sessionDescripter, Destination destination) {
    	JmsProducerDescripter specification = createProducerDescripter(sessionDescripter);
    	specification.setDestination(destination);
    	return specification;
    }

    public static JmsConsumerDescripter createConsumerDescripter(JmsSessionDescripter sessionDescripter, String destination) {
    	JmsConsumerDescripter specification = createConsumerDescripter(sessionDescripter);
    	specification.setDestinationName(destination);
    	return specification;
    }

    public static JmsConsumerDescripter createConsumerDescripter(JmsSessionDescripter sessionDescripter, Destination destination) {
    	JmsConsumerDescripter specification = createConsumerDescripter(sessionDescripter);
    	specification.setDestination(destination);
    	return specification;
    }

    public static JmsConsumerDescripter createConsumerDescripter(JmsSessionDescripter sessionDescripter, String destination, boolean noLocalReceipt) {
    	JmsConsumerDescripter specification = createConsumerDescripter(sessionDescripter);
    	specification.setDestinationName(destination);
    	specification.setNoLocalReceipt(noLocalReceipt);
    	return specification;
    }

    public static JmsConsumerDescripter createConsumerDescripter(JmsSessionDescripter sessionDescripter, Destination destination, boolean noLocalReceipt) {
    	JmsConsumerDescripter specification = createConsumerDescripter(sessionDescripter);
    	specification.setDestination(destination);
    	specification.setNoLocalReceipt(noLocalReceipt);
    	return specification;
    }

    
    /*
     * Producer factory methods
     */
    
    public static JmsMessageProducer createProducer(String destination) throws Exception {
		JmsSessionAdapter session = JmsSessionPool.getInstance().get();
		JmsMessageProducer producer = JmsMessageEndpointFactory.createProducer(session, destination);
    	Assert.notNull(producer, "Incomplete configuration");
    	producer.setJndiContext(session.getJndiContext());
        return producer;
    }

    public static JmsMessageProducer createProducer(Destination destination) throws Exception {
		JmsSessionAdapter session = JmsSessionPool.getInstance().get();
		JmsMessageProducer producer = JmsMessageEndpointFactory.createProducer(session, destination);
    	Assert.notNull(producer, "Incomplete configuration");
    	producer.setJndiContext(session.getJndiContext());
        return producer;
    }

	public static JmsMessageProducer createProducer(JmsProducerDescripter specification) throws JMSException {
		JmsSessionAdapter session = JmsSessionPool.getInstance().get();
		JmsMessageProducer fixture = new JmsMessageProducer(session, specification);
		return fixture;
	}

	public static JmsMessageProducer createProducer(JmsSessionAdapter session, JmsProducerDescripter Descripter) throws JMSException {
		JmsMessageProducer fixture = new JmsMessageProducer(session, Descripter);
		return fixture;
	}

    public static JmsMessageProducer createProducer(JmsSessionAdapter session, String destination) throws JMSException {
		JmsProducerDescripter specification = createProducerDescripter(session.getDescripter(), destination);
		return createProducer(session, specification);
    }

    public static JmsMessageProducer createProducer(JmsSessionAdapter session, Destination destination) throws JMSException {
		JmsProducerDescripter specification = createProducerDescripter(session.getDescripter(), destination);
		JmsMessageProducer producer = (JmsMessageProducer) createProducer(session, specification);
		return producer;
    }
    
	
	/*
	 * Consumer factory methods
	 */
	

    public static JmsMessageConsumer createConsumer(String destination, MessageListener listener) throws Exception {
    	JmsSessionAdapter session = JmsSessionPool.getInstance().get();
		JmsMessageConsumer consumer = JmsMessageEndpointFactory.createConsumer(session, destination, true);
    	Assert.notNull(consumer, "Incomplete configuration");
    	consumer.setJndiContext(session.getJndiContext());
        consumer.setMessageListener(listener);
        return consumer;
    }

    public static JmsMessageConsumer createConsumer(Destination destination, MessageListener listener) throws Exception {
    	JmsSessionAdapter session = JmsSessionPool.getInstance().get();
		JmsMessageConsumer consumer = JmsMessageEndpointFactory.createConsumer(session, destination, true);
    	Assert.notNull(consumer, "Incomplete configuration");
    	consumer.setJndiContext(session.getJndiContext());
        consumer.setMessageListener(listener);
        return consumer;
    }

	public static JmsMessageConsumer createConsumer(JmsSessionAdapter sessionAdapter, JmsConsumerDescripter Descripter) throws JMSException {
		JmsMessageConsumer fixture = new JmsMessageConsumer(sessionAdapter, Descripter);
		return fixture;
	}

//	public static JmsMessageProducer createProducer(JmsMessageProducerDescripter specification, Destination destination) throws JMSException {
//		JmsMessageProducer producer = new JmsMessageProducerImpl(specification, destination);
//		return producer;
//	}
//
//	public static JmsMessageConsumer createConsumer(JmsMessageConsumerDescripter specification, Destination destination) throws JMSException {
//		JmsMessageConsumer consumer = new JmsMessageConsumerImpl(specification, destination);
//		return consumer;
//	}

//    public static JmsMessageProducer createProducer(JmsSessionAdapter sessionAdapter) throws JMSException {
//		JmsMessageProducerDescripter specification = createProducerDescripter(sessionAdapter.getDescripterification());
//		return createProducer(sessionAdapter, specification);
//    }
//
//    public static JmsMessageConsumer createConsumer(JmsSessionAdapter sessionAdapter) throws JMSException {
//		JmsMessageConsumerDescripter specification = createConsumerDescripter(sessionAdapter.getDescripterification());
//		return createConsumer(sessionAdapter, specification);
//    }

    public static JmsMessageConsumer createConsumer(JmsSessionAdapter sessionAdapter, String destination) throws JMSException {
		return createConsumer(sessionAdapter, destination, false);
    }

    public static JmsMessageConsumer createConsumer(JmsSessionAdapter sessionAdapter, String destination, boolean noLocalReceipt) throws JMSException {
		JmsConsumerDescripter Descripter = createConsumerDescripter(sessionAdapter.getDescripter(), destination);
		Descripter.setNoLocalReceipt(noLocalReceipt);
		return createConsumer(sessionAdapter, Descripter);
    }

    public static JmsMessageConsumer createConsumer(JmsSessionAdapter sessionAdapter, Destination destination) throws JMSException {
    	return createConsumer(sessionAdapter, destination, false);
    }
    
    public static JmsMessageConsumer createConsumer(JmsSessionAdapter sessionAdapter, Destination destination, boolean noLocalReceipt) throws JMSException {
		JmsConsumerDescripter Descripter = createConsumerDescripter(sessionAdapter.getDescripter(), destination, noLocalReceipt);
		JmsMessageConsumer consumer = (JmsMessageConsumer) createConsumer(sessionAdapter, Descripter);
		return consumer;
    }

//    public static JmsMessageProducer createProducer(JmsSessionAdapter sessionAdapter, JmsMessageProducerDescripter specification) throws JMSException {
//    	JmsMessageProducerImpl producer = (JmsMessageProducerImpl) createProducer(specification);
//    	producer.setJndiContext(sessionAdapter.getJndiContext());
//    	producer.setSessionAdapter(sessionAdapter);
//		return producer;
//    }
    
//    public static JmsMessageConsumer createConsumer(JmsSessionAdapter sessionAdapter, JmsMessageConsumerDescripter specification) throws JMSException {
//    	JmsMessageConsumerImpl consumer = (JmsMessageConsumerImpl) createConsumer(specification, sessionAdapter);
//		return consumer;
//    }
    
//    public static JmsProducerImpl createProducer(JNDIContext jndiContext, JmsProducerDescripter specification) throws JMSException {
//		JmsProducerImpl producer = createProducer(specification);
//    	producer.setJNDIContext(jndiContext);
//		return producer;
//    }
//
//    public static JmsConsumerImpl createConsumer(JNDIContext jndiContext, JmsConsumerDescripter specification) throws JMSException {
//		JmsConsumerImpl consumer = createConsumer(specification);
//		consumer.setJNDIContext(jndiContext);
//		return consumer;
//    }
    
	
	
	
//	public static MessageProducer createProducer(JmsProducerDescripter config, SessionAdapter sessionAdapter) throws JMSException {
//		return createProducer(config, sessionAdapter.getSession(), sessionAdapter.getDestination());
//	}


	public static MessageProducer createMessageProducer(Session session, JmsProducerDescripter descripter, Destination destination) throws JMSException {
    	MessageProducer messageProducer = session.createProducer(destination);
    	messageProducer.setDeliveryMode(descripter.getDeliveryMode());
    	messageProducer.setPriority(descripter.getPriority());
        messageProducer.setTimeToLive(descripter.getTimeToLive());
        messageProducer.setDisableMessageID(!descripter.isMessageIdEnabled());
        messageProducer.setDisableMessageTimestamp(!descripter.isMessageTimestampEnabled());
		log.debug("Created producer: "+descripter.toString());
		return messageProducer;
    }
    
//	public static MessageConsumer createConsumer(JmsConsumerDescripter config, SessionAdapter sessionAdapter) throws JMSException {
//		return createConsumer(config, sessionAdapter.getSession(), sessionAdapter.getDestination());
//	}
	
	public static MessageConsumer createMessageConsumer(Session session, JmsConsumerDescripter descripter, Destination destination) throws JMSException {
    	boolean noLocalReceipt = descripter.isNoLocalReceipt();
    	String messageSelector = descripter.getMessageSelector();
    	MessageConsumer messageConsumer = session.createConsumer(destination, messageSelector, noLocalReceipt);
        log.debug("Created consumer: "+descripter.toString());
		return messageConsumer;
    }

}
