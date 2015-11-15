package org.aries.jms;

import org.aries.jms.config.JmsConsumerDescripter;
import org.aries.jms.config.JmsProducerDescripter;
import org.aries.jms.config.JmsSessionDescripter;
import org.aries.jndi.JndiContext;


public class JMSEndpointTestFixture {

    public static JmsProducerDescripter createProducerDescripter() {
    	JmsProducerDescripter specification = new JmsProducerDescripter();
    	specification.setDestinationName(JMSTestFixture.getDestinationName());
        return specification;
    }

    public static JmsConsumerDescripter createConsumerDescripter() {
    	JmsConsumerDescripter specification = new JmsConsumerDescripter();
    	specification.setDestinationName(JMSTestFixture.getDestinationName());
        return specification;
    }

    public static JmsProducerDescripter createProducerDescripter(String destination) {
    	JmsSessionDescripter sessionDescripter = JMSTestFixture.createSessionDescripter();
    	return JMSEndpointTestFixture.createProducerDescripter(sessionDescripter, destination);
    }
    
    public static JmsConsumerDescripter createConsumerDescripter(String destination) {
    	JmsSessionDescripter sessionDescripter = JMSTestFixture.createSessionDescripter();
    	return JMSEndpointTestFixture.createConsumerDescripter(sessionDescripter, destination);
    }
    
    public static JmsProducerDescripter createProducerDescripter(JmsSessionDescripter sessionDescripter) {
    	JmsProducerDescripter specification = createProducerDescripter();
    	specification.setSessionDescripter(sessionDescripter);
    	return specification;
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

    public static JmsConsumerDescripter createConsumerDescripter(JmsSessionDescripter sessionDescripter, String destination) {
    	JmsConsumerDescripter specification = createConsumerDescripter(sessionDescripter);
    	specification.setDestinationName(destination);
    	return specification;
    }

    public static JmsMessageProducer createProducer(JmsSessionAdapter sessionAdapter) throws Exception {
		JmsProducerDescripter specification = createProducerDescripter(sessionAdapter.getDescripter());
		return createProducer(sessionAdapter, specification);
    }

    public static JmsMessageConsumer createConsumer(JmsSessionAdapter sessionAdapter) throws Exception {
    	JmsConsumerDescripter specification = createConsumerDescripter(sessionAdapter.getDescripter());
		return createConsumer(sessionAdapter, specification);
    }

    public static JmsMessageProducer createProducer(JmsSessionAdapter sessionAdapter, String destination) throws Exception {
		JmsProducerDescripter specification = createProducerDescripter(sessionAdapter.getDescripter(), destination);
		return createProducer(sessionAdapter, specification);
    }

    public static JmsMessageConsumer createConsumer(JmsSessionAdapter sessionAdapter, String destination) throws Exception {
    	JmsConsumerDescripter specification = createConsumerDescripter(sessionAdapter.getDescripter(), destination);
		return createConsumer(sessionAdapter, specification);
    }

    public static JmsMessageProducer createProducer(JmsSessionAdapter sessionAdapter, JmsProducerDescripter Descripter) throws Exception {
    	JmsMessageProducer producer = JmsMessageEndpointFactory.createProducer(sessionAdapter, Descripter);
		return producer;
    }
    
    public static JmsMessageConsumer createConsumer(JmsSessionAdapter sessionAdapter, JmsConsumerDescripter Descripter) throws Exception {
    	JmsMessageConsumer consumer = JmsMessageEndpointFactory.createConsumer(sessionAdapter, Descripter);
		return consumer;
    }
    
    public static JmsMessageProducer createProducer(JndiContext jndiContext, JmsProducerDescripter Descripter) throws Exception {
		JmsMessageProducer producer = JmsMessageEndpointFactory.createProducer(null, Descripter);
    	producer.setJndiContext(jndiContext);
		return producer;
    }

    public static JmsMessageConsumer createConsumer(JndiContext jndiContext, JmsConsumerDescripter Descripter) throws Exception {
		JmsMessageConsumer consumer = JmsMessageEndpointFactory.createConsumer(null, Descripter);
		consumer.setJndiContext(jndiContext);
		return consumer;
    }

    public static JmsMessageConsumer createDurableConsumer(JmsSessionAdapter sessionAdapter) throws Exception {
		return createDurableConsumer(sessionAdapter, JMSTestFixture.getDurableSubscriberId());
    }

    public static JmsMessageConsumer createDurableConsumer(JmsSessionAdapter sessionAdapter, String durableSubscriberId) throws Exception {
    	JmsConsumerDescripter specification = createConsumerDescripter(sessionAdapter.getDescripter());
		specification.setDurableSubscriberId(durableSubscriberId);
		return createConsumer(sessionAdapter, specification);
    }

    public static JmsMessageConsumer prepareConsumer(JmsSessionAdapter sessionAdapter) throws Exception {
    	JmsMessageConsumer consumer = createConsumer(sessionAdapter);
		consumer.initialize();
		return consumer;
    }

    public static JmsMessageConsumer prepareDurableConsumer(JmsSessionAdapter sessionAdapter) throws Exception {
    	JmsMessageConsumer consumer = createDurableConsumer(sessionAdapter, JMSTestFixture.getDurableSubscriberId());
		consumer.initialize();
		return consumer;
    }

}
