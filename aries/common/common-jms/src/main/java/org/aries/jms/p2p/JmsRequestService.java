package org.aries.jms.p2p;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.aries.Assert;
import org.aries.jms.JmsSessionAdapter;


public class JmsRequestService extends AbstractBridge {

    private static final String MBEAN_NAME = "Messaging:name=JmsRequestService";
    
    protected JmsMessageProcessor processor;

    protected Destination replyTo;
    

	public JmsRequestService(JmsSessionAdapter session) {
		super(session);
	}

    public String getMBeanName() {
        return MBEAN_NAME;
    }

	public JmsMessageProcessor getMessageProcessor() {
		return processor;
	}

	public void setMessageProcessor(JmsMessageProcessor processor) {
		Assert.notNull(processor, "Processor must be specified");
		this.processor = processor;
	}
	
//	public Destination getDestination() {
//		return _exchanger.getDestination();
//	}
//
//	public void setDestination(Destination value) {
//		_exchanger.setDestination(value);
//	}

	
	public void initialize() throws Exception {
		messageListener = createHandler();
		messageProducer = createProducer();
		messageConsumer = createConsumer();
		messageProducer.initialize();
		messageConsumer.initialize();
        log.info("initialized");
	}
	
	protected MessageListener createHandler() throws JMSException {
		return new MessageListener() {
			public void onMessage(Message request) {
	            Message response = null;

	            try {
	            	if (processor == null) {
	            		//TODO 
	            		//forward message? or rollback message? or drop message? 
	            		//(must be configurable from model level)
	            		System.out.println("Dropping "+request);
	            		return;
	            	}
	            	
		            //process request message 
	            	//System.out.println("Processing "+request);
            		response = processor.process(request);
            		//TODO status of response and response accordingly
            		
		        } catch (JMSException e) {
		            log.error("****** Exception: "+ExceptionUtils.getRootCauseMessage(e));
		            log.debug("", e);
		            response = createExceptionMessage(e);
		            //TODO send to DLQ?
		        }
		        
		        if (messageProducer.isClosed()) {
		        	//TODO 
		        	//handle the message
		        	//don't just drop it.
		        	return;
		        }

		        try {
		        	//validate producer state
		        	messageProducer.validate();

		        	//return response message
		        	Destination destination = request.getJMSReplyTo();
		    	    Assert.notNull(destination, "ReplyTo destination not found");
		            Long requestId = request.getLongProperty("RequestId");
		            String messageId = request.getJMSMessageID();
			        response.setJMSCorrelationID(messageId);
					response.setLongProperty("RequestId", requestId);
		            log.info("Executing reply: "+messageProducer.getDescripter());
		            messageProducer.send(response, destination);
		            
		            //System.out.println(">>>Sent response: "+messageId);
		            
		            log.debug("Processed request");
		        	//validate consumer state
		            messageConsumer.validate();
		        	
		        } catch (Exception e) {
		            log.error("****** Exception: "+ExceptionUtils.getRootCauseMessage(e));
		            log.debug("", e);
		        }
			}
		};
	}  
    
	protected Message createExceptionMessage(Exception exception) {
        try {
			Message message = getSession().createObjectMessage(exception);
			return message;
		} catch (JMSException e) {
            log.error("****** Exception: "+ExceptionUtils.getRootCauseMessage(e));
            log.debug("", e);
		}
		return null;
	}

	public void start() throws Exception {
		//_receiver.start();
	}

	public void stop() throws Exception {
		//_receiver.stop();
	}

	public void validate() throws Exception {
		messageProducer.validate();
		messageConsumer.validate();
	}

	public Message receiveMessage(int timeout) throws Exception {
		return messageConsumer.receive(timeout);
	}

	public void sendMessage(Message message, Destination destination) throws Exception {
		messageProducer.send(message, destination);
	}

//	public void sendMessage(Destination destination, Serializable responseData, Long requestId, String messageId) throws Exception {
//        producer.sendResponse(destination, responseData, requestId, messageId);
//	}


	/**
     * Provides local MessageReceiver implementation 
     * for purpose of handling Request messages and 
     * dispatching Response messages to be processed.
     */
//    class RequestReceiver extends JmsMessageReceiverImpl {
//    	public void execute() {
//    		//_transactor.begin();
//    		//Assert.isTrue(_transactor.isStarted());
//    		while (!_thread.isInterrupted() && !_receiver.isStopped() && !_exchanger.isClosing()) {
//    			String jmsMessageID = "n/a";
//    			try {
//    				//Transaction transaction = _transactor.getTransaction();
//    				//Message message = _consumer.receiveMessage(transaction, getTimeout());
//    				Message message = _exchanger.receiveMessage(getTimeout());
//    				if (message != null) {
//        				jmsMessageID = message.getJMSMessageID();
//    					dispatch(message);
//    				}
//    			} catch (RejectedExecutionException e) {
//    				log.error("Message rejected, input queue is full, ID="+jmsMessageID);
//    			} catch (Throwable e) {
//    				//_transactor.rollback();
//    				log.error(e);
//    			}
//    		}
//    	}    	
//    }

}
