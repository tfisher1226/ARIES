package org.aries.jms;

import javax.jms.Message;
import javax.jms.TextMessage;



public abstract class AbstractJmsEndpointIT extends AbstractJmsIT {

	protected JmsMessageProducer producer;
	
	protected JmsMessageProducer producer2;
	
	protected JmsMessageConsumer consumer;
	
	protected JmsMessageConsumer consumer2;
	
	
    public void setUp() throws Exception {
        super.setUp();
    	assureProducers();
    	assureConsumers();
    	assureInitialization();
    }
    
	public void tearDown() throws Exception {
    	closeProducers();
    	closeConsumers();
        super.tearDown();
    }

	/*
     * Assurance methods
     */

	protected void assureProducers() throws Exception {
    	producer = JMSEndpointTestFixture.createProducer(sessionAdapter);
    	producer2 = JMSEndpointTestFixture.createProducer(sessionAdapter);
	}

    protected void assureConsumers() throws Exception {
    	consumer = JMSEndpointTestFixture.createConsumer(sessionAdapter2);
    	consumer2 = JMSEndpointTestFixture.createConsumer(sessionAdapter2);
	}

    /*
     * Initialization methods
     */

    private void assureInitialization() throws Exception {
    	initializeProducers();
    	initializeConsumers();
    }

	public void initializeProducers() throws Exception {
		producer.initialize();
		producer2.initialize();
	}

	public void initializeConsumers() throws Exception {
		consumer.initialize();
		consumer2.initialize();
	}

    
    /*
     * Assertion methods
     */

	protected void assertMessageGenerated(String data) throws Exception {
		assertMessageGenerated(producer, data);
	}

	protected void assertMessageGenerated(JmsMessageProducer producer, String data) throws Exception {
		TextMessage message = producer.getSessionAdapter().createTextMessage(data);
    	producer.send(message);
	}

	protected void assertNoMessageReceived() throws Exception {
		assertNoMessageReceived(consumer);
	}

    protected void assertNoMessageReceived(JmsMessageConsumer consumer) throws Exception {
    	Message data = consumer.receive(1000);
    	assertNull(data);
	}

	protected void assertMessageReceived(String data) throws Exception {
		assertMessageReceived(consumer, data);
	}

    protected void assertMessageReceived(JmsMessageConsumer consumer, String expectedData) throws Exception {
    	Message actualData = consumer.receive();
    	assertEquals(expectedData, actualData);
	}

    
    protected void closeProducers() throws Exception {
    	producer.close();
    	producer2.close();
    	producer = null;
    	producer2 = null;
    }

    protected void closeConsumers() throws Exception {
    	consumer.close();
    	consumer2.close();
    	consumer = null;
    	consumer2 = null;
    }

}
