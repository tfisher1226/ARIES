package org.aries.jms.p2p;

import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.aries.AbstractAdapter;
import org.aries.Assert;
import org.aries.jms.config.JmsExchangeDescripter;


public class JmsMessageServer extends AbstractAdapter {
    
    private static final String MBEAN_NAME = "Messaging:name=MessageExchangeServer";
    
    protected JmsExchangeDescripter _specification;

    protected JmsMessageExchanger _exchanger;

//    protected JmsMessageReceiver _receiver;

    protected MessageListener _handler;

    protected JmsMessageProcessor _processor;

    //private Object _lock = new Object();


	public JmsMessageServer(JmsExchangeDescripter specification) throws Exception {
		_specification = specification;
		construct();
	}

//	public JmsMessageExchangeServerImpl(JmsSessionAdapter incomingSessionAdapter, JmsSessionAdapter outgoingSessionAdapter) throws Exception {
//		_exchanger = new JmsMessageExchangeTransportImpl(incomingSessionAdapter, outgoingSessionAdapter);
//		//construct(incomingSessionAdapter, outgoingSessionAdapter);
//	}

//	public JmsMessageExchangeServerImpl(JmsSessionAdapter sessionAdapter, JmsMessageExchangeSpec specification) throws Exception {
//		_receiveSessionAdapter = sessionAdapter;
//		_specification = specification;
//		construct();
//	}
	
//	private void construct(JmsSessionAdapter incomingSessionAdapter, JmsSessionAdapter outgoingSessionAdapter) throws Exception {
//		_exchanger = new JmsMessageExchangeTransportImpl(incomingSessionAdapter, outgoingSessionAdapter);
//		//construct(incomingSessionAdapter, outgoingSessionAdapter);
//		//_exchanger = createExchanger(incomingSessionAdapter, outgoingSessionAdapter);
//	}
	
	private void construct() throws Exception {
		_exchanger = createExchanger();
	}

    public String getMBeanName() {
        return MBEAN_NAME;
    }

	public JmsExchangeDescripter getDescripter() {
		return _specification;
	}

	public void setDescripter(JmsExchangeDescripter value) {
		if (_exchanger != null)
			_exchanger.setSpecification(value);
		_specification = value;
	}

//	public Session getIncomingSession() {
//		return _exchanger.getIncomingSessionAdapter().getSession();
//	}
//
//	public Session getOutgoingSession() {
//		return _exchanger.getOutgoingSessionAdapter().getSession();
//	}

//    public JmsSessionAdapter getSessionAdapter() {
//    	return _receiveSessionAdapter;
//    }
//
//	public void setSessionAdapter(JmsSessionAdapter value) {
//		_receiveSessionAdapter = value;
//		if (_exchanger != null)
//			_exchanger.setSessionAdapter(value);
//		setJndiContext(value.getJndiContext());
//	}

	public JmsMessageProcessor getMessageProcessor() {
		return _processor;
	}

	public void setMessageProcessor(JmsMessageProcessor value) {
		_processor = value;
	}

	public Destination getDestination() {
		return _exchanger.getDestination();
	}

	public void setDestination(Destination value) {
		_exchanger.setDestination(value);
	}

	
	public void initialize() throws Exception {
//        _receiver = createReceiver();
        _handler = createHandler();
//		_receiver.setMessageListener(_handler);
		_exchanger.setMessageListener(_handler);
//    	_receiver.initialize();
		_exchanger.initialize();
        log.info("Initialized");
    }
	
//	protected JmsMessageExchangeTransportImpl createExchanger(JmsSessionAdapter incomingSessionAdapter, JmsSessionAdapter outgoingSessionAdapter) {
//		JmsMessageExchangeTransportImpl exchanger = new JmsMessageExchangeTransportImpl(incomingSessionAdapter, outgoingSessionAdapter);
//		exchanger.setSpecification(getSpecification());
//		//exchanger.setSessionAdapter(getSessionAdapter());
//		return exchanger;
//	}

	protected JmsMessageExchanger createExchanger() {
		JmsMessageExchanger exchanger = new JmsMessageExchanger(_specification);
		//exchanger.setSessionAdapter(getSessionAdapter());
		return exchanger;
	}

//    protected JmsMessageReceiverImpl createReceiver() {
//    	RequestReceiver receiver = new RequestReceiver();
//		return receiver;
//	}    

    public MessageListener getHandler() {
    	return _handler;
    }
    
    protected MessageListener createHandler() {
    	RequestHandler handler = new RequestHandler();
		return handler;
	}    

	public void start() throws Exception {
		//_receiver.start();
		_exchanger.start();
	}

	public void stop() throws Exception {
		//_receiver.stop();
		_exchanger.stop();
	}

	public void validate() throws Exception {
		_exchanger.validate();
	}

	public Message receiveMessage(int timeout) throws Exception {
		return _exchanger.receiveMessage(timeout);
	}

	public void sendMessage(Message message, Destination destination) throws Exception {
        _exchanger.sendMessage(message, destination);
	}

//	public void sendMessage(Destination destination, Serializable responseData, Long requestId, String messageId) throws Exception {
//        _exchanger.sendResponse(destination, responseData, requestId, messageId);
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

    
    /**
     * Provides local MessageListener for:
     * 1) handling dispatch of incoming Request messages, 
     * 2) passing them to external MessageProcessor,
     * 3) then sending output back in Response message.
     */
    class RequestHandler implements MessageListener {
		public void onMessage(Message request) {
            Message response = null;

            try {
	            //process the request 
            	response = _processor.process(request);
	        } catch (Exception e) {
	            log.error(e);
	        	//output = e;
	            //TODO
	        }

	        try {
	            //return the response
	        	_exchanger.validate();
	            Destination replyTo = request.getJMSReplyTo();
	            Assert.notNull(replyTo, "ReplyTo must be specified");
	            Long requestId = request.getLongProperty("RequestId");
	            String messageId = request.getJMSMessageID();
		        response.setJMSCorrelationID(messageId);
				response.setObjectProperty("RequestId", requestId);
	            _exchanger.sendMessage(response, replyTo);
	            
	            log.debug("Processed request");
	        } catch (Exception e) {
	            log.error(e);
	        }
		}
    }


	public void join() throws Exception {
		_exchanger.join();
	}
	
	public void reset() throws Exception {
		_exchanger.reset();
	}
	
	public void close() throws Exception {
		_exchanger.close();
//		_receiver.close();
	}

}
