package org.aries.jms;

import javax.jms.Message;
import javax.jms.TextMessage;

import org.aries.Assert;
import org.aries.jms.AbstractJmsTest;
import org.aries.jms.JMSEndpointTestFixture;
import org.aries.jms.JMSTestContext;
import org.aries.jms.JMSTestFixture;
import org.aries.jms.JmsMessageConsumer;
import org.aries.jms.JmsMessageProducer;
import org.aries.jms.JmsSessionAdapter;


public class JMSMessageProducerIT extends AbstractJmsTest {

	private JmsMessageProducer producer;

	private JmsMessageConsumer consumer;

	
//    public static void main(String[] args) {
//        junit.textui.TestRunner.run(JMSMessageProducerRuntimeTest.class);     
//    }
//
//    public static Test suite() {
//        return new TestSuite(JMSMessageProducerRuntimeTest.class);
//    }
    
    
    public void setUp() throws Exception {
        super.setUp();
        JMSTestContext.expireMessages();
        JMSTestContext.removeMessages();
    	producer = JMSEndpointTestFixture.createProducer(getSessionAdapter());
    	consumer = JMSEndpointTestFixture.createConsumer(getSessionAdapter2());
    	producer.initialize();
    	consumer.initialize();
    }
    
	public void tearDown() throws Exception {
		consumer.close();
        producer.close();
        consumer = null;
        producer = null;
        super.tearDown();
    }

    
    public void testCreateTextMessage() throws Exception {
		Message message = producer.createTextMessage("data");
    	Assert.notNull(message, "message should be set");
    	Assert.isInstanceOf(TextMessage.class, message);
    }

    public void testCreateTextMessage_Exception() throws Exception {
    	getConnectionAdapter().close();
		Message message = producer.createTextMessage("data");
    	Assert.notNull(message, "message should be set");
    	Assert.isInstanceOf(TextMessage.class, message);
    }

    public void testSend() throws Exception {
		String testData = "data";
		TextMessage message = producer.createTextMessage(testData);
    	producer.send(message);
    	assertMessageReceived(consumer, testData);
    }

    public void testSend_ConnectionClosed() throws Exception {
    	getConnectionAdapter().close();
		String testData = "data";
		TextMessage message = producer.createTextMessage(testData);
    	producer.send(message);
    	assertMessageReceived(consumer, testData);
    }

    public void testSend_SessionClosed() throws Exception {
    	getSessionAdapter().close();
		String testData = "data";
		TextMessage message = producer.createTextMessage(testData);
    	producer.send(message);
    	assertMessageReceived(consumer, testData);
    }

    public void testSend_EndpointClosed() throws Exception {
    	producer.close();
		String testData = "data";
		TextMessage message = producer.createTextMessage(testData);
    	producer.send(message);
    	assertMessageReceived(consumer, testData);
    }

	public void testSendMessage() throws Exception {
		Message message = producer.createTextMessage("data");
    	Assert.notNull(message, "message should be set");
    	Assert.isInstanceOf(TextMessage.class, message);
    	producer.send(message);
    }

	public void testSendMessage_ConnectionClosed() throws Exception {
		Message message = producer.createTextMessage("data");
    	getConnectionAdapter().close();
    	producer.send(message);
    }

	public void testSendMessage_SessionClosed() throws Exception {
		Message message = producer.createTextMessage("data");
    	getSessionAdapter().close();
    	producer.send(message);
	}

	public void testSendTransacted_Commit() throws Exception {
		JmsSessionAdapter sessionAdapter = JMSTestFixture.prepareTransactedSessionAdapter(getConnectionAdapter());
		JmsMessageProducer producer = JMSEndpointTestFixture.createProducer(sessionAdapter);
    	producer.initialize();

		String testData = "data";
    	producer.send(producer.createTextMessage(testData));
    	producer.send(producer.createTextMessage(testData));
    	sessionAdapter.commit();
    	assertMessageReceived(consumer, testData);
    	assertMessageReceived(consumer, testData);
	}

	public void testSendTransacted_Rollback() throws Exception {
		JmsSessionAdapter sessionAdapter = JMSTestFixture.prepareTransactedSessionAdapter(getConnectionAdapter());
		JmsMessageProducer producer = JMSEndpointTestFixture.createProducer(sessionAdapter);
    	producer.initialize();
		String testData = "data";
    	producer.send(producer.createTextMessage(testData));
    	producer.send(producer.createTextMessage(testData));
    	sessionAdapter.rollback();
    	assertNoMessageReceived(consumer);
	}

}
