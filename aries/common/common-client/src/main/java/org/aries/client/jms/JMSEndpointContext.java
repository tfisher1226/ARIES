package org.aries.client.jms;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;
import javax.naming.NamingException;
import javax.xml.bind.JAXBException;

import org.aries.Assert;
import org.aries.jaxb.JAXBReader;
import org.aries.jaxb.JAXBSessionCache;
import org.aries.jaxb.JAXBWriter;
import org.aries.jms.JmsMessageConsumer;
import org.aries.jms.JmsMessageProducer;
import org.aries.jms.JmsSessionAdapter;
import org.aries.jms.config.JmsConsumerDescripter;
import org.aries.jms.config.JmsProducerDescripter;
import org.aries.jms.p2p.JmsRequestInvoker;
import org.aries.jndi.JndiContext;
import org.aries.message.Fault;
import org.aries.message.Message;
import org.aries.message.util.MessageUtil;
import org.aries.nam.model.old.OperationDescripter;
import org.aries.runtime.BeanContext;
import org.aries.service.registry.ServiceState;
import org.aries.transport.TransferMode;
import org.aries.util.ClassUtil;
import org.aries.util.ObjectUtil;


public class JMSEndpointContext {

	private ServiceState serviceState;
	
	private OperationDescripter operationDescripter;

	private JmsRequestInvoker messageInvoker;
	
	private JmsMessageProducer messageProducer;

	private JmsMessageConsumer messageConsumer;

	private JmsSessionAdapter incomingSessionAdapter;

	private JmsSessionAdapter outgoingSessionAdapter;

	private String incomingChannelName;
	
	private String outgoingChannelName;

	private Object mutex = new Object();

	
	public JMSEndpointContext() {
		//nothing for now
	}

	public JMSEndpointContext(String serviceId) {
		//TODO take this out - should not be used
		System.out.println("TAKE THIS OUT - no longer used");
	}

	public JMSEndpointContext(ServiceState serviceState) {
		this.serviceState = serviceState;
	}

	public String getServiceId() {
		return this.serviceState.getServiceId();
	}

	public ServiceState getServiceState() {
		return this.serviceState;
	}

	public void setServiceState(ServiceState serviceState) {
		this.serviceState = serviceState;
	}

	public OperationDescripter getOperationDescripter() {
		return this.operationDescripter;
	}

	public void setOperationDescripter(OperationDescripter operationDescripter) {
		this.operationDescripter = operationDescripter;
	}

//	public void setTransferMode(TransferMode transferMode) {
//		this.transferMode = transferMode;
//	}
	
	public void setIncomingSessionAdapter(JmsSessionAdapter incomingSessionAdapter) {
		this.incomingSessionAdapter = incomingSessionAdapter;
	}

	public void setOutgoingSessionAdapter(JmsSessionAdapter outgoingSessionAdapter) {
		this.outgoingSessionAdapter = outgoingSessionAdapter;
	}

	public void setIncomingChannel(String incomingChannelName) {
		this.incomingChannelName = incomingChannelName;
	}
	
	public void setOutgoingChannel(String outgoingChannelName) {
		this.outgoingChannelName = outgoingChannelName;
	}

	protected JAXBSessionCache getJAXBSessionCache() {
		return BeanContext.get(serviceState.getDomain() + ".jaxbSessionCache");
	}

	public void initialize() throws JMSException {
		if (incomingSessionAdapter != null && !incomingSessionAdapter.isInitialized())
			incomingSessionAdapter.initialize();
		if (outgoingSessionAdapter != null && !outgoingSessionAdapter.isInitialized())
			outgoingSessionAdapter.initialize();
	}
	
	public void start() throws JMSException {
		if (incomingSessionAdapter != null && !incomingSessionAdapter.isStarted())
			incomingSessionAdapter.start();
		if (outgoingSessionAdapter != null && !outgoingSessionAdapter.isStarted())
			outgoingSessionAdapter.start();
	}

	public JmsRequestInvoker getInvoker() throws JMSException {
		synchronized (mutex) {
			if (messageInvoker == null) { 
				messageInvoker = createInvoker();
				messageInvoker.initialize();
			}
			return messageInvoker;
		}
	}

	public JmsMessageProducer getProducer() throws NamingException, JMSException {
		synchronized (mutex) {
			if (messageProducer == null && outgoingChannelName != null) {
				messageProducer = createProducer();
				messageProducer.initialize();
			}
			return messageProducer;
		}
	}

	public JmsMessageConsumer getConsumer() throws NamingException, JMSException {
		synchronized (mutex) {
			if (messageConsumer == null && incomingChannelName != null) {
				messageConsumer = createConsumer();
				messageConsumer.initialize();
			}
			return messageConsumer;
		}
	}
	
	protected JmsMessageProducer createProducer() throws JMSException {
		Assert.notNull(getServiceId(), "Service ID must be specified");
		Assert.notNull(outgoingChannelName, "Outgoing channel must be specified");
		JmsProducerDescripter descripter = createProducerDescripter(outgoingChannelName);
		JmsMessageProducer producer = new JmsMessageProducer(outgoingSessionAdapter, descripter);
		return producer;
	}

	protected JmsMessageConsumer createConsumer() throws JMSException {
		Assert.notNull(getServiceId(), "Service ID must be specified");
		JmsConsumerDescripter descripter = null;
		if (incomingChannelName != null)
			descripter = createConsumerDescripter(incomingChannelName);
		else descripter = createConsumerDescripter(getReplyTo());
		//JmsConsumerDescripter descripter = createConsumerDescripter(incomingChannelName);
		JmsMessageConsumer consumer = new JmsMessageConsumer(incomingSessionAdapter, descripter);
		return consumer;
	}

	protected JmsRequestInvoker createInvoker() throws JMSException {
		Assert.notNull(getServiceId(), "Service ID must be specified");
		//Assert.notNull(incomingChannelName, "Incoming channel must be specified");
		Assert.notNull(outgoingChannelName, "Outgoing channel must be specified");
		JmsRequestInvoker invoker = new JmsRequestInvoker(outgoingSessionAdapter);
		invoker.setProducerDescripter(createProducerDescripter(outgoingChannelName));
		//invoker.setConsumerDescripter(createConsumerDescripter(incomingChannelName));
		if (incomingChannelName != null)
			invoker.setConsumerDescripter(createConsumerDescripter(incomingChannelName));
		else invoker.setConsumerDescripter(createConsumerDescripter(getReplyTo()));
		return invoker;
	}

	
	//TODO move to a factory
	JmsProducerDescripter createProducerDescripter(String channelName) {
		JmsProducerDescripter producerDescripter = new JmsProducerDescripter();
		producerDescripter.setDestinationName(getDestinationName(channelName));
		return producerDescripter;
	}

	//TODO move to a factory
	JmsConsumerDescripter createConsumerDescripter(String channelName) {
		JmsConsumerDescripter consumerDescripter = new JmsConsumerDescripter();
		consumerDescripter.setDestinationName(getDestinationName(channelName));
		return consumerDescripter;
	}

	//TODO move to a factory
	JmsConsumerDescripter createConsumerDescripter(Destination destination) {
		JmsConsumerDescripter consumerDescripter = new JmsConsumerDescripter();
		consumerDescripter.setDestination(destination);
		return consumerDescripter;
	}

	private String getDestinationName(String channelName) {
		//ChannelDescripter channelDescripter = ApplicationModel.getChannelDescripterByName(channelName);
		//if (channelDescripter != null)
		//	return channelDescripter.getDestinationName();
		return channelName;
	}

	
	//assuming transferMode is set and valid
	public javax.jms.Message toJMSMessage(Message request) throws JMSException, JAXBException {
		TransferMode transferMode = outgoingSessionAdapter.getTransferMode();
		javax.jms.Message message = null;
		switch (transferMode) {
		case TEXT:
			JAXBWriter jaxb = getJAXBSessionCache().getWriter(getServiceId());
			String xml = jaxb.marshal(request);
			message = outgoingSessionAdapter.createTextMessage(xml);
			break;
		case BINARY:
			message = outgoingSessionAdapter.createObjectMessage(request);
			break;
		}
		message.setObjectProperty("XXAriesCorrelationIdXX", request.getCorrelationId());
		message.setStringProperty("XXAriesProviderNameXX", serviceState.getProviderName());
		message.setStringProperty("XXAriesDestinationNameXX", serviceState.getDestinationName());
        return message;
	}

	public Destination getReplyTo() throws JMSException {
		Destination replyTo = null;
		if (incomingChannelName != null) {
			try {
				//TODO get jndiContext from specified provider
				JndiContext jndiContext = outgoingSessionAdapter.getJndiContext();
				replyTo = (Destination) jndiContext.lookupObject(getDestinationName(incomingChannelName));
			} catch (NamingException e) {
				//TODO default to using temp destination?
				throw new RuntimeException(e);
			}
		} else {
			replyTo = outgoingSessionAdapter.createReplyTo();
		}

        Assert.notNull(replyTo, "ReplyTo must be specified");
		return replyTo;
	}
	
	public Message fromJMSMessage(javax.jms.Message jmsMessage) throws JMSException, JAXBException {
		TransferMode transferMode = null;
		if (incomingSessionAdapter != null)
			transferMode = incomingSessionAdapter.getTransferMode();
		else transferMode = outgoingSessionAdapter.getTransferMode();
		
		Message message = null;
		switch (transferMode) {
		case TEXT:
			Assert.isTrue(jmsMessage instanceof TextMessage, "Unexpected message type: "+jmsMessage.getClass());
			TextMessage textMessage = (TextMessage) jmsMessage;
			String xml = textMessage.getText();
			JAXBReader jaxb = getJAXBSessionCache().getReader(getServiceId());
			message = jaxb.unmarshal(xml);
			break;
		case BINARY:
			Assert.isTrue(jmsMessage instanceof ObjectMessage, "Unexpected message type: "+jmsMessage.getClass());
			ObjectMessage objectMessage = (ObjectMessage) jmsMessage;
			message = (Message) objectMessage.getObject(); 
			break;
		}
		checkForAndProcessFaults(message);
		return message;
	}

	//TODO cache exceptionClass objects?
	//TODO need to handle ClassNotFoundException for incoming type
	public void checkForAndProcessFaults(Message response) throws RuntimeException {
		Fault fault = (Fault) MessageUtil.getPart(response, "@AriesFault@");
		if (fault != null) {
			String type = fault.getType();
			String message = fault.getMessage();
			Class<?>[] parameterTypes = new Class<?>[] { String.class };
			Object[] parameterValues = new Object[] { message };
			Class<?> exceptionClass = null;
			try {
				exceptionClass = ClassUtil.loadClass(type);
				Exception e = ObjectUtil.loadObject(exceptionClass, parameterTypes, parameterValues);
				throw new RuntimeException(e);
			} catch (ClassNotFoundException e) {
				throw new RuntimeException(e);
			}
		}
	}
	


}
