package org.aries.jms;


public class JMSEndpointTestContext extends JMSTestContext {

	protected JmsMessageProducer producer;
	protected JmsMessageProducer producer2;
	protected JmsMessageConsumer consumer;
	protected JmsMessageConsumer consumer2;
	
	
    public void setUp() throws Exception {
        super.setUp();
        //createProducers();
        //createConsumers();
    	initialize();
    }
    
	public void tearDown() throws Exception {
    	//closeProducers();
    	//closeConsumers();
        super.tearDown();
    }

	public JmsMessageProducer getProducer() {
		return producer;
	}

	public JmsMessageProducer getProducer2() {
		return producer2;
	}

	public JmsMessageConsumer getConsumer() {
		return consumer;
	}

	public JmsMessageConsumer getConsumer2() {
		return consumer2;
	}

	protected void createProducers() throws Exception {
    	producer = JMSEndpointTestFixture.createProducer(sessionAdapter);
    	producer2 = JMSEndpointTestFixture.createProducer(sessionAdapter);
	}

    protected void createConsumers() throws Exception {
    	consumer = JMSEndpointTestFixture.createConsumer(sessionAdapter2);
    	consumer2 = JMSEndpointTestFixture.createConsumer(sessionAdapter2);
	}

    private void initialize() throws Exception {
    	//initializeProducers();
    	//initializeConsumers();
    }

    protected void initializeProducers() throws Exception {
		producer.initialize();
		producer2.initialize();
	}

    protected void initializeConsumers() throws Exception {
		consumer.initialize();
		consumer2.initialize();
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
