package org.aries.jms;

import javax.jms.Message;

import org.aries.Assert;
import org.aries.jms.config.JmsConnectionDescripter;
import org.aries.jms.config.JmsConsumerDescripter;
import org.aries.jms.config.JmsProducerDescripter;
import org.aries.jms.config.JmsSessionDescripter;


public class AbstractJmsTest {

	protected JMSEndpointTestContext context;


//	public AbstractTestCase(String testName){
//		super(testName);
//	}
	
    public void setUp() throws Exception {
        //super.setUp();
        context = new JMSEndpointTestContext();
        context.setUp();
    }
    
	public void tearDown() throws Exception {
        context.tearDown();
        context = null;
        //super.tearDown();
    }
	
	public JmsConnectionDescripter getConnectionDescripter() {
		return context.getConnectionDescripter();
	}

	public JmsConnectionAdapter getConnectionAdapter() {
		return context.getConnectionAdapter();
	}
	
	public JmsConnectionAdapter getConnectionAdapter2() {
		return context.getConnectionAdapter2();
	}
	
	public JmsSessionAdapter getSessionAdapter() {
		return context.getSessionAdapter();
	}
	
	public JmsSessionAdapter getSessionAdapter2() {
		return context.getSessionAdapter2();
	}
	
    protected JmsProducerDescripter createProducerDescripter(String destination) {
    	JmsSessionDescripter sessionDescripter = context.getSessionDescripter();
		JmsProducerDescripter producerDescripter = JMSEndpointTestFixture.createProducerDescripter(sessionDescripter);
		producerDescripter.setDestinationName(destination);
		return producerDescripter;
	}

    protected JmsConsumerDescripter createConsumerDescripter(String destination) {
    	JmsSessionDescripter sessionDescripter = context.getSessionDescripter();
		JmsConsumerDescripter consumerDescripter = JMSEndpointTestFixture.createConsumerDescripter(sessionDescripter);
		consumerDescripter.setDestinationName(destination);
		return consumerDescripter;
	}

    /*
     * Message creation 
     */

//    protected Message createMessage(String data) throws Exception {
//    	return createTextMessage(data);
//    }
//
//    protected Message createMessage(Serializable data) throws Exception {
//    	return createObjectMessage(data);
//    }
//
//    protected TextMessage createTextMessage(String data) throws Exception {
//		TextMessage message = getSessionAdapter().createTextMessage(data);
//		return message;
//	}
//
//	protected ObjectMessage createObjectMessage(Serializable data) throws Exception {
//		ObjectMessage message = getSessionAdapter().createObjectMessage(data);
//		return message;
//	}

    /*
     * Message send
     */

//	protected void sendMessage(String data) throws Exception {
//		context.getProducer().send(data);
//	}
//
//	protected void sendMessage(Serializable data) throws Exception {
//		context.getProducer().send(data);
//	}
//
//	protected void sendMessage(Message message) throws Exception {
//		context.getProducer().sendMessage(message);
//	}

    /*
     * Message receipt
     */

//	protected void assertNoMessageReceived() throws Exception {
//		assertNoMessageReceived(context.getConsumer());
//	}

    protected void assertNoMessageReceived(JmsMessageConsumer consumer) throws Exception {
    	Message data = consumer.receive(10);
    	Assert.isNull(data);
	}

//	protected void assertMessageReceived(String data) throws Exception {
//		assertMessageReceived(context.getConsumer(), data);
//	}

    protected void assertMessageReceived(JmsMessageConsumer consumer, String expectedData) throws Exception {
    	Message actualData = consumer.receive();
    	Assert.equals(expectedData, actualData);
	}
    
    
    protected void fail(String description) {
    	throw new RuntimeException(description);
	}


}
