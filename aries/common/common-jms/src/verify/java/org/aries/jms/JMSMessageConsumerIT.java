package org.aries.jms;

import java.io.Serializable;

import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;

import org.aries.Assert;


public class JMSMessageConsumerIT extends AbstractJmsTest {

	private JmsMessageConsumer consumer;

	private JmsMessageProducer producer;
	
	
//    public static void main(String[] args) {
//        junit.textui.TestRunner.run(JMSMessageConsumerRuntimeTest.class);     
//    }
//
//    public static Test suite() {
//        return new TestSuite(JMSMessageConsumerRuntimeTest.class);
//    }

    
    public void setUp() throws Exception {
        super.setUp();
        JMSTestContext.expireMessages();
        JMSTestContext.removeMessages();
		consumer = JMSEndpointTestFixture.createConsumer(getSessionAdapter());
    	producer = JMSEndpointTestFixture.createProducer(getSessionAdapter2());
    	consumer.initialize();
    	producer.initialize();
    }
    
	public void tearDown() throws Exception {
		consumer.close();
        producer.close();
        consumer = null;
        producer = null;
        super.tearDown();
    }

    
    public void testReceive() throws Exception {
    	String testData = "data";
		TextMessage textMessage = producer.getSession().createTextMessage(testData);
    	producer.send(textMessage);
    	Message message = consumer.receive(2000);
    	Assert.notNull(message, "Message not received");
    	Assert.isTrue(message instanceof TextMessage, "Unexpected message type");
    	textMessage = (TextMessage) message;
    	String actualData = textMessage.getText();
    	Assert.equals(testData, actualData);
    }

//    public void testReceive_ConnectionClosed() throws Exception {
//    	String testData = "data";
//		TextMessage message = producer.getSession().createTextMessage(testData);
//    	producer.sendMessage(message);
//    	getConnectionAdapter().close();
//    	Serializable actualData = consumer.receive(2000);
//    	assertEquals(testData, actualData);
//    }

//    public void testReceive_SessionClosed() throws Exception {
//    	String testData = "data";
//    	producer.send(testData);
//    	getSessionAdapter().close();
//    	Serializable actualData = consumer.receive(2000);
//    	assertEquals(testData, actualData);
//    }

    public void testReceive_EndpointClosed() throws Exception {
    	//JmsMessageConsumer consumer = JMSEndpointTestFixture.prepareDurableConsumer(getSessionAdapter());
    	String testData = "data";
		TextMessage textMessage = producer.getSession().createTextMessage(testData);
    	producer.send(textMessage);
    	//fixture.close();
    	//getConnectionAdapter2().close();
    	Message message = consumer.receive(2000);
    	Assert.notNull(message, "Message not received");
    	Assert.isTrue(textMessage instanceof TextMessage, "Unexpected message type");
    	textMessage = (TextMessage) message;
    	String actualData = textMessage.getText();
    	Assert.equals(testData, actualData);
    }

    public void testReceive_TextMessage() throws Exception {
    	String testData = "data";
		TextMessage message = producer.getSession().createTextMessage(testData);
    	producer.send(message);
    	Message receipt = consumer.receive(2000);
    	Assert.notNull(receipt, "Message not received");
    	Assert.isInstanceOf(TextMessage.class, receipt);
    	TextMessage textMessage = (TextMessage) receipt;
    	String text = textMessage.getText();
    	Assert.equals(text, testData);
    }

    public void testReceive_ObjectMessage() throws Exception {
    	Serializable testData = "data";
		ObjectMessage message = producer.getSession().createObjectMessage(testData);
    	producer.send(message);
    	Message receipt = consumer.receive(2000);
    	Assert.notNull(receipt, "Message not received");
    	Assert.isInstanceOf(ObjectMessage.class, receipt);
    	ObjectMessage objectMessage = (ObjectMessage) receipt;
    	Object object = objectMessage.getObject();
    	Assert.equals(object, testData);
    }

}
