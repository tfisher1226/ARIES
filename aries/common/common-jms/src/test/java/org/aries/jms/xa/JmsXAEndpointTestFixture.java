package org.aries.jms.xa;

import org.aries.jms.JMSEndpointTestFixture;
import org.aries.jms.config.JmsConsumerDescripter;
import org.aries.jms.config.JmsProducerDescripter;


public class JmsXAEndpointTestFixture extends /*JMSEndpointTestFixture*/ JmsXATestFixture {

    public static JmsXAMessageProducer createXAProducer(JmsXASessionAdapter sessionAdapter) throws Exception {
    	JmsProducerDescripter specification = JMSEndpointTestFixture.createProducerDescripter(sessionAdapter.getDescripter());
		return createXAProducer(sessionAdapter, specification);
    }

    public static JmsXAMessageConsumer createXAConsumer(JmsXASessionAdapter sessionAdapter) throws Exception {
    	JmsConsumerDescripter specification = JMSEndpointTestFixture.createConsumerDescripter(sessionAdapter.getDescripter());
		return createXAConsumer(sessionAdapter, specification);
    }
    

    public static JmsXAMessageProducer createXAProducer(JmsXASessionAdapter sessionAdapter, String destination) throws Exception {
    	JmsProducerDescripter specification = JMSEndpointTestFixture.createProducerDescripter(sessionAdapter.getDescripter(), destination);
		return createXAProducer(sessionAdapter, specification);
    }

    public static JmsXAMessageConsumer createXAConsumer(JmsXASessionAdapter sessionAdapter, String destination) throws Exception {
    	JmsConsumerDescripter specification = JMSEndpointTestFixture.createConsumerDescripter(sessionAdapter.getDescripter(), destination);
		return createXAConsumer(sessionAdapter, specification);
    }

    public static JmsXAMessageProducer createXAProducer(JmsXASessionAdapter sessionAdapter, JmsProducerDescripter specification) throws Exception {
    	JmsXATransportFactory endpointFactory = new JmsXATransportFactory();
    	JmsXAMessageProducer producer = endpointFactory.createXAProducer(sessionAdapter, specification);
		return producer;
    }
    
    public static JmsXAMessageConsumer createXAConsumer(JmsXASessionAdapter sessionAdapter, JmsConsumerDescripter specification) throws Exception {
    	JmsXATransportFactory endpointFactory = new JmsXATransportFactory();
    	JmsXAMessageConsumer consumer = endpointFactory.createXAConsumer(sessionAdapter, specification);
		return consumer;
    }
    
//    public static JmsXAMessageConsumer createXAConsumer(JndiContext jndiContext, JmsConsumerDescripter specification) throws Exception {
//		JmsXATransportFactory endpointFactory = new JmsXATransportFactory();
//		JmsXAMessageConsumer consumer = endpointFactory.createXAConsumer(specification, null);
//		consumer.setJndiContext(jndiContext);
//		return consumer;
//    }

//    public static JmsXAMessageProducer createXAProducer(JndiContext jndiContext, JmsProducerDescripter specification) throws Exception {
//    	JmsXATransportFactory endpointFactory = new JmsXATransportFactory();
//		JmsXAMessageProducer producer = endpointFactory.createXAProducer(specification, null);
//    	producer.setJndiContext(jndiContext);
//		return producer;
//    }

    public static JmsXAMessageConsumer createDurableXAConsumer(JmsXASessionAdapter sessionAdapter) throws Exception {
		return createDurableXAConsumer(sessionAdapter, "durableSubscriberId");
    }

    public static JmsXAMessageConsumer createDurableXAConsumer(JmsXASessionAdapter sessionAdapter, String durableSubscriberId) throws Exception {
    	JmsConsumerDescripter specification = JMSEndpointTestFixture.createConsumerDescripter(sessionAdapter.getDescripter());
		specification.setDurableSubscriberId(durableSubscriberId);
		return createXAConsumer(sessionAdapter, specification);
    }

    public static JmsXAMessageConsumer prepareConsumer(JmsXASessionAdapter sessionAdapter) throws Exception {
    	JmsXAMessageConsumer consumer = createXAConsumer(sessionAdapter);
		consumer.initialize();
		return consumer;
    }

    public static JmsXAMessageConsumer prepareDurableConsumer(JmsXASessionAdapter sessionAdapter) throws Exception {
    	JmsXAMessageConsumer consumer = createDurableXAConsumer(sessionAdapter, "durableSubscriberId");
		consumer.initialize();
		return consumer;
    }

}
