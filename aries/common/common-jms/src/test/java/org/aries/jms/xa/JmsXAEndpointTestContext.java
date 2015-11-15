package org.aries.jms.xa;



public class JmsXAEndpointTestContext extends JmsXATestContext {

	protected JmsXAMessageProducer producer;
	
	protected JmsXAMessageProducer producer2;
	
	protected JmsXAMessageConsumer consumer;
	
	protected JmsXAMessageConsumer consumer2;
	
	protected XATransactor transactor;
	
	protected XATransactor transactor2;
	
	
    public void setUp() throws Exception {
        super.setUp();
    	//createProducers();
    	//createConsumers();
    	createTransactors();
    	//initializeProducers();
    	//initializeConsumers();
    }
    
	public void tearDown() throws Exception {
    	//closeProducers();
    	//closeConsumers();
        super.tearDown();
    }

	public JmsXAMessageProducer getProducer() {
		return producer;
	}

	public JmsXAMessageProducer getProducer2() {
		return producer2;
	}

	public JmsXAMessageConsumer getConsumer() {
		return consumer;
	}

	public JmsXAMessageConsumer getConsumer2() {
		return consumer2;
	}

	public XATransactor getTransactor() {
		return transactor;
	}

	public XATransactor getTransactor2() {
		return transactor2;
	}

	protected void createProducers() throws Exception {
    	producer = JmsXAEndpointTestFixture.createXAProducer(xaSessionAdapter);
    	producer2 = JmsXAEndpointTestFixture.createXAProducer(xaSessionAdapter);
	}

    protected void createConsumers() throws Exception {
    	consumer = JmsXAEndpointTestFixture.createXAConsumer(xaSessionAdapter2);
    	consumer2 = JmsXAEndpointTestFixture.createXAConsumer(xaSessionAdapter2);
	}

	protected void createTransactors() throws Exception {
		transactor = JmsXATransactionTestFixture.createTransactor();
		transactor2 = JmsXATransactionTestFixture.createTransactor();
	}
	
	public void initializeProducers() throws Exception {
		producer.initialize();
		producer2.initialize();
	}

	public void initializeConsumers() throws Exception {
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
