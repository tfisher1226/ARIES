package org.aries.jms.p2p;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;
import javax.naming.NamingException;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.jms.JmsSessionAdapter;
import org.aries.jms.util.MessageUtil;
import org.aries.util.IdGenerator;
import org.aries.util.concurrent.FutureResult;


public class JmsRequestInvoker extends AbstractBridge {
	
	private static Log log = LogFactory.getLog(JmsRequestInvoker.class);

    private static final String MBEAN_NAME = "Messaging:name=JmsRequestInvoker";

	private static final int DEFAULT_RECEIVE_WAIT_TIME = 3600000;
	
	//TODO synchronize access to this
	private Map<Long, FutureResult<Object>> pendingRequests;

	private int receiveWaitTime;
	
	
	public JmsRequestInvoker(JmsSessionAdapter session) {
		super(session);
	}

    public String getMBeanName() {
        return MBEAN_NAME;
    }
    
	public int getReceiveWaitTime() {
		return receiveWaitTime;
	}

	public void setReceiveWaitTime(int waitTime) {
		receiveWaitTime = waitTime;
	}
	
	public void initialize() throws JMSException {
		messageListener = createHandler();
		if (messageProducer != null)
			messageProducer.close();
		if (messageConsumer != null)
			messageConsumer.close();
		messageProducer = createProducer();
		messageConsumer = createConsumer();
		//producer.initialize();
		//consumer.initialize();
		pendingRequests = new HashMap<Long, FutureResult<Object>>();
		receiveWaitTime = DEFAULT_RECEIVE_WAIT_TIME;
        log.info("initialized");
	}
	
	protected Destination getReplyTo() {
		Destination replyTo = messageConsumer.getDestination();
		if (replyTo == null)
			replyTo = consumerDescripter.getDestination();
		return replyTo; 
	}
	
	public Message invoke(Message request) throws NamingException, JMSException {
		request.setJMSReplyTo(getReplyTo());
		//messageConsumer.setDestination(replyTo);
		messageConsumer.initialize();
		messageProducer.initialize();

		Long requestId = IdGenerator.createRequestId(request);
		FutureResult<Object> futureResult = new FutureResult<Object>();
		pendingRequests.put(requestId, futureResult);

    	//validate state
		//consumer.setMessageListener(listener);
		//request.setJMSReplyTo(consumer.getDestination());
    	//consumer.validate();
    	//producer.validate();

		try {
        	//send request message
			request.setLongProperty("RequestId", requestId);
			messageProducer.send(request);
			
		} catch (Throwable e) {
            log.error("****** Exception: "+ExceptionUtils.getRootCauseMessage(e));
			log.debug("", e);
			notifyPendingRequest(requestId, e);
		}

    	//wait for response message (or exception)
		Object result = waitForIncomingResult(requestId, futureResult, receiveWaitTime);
		//checkForAndThrowException(result);

		Message response = (Message) result;
		return response;
	}

	protected Object waitForIncomingResult(Long requestId, FutureResult<Object> futureResult, long timeout) throws JMSException {
		log.debug("Response pending: timeout="+timeout);

		try {
			Object result = futureResult.get(timeout);
			log.debug("Response received");
			pendingRequests.remove(requestId);
			//Assert.isSerializable(result);
			return result;

		} catch (Exception e) {
			//throw a JMSException here?
			throw new RuntimeException(e);

		} finally {
			pendingRequests.remove(requestId);
		}
	}

	protected MessageListener createHandler() throws JMSException {
		return new MessageListener() {
			public void onMessage(Message message) {
				Long requestId = null;
				try {
					requestId = (Long) message.getLongProperty("RequestId");
					log.info("Message received: "+MessageUtil.toString(message));
					notifyPendingRequest(requestId, message);
				} catch (Exception e) {
		            log.error("****** Exception: "+ExceptionUtils.getRootCauseMessage(e));
					notifyPendingRequest(requestId, e);
				}				
			}
		};
	}

	protected void notifyPendingRequest(Long requestId, Object result) {
		log.info("Pending-request count: "+pendingRequests.size());
		Iterator<Long> iterator = pendingRequests.keySet().iterator();
		while (iterator.hasNext()) {
			Long key = (Long) iterator.next();
			log.info("Pending-request found");
		}
		
		FutureResult<Object> futureResult = pendingRequests.get(requestId);
		pendingRequests.remove(requestId);

		if (futureResult == null)
			log.error("FutureResult is null");
		if (futureResult != null)
			futureResult.set(result);
	}
	
	protected void checkForAndThrowException(Object result) throws Exception {
		if (result instanceof Exception) {
			throw (Exception) result;
		}
		if (result instanceof RuntimeException) {
			throw (RuntimeException) result;
		}
		Object object = null;
		try {
			if (result instanceof TextMessage) {
				ObjectMessage objectMessage = (ObjectMessage) result;
				//TODO unmarshall the exception
				object = null;
				
			} else if (result instanceof ObjectMessage) {
				ObjectMessage objectMessage = (ObjectMessage) result;
				object = objectMessage.getObject();
			}
		} catch (JMSException e) {
			throw new JMSException("Unexpected exception: "+e.getMessage());
		}
		if (object instanceof Exception) {
			throwJMSException((Exception) object);
		}
	}

	protected void throwJMSException(Throwable exception) throws JMSException {
		if (exception instanceof JMSException)
			throw (JMSException) exception;
		if (exception instanceof Exception) {
			throw new JMSException(exception.getMessage());
		}
	}
}
