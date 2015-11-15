package org.aries.service.jms;

import java.lang.reflect.Method;
import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;
import javax.naming.NamingException;

import nam.model.Operation;
import nam.model.Parameter;
import nam.model.Provider;
import nam.model.Result;
import nam.model.Service;
import nam.model.util.OperationUtil;
import nam.model.util.ServiceUtil;
import nam.runtime.ProviderCache;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.Assert;
import org.aries.jaxb.JAXBReader;
import org.aries.jaxb.JAXBSessionCache;
import org.aries.jaxb.JAXBWriter;
import org.aries.jms.JmsSessionAdapter;
import org.aries.jms.config.JmsConsumerDescripter;
import org.aries.jms.config.JmsProducerDescripter;
import org.aries.jms.p2p.JmsMessageProcessor;
import org.aries.jms.p2p.JmsRequestService;
import org.aries.jms.util.ExceptionUtil;
import org.aries.jndi.JndiContext;
import org.aries.jndi.JndiProxy;
import org.aries.message.Fault;
import org.aries.message.util.MessageUtil;
import org.aries.nam.model.old.OperationDescripter;
import org.aries.nam.model.old.ParameterDescripter;
import org.aries.nam.model.old.ResultDescripter;
import org.aries.runtime.BeanContext;
import org.aries.runtime.RuntimeContext;
import org.aries.service.ServiceRepository;
import org.aries.util.NameUtil;
import org.aries.util.ReflectionUtil;
import org.aries.util.TypeMap;


public class JMSServiceImpl implements JMSService {

	private static Log log = LogFactory.getLog(JMSService.class);

	private String serviceId;
	
	private String transferMode;

	private String destinationName;

	private JmsSessionAdapter session;
	
	private JmsRequestService server;

	private Map<String, Object> context;

	
	@SuppressWarnings("unused")
	private JMSServiceImpl() {
		//cannot use this constructor
	}

	//TODO validate parameter values
	public JMSServiceImpl(JmsSessionAdapter session, String destinationName, String serviceId, String transferMode) {
		this.session = session;
		this.destinationName = destinationName;
		this.serviceId = serviceId;
		this.transferMode = transferMode;
	}

	//TODO validate parameter values
	public JMSServiceImpl(JmsSessionAdapter session, Map<String, Object> context, String destinationName, String serviceId, String transferMode) {
		this(session, destinationName, serviceId, transferMode);
		this.context = context;
	}

	//@Create
	public void initialize() {
		try {
	    	JmsProducerDescripter producerDescripter = new JmsProducerDescripter();
	    	JmsConsumerDescripter consumerDescripter = new JmsConsumerDescripter();
	    	consumerDescripter.setDestinationName(destinationName);

	    	server = new JmsRequestService(session);
	    	server.setConsumerDescripter(consumerDescripter);
	    	server.setProducerDescripter(producerDescripter);
	    	server.setMessageProcessor(createMessageProcessor());
	    	server.initialize();

			log.info("****** JMS service listener registered: destination="+destinationName+", serviceId="+serviceId);
		} catch (Exception e) {
			log.error("****** Service not initialized: "+serviceId+", cause: "+ExceptionUtils.getRootCauseMessage(e));
			log.debug("", e);
			throw new RuntimeException(e);
		} catch (Throwable e) {
            log.error("****** Exception: "+ExceptionUtils.getRootCauseMessage(e));
            log.debug("", e);
			throw new RuntimeException(e);
		}
	}

	protected JmsMessageProcessor createMessageProcessor() {
		return new JmsMessageProcessor() {
		    public Message process(Message message) throws JMSException {
		    	//TODO Thread.currentThread().setUncaughtExceptionHandler(eh);
				return processMessage(message);
		    }
		};
	}

	/*
	 * TODO investigate whether or not this can be reentrant for the same service.
	 * Currently we depend each thread having its own ThreadLocal ExecutionContext.
	 * This can be changed though if we want to allow this code to be reentrant.
	 */
    protected Message processMessage(Message request) throws JMSException {
		Message response = createResponseMessage();

		try {
        	if (context != null)
        		BeanContext.begin(context);
            log.debug("****** Message: "+serviceId+": "+request);

            //printDescripters();
            Object serviceInstance = getService(serviceId);
			if (serviceInstance == null) {
	            log.warn("****** Service unavailable: "+serviceId);
	            return request;
			}
			
            setReplyTo(request);
            log.info("****** Invoking: "+serviceId);
			org.aries.message.Message platformRequest = (org.aries.message.Message) getPlatformMessage(request); 
            org.aries.message.Message platformResponse = processMessage(serviceInstance, platformRequest);
            setPlatformMessage(response, platformResponse);
            log.debug("****** Complete");
            
        } catch (Throwable e) {
        	//TODO get message here
            log.error("****** Exception: "+ExceptionUtils.getRootCauseMessage(e));
            //log.error("****** Exception: "+ExceptionUtils.getRootCauseMessage(e), ExceptionUtils.getRootCause(e));
            log.debug("", e);
            addExceptionToMessage(response, e);
        }

        return response;
	}

    //TODO will we ever need to override what is currently set (or just only if missing...)
	protected void setReplyTo(Message request) throws NamingException, JMSException {
	    Destination destination = request.getJMSReplyTo();
		if (destination == null) {
			String providerName = request.getStringProperty("XXAriesProviderNameXX");
			String destinationName = request.getStringProperty("XXAriesDestinationNameXX");
	    	ProviderCache providerCache = BeanContext.get("org.aries.providerCache");
			Provider provider = providerCache.getProviderByName(providerName);
			Assert.notNull(provider, "Provider not found: "+providerName);
			JndiContext jndiContext = getJndiContext(provider);
		    destination = (Destination) jndiContext.lookupObject(destinationName);
		    request.setJMSReplyTo(destination);
		}
	}

//    //TODO will we ever need to override what is currently set (or just only if missing...)
//	protected void setReplyToOLD(Message request) throws NamingException, JMSException {
//	    Destination destination = request.getJMSReplyTo();
//		if (destination == null) {
//			String providerName = request.getStringProperty("AriesProviderName");
//			String destinationName = request.getStringProperty("AriesDestinationName");
//			ServiceDescripter serviceDescripter = Execution.getContext().getServiceDescripter();
//			ApplicationProfile applicationProfile = serviceDescripter.getApplicationProfile();
//			ProviderDescripter providerDescripter = applicationProfile.getProviderDescripterByName(providerName);
//			Assert.notNull(providerDescripter, "Provider not found: "+providerName);
//			JndiContext jndiContext = getJndiContext(providerDescripter);
//		    destination = (Destination) jndiContext.lookupObject(destinationName);
//		    request.setJMSReplyTo(destination);
//		}
//	}

	protected JndiContext getJndiContext(Provider provider) {
    	JndiProxy jndiContext = new JndiProxy();
    	jndiContext.setConnectionUrl(provider.getConnectionUrl());
    	jndiContext.setContextFactory(provider.getJndiContext().getContextFactory());
    	jndiContext.setUserName(provider.getUserName());
    	jndiContext.setPassword(provider.getPassword());
    	return jndiContext;
    }

//	protected JndiContext getJndiContext(ProviderDescripter providerDescripter) {
//    	JndiProxy jndiContext = new JndiProxy();
//    	jndiContext.setConnectionUrl(providerDescripter.getConnectionUrl());
//    	jndiContext.setContextFactory(providerDescripter.getContextFactory());
//    	jndiContext.setUserName(providerDescripter.getSecurityPrinciple());
//    	jndiContext.setPassword(providerDescripter.getSecurityCredentials());
//    	return jndiContext;
//    }

    protected void addExceptionToMessage(Message message, Throwable exception) throws JMSException {
    	org.aries.message.Message platformMessage = new org.aries.message.Message();
    	Fault fault = new Fault();
    	Throwable rootCause = ExceptionUtils.getRootCause(exception);
    	if (rootCause == null)
    		rootCause = exception;
    	fault.setMessage(rootCause.getMessage());
    	fault.setType(rootCause.getClass().getName());
    	MessageUtil.addPart(platformMessage, "@AriesFault@", fault);
   		setPlatformMessage(message, platformMessage);
    }
    
    protected void setPlatformMessage(Message message, org.aries.message.Message platformMessage) throws JMSException {
		if (transferMode.toLowerCase().equals("text")) {
			try {
				String xml = marshal(platformMessage);
				Assert.isTrue(message instanceof TextMessage, "Unexpected message type: "+message.getClass());
				TextMessage textMessage = (TextMessage) message;
				textMessage.setText(xml);
			} catch (Exception e) {
				throw ExceptionUtil.getAs(e);
			}
		}
		
		if (transferMode.toLowerCase().equals("binary")) {
			Assert.isTrue(message instanceof ObjectMessage, "Unexpected message type: "+message.getClass());
			ObjectMessage objectMessage = (ObjectMessage) message;
			objectMessage.setObject(platformMessage);
		}
    }

    protected org.aries.message.Message getPlatformMessage(Message message) throws JMSException {
    	org.aries.message.Message response = null;
    	
		if (transferMode.toLowerCase().equals("text")) {
			try {
				Assert.isTrue(message instanceof TextMessage, "Unexpected message type: "+message.getClass());
				TextMessage textMessage = (TextMessage) message;
				String xml = textMessage.getText();
				response = unmarshal(xml);
			} catch (Exception e) {
				throw ExceptionUtil.getAs(e);
			}
		}
		
		if (transferMode.toLowerCase().equals("binary")) {
			Assert.isTrue(message instanceof ObjectMessage, "Unexpected message type: "+message.getClass());
			ObjectMessage objectMessage = (ObjectMessage) message;
			response = (org.aries.message.Message) objectMessage.getObject(); 
		}
		
		String correlationId = message.getStringProperty("XXAriesCorrelationIdXX");
		response.setCorrelationId(correlationId);
		return response;
	}

    protected Message createResponseMessage() throws JMSException {
		String mode = transferMode.toLowerCase();
		if (mode.equals("text"))
			return server.createTextMessage();
		if (mode.equals("binary"))
			return server.createObjectMessage();
		throw new JMSException("Unexpected transfer mode: "+mode);
    }
    
	protected String marshal(org.aries.message.Message message) throws Exception {
		String domain = NameUtil.getPackageName(serviceId);
		JAXBSessionCache jaxbSessionCache = BeanContext.get(domain + ".jaxbSessionCache");
		JAXBWriter writer = jaxbSessionCache.getWriter(serviceId);
		String xml = writer.marshal(message);
		return xml;
		
//		Class<?>[] classesToBeMarshalled = getClassesToBeMarshalled(message);
//		JAXBContext context = JAXBContext.newInstance(classesToBeMarshalled);
//		Marshaller marshaller = context.createMarshaller();
//		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
//		StringWriter writer = new StringWriter();
//		marshaller.marshal(message, writer);
//		String outString = writer.toString();
//		return outString;
	}

	protected org.aries.message.Message unmarshal(String xml) throws Exception {
		String domain = NameUtil.getPackageName(serviceId);
		JAXBSessionCache jaxbSessionCache = BeanContext.get(domain + ".jaxbSessionCache");
		JAXBReader reader = jaxbSessionCache.getReader(serviceId);
		org.aries.message.Message message = reader.unmarshal(xml);
		return message;

//		Class<?>[] classesToBeUnmarshalled = getClassesToBeUnmarshalled();
//		JAXBContext context = JAXBContext.newInstance(classesToBeUnmarshalled);
//		Unmarshaller unmarshaller = context.createUnmarshaller();
//		StringReader reader = new StringReader(xml);
//		org.aries.message.Message message = (org.aries.message.Message) unmarshaller.unmarshal(reader);
//		return message;
	}
	
//	protected Class<?>[] getClassesToBeMarshalledOLD(org.aries.message.Message message) {
//		List<Class<?>> classesToBeBound = new ArrayList<Class<?>>();
//		classesToBeBound.addAll(MessageUtil.getClassesFromMessage(message));
//		ServiceRepositoryOLD serviceRepository = BeanContext.get("org.aries.serviceRepository");
//		ServiceDescripter serviceDescripter = serviceRepository.getServiceDescripter(serviceId);
//		ProcessDescripter processDescripter = serviceDescripter.getProcessDescripters().get(0);
//		List<ParameterDescripter> parameterDescripters = processDescripter.getParameterDescripters();
//		String returnType = processDescripter.getResultDescripter().getType();
//		classesToBeBound.add(TypeMap.INSTANCE.getTypeClass(returnType));
//		Iterator<ParameterDescripter> iterator = parameterDescripters.iterator();
//		for (int i=2; iterator.hasNext(); i++) {
//			ParameterDescripter parameterDescripter = iterator.next();
//			String parameterType = parameterDescripter.getType();
//			classesToBeBound.add(TypeMap.INSTANCE.getTypeClass(parameterType));
//		}
//		Class<?>[] classesArray = classesToBeBound.toArray(new Class<?>[classesToBeBound.size()]);
//		return classesArray;
//	}
	
//	protected Class<?>[] getClassesToBeUnmarshalledOLD() {
//		ServiceRepositoryOLD serviceRepository = BeanContext.get("org.aries.serviceRepository");
//		ServiceDescripter serviceDescripter = serviceRepository.getServiceDescripter(serviceId);
//		ProcessDescripter processDescripter = serviceDescripter.getProcessDescripters().get(0);
//		List<ParameterDescripter> parameterDescripters = processDescripter.getParameterDescripters();
//		Iterator<ParameterDescripter> iterator = parameterDescripters.iterator();
//		Class<?>[] classesToBeBound = new Class<?>[parameterDescripters.size()+1];
//		classesToBeBound[0] = org.aries.message.Message.class;
//		for (int i=1; iterator.hasNext(); i++) {
//			ParameterDescripter parameterDescripter = iterator.next();
//			String parameterType = parameterDescripter.getType();
//			classesToBeBound[i] = TypeMap.INSTANCE.getTypeClass(parameterType);
//		}
//		return classesToBeBound;
//	}
	
	
	//TODO move this to shared class
    protected org.aries.message.Message processMessage(Object serviceInstance, org.aries.message.Message message) throws JMSException {
		ServiceRepository serviceRepository = BeanContext.get("org.aries.serviceRepository");
		Service service = (Service) serviceRepository.getServiceDescripter(serviceId);
		
		Operation operation = ServiceUtil.getOperations(service).get(0);
		String operationName = operation.getName();
    	
    	if (operationName == null) {
        	operationName = "process";
        	Class<?> resultType = org.aries.message.Message.class;
        	Class<?>[] parameterTypes = new Class<?>[] { org.aries.message.Message.class };
        	Method operationInstance = ReflectionUtil.findMethod(serviceInstance.getClass(), operationName, parameterTypes, resultType);

        	Object[] parameterValues = new Object[] { message };
    		Object result = ReflectionUtil.invokeMethod(serviceInstance, operationInstance, parameterValues);
    		org.aries.message.Message response = (org.aries.message.Message) result;
        	return response;
        	
    	} else {
			//get return type
    		String resultName = null;
    		Class<?> resultType = void.class;
			TypeMap typeMap = TypeMap.INSTANCE;
    		Result result = OperationUtil.getFirstResult(operation);
			if (result != null) {
				resultName = result.getName();
				String returnTypeName = result.getType();
				String packageName = NameUtil.getPackageNameFromType(returnTypeName);
				String className = NameUtil.getClassNameFromType(returnTypeName);
	        	resultType = typeMap.getTypeClassByClassName(packageName+"."+className);
    		}
        	
			//get parameter types
			List<Parameter> parameters = operation.getParameters();
			Iterator<Parameter> iterator = parameters.iterator();
			Class<?>[] parameterTypes = new Class<?>[parameters.size()];
			Object[] parameterValues = new Object[parameterTypes.length];
			for (int i=0; iterator.hasNext(); i++) {
				Parameter parameter = iterator.next();
				String parameterType = parameter.getType();
				String parameterName = parameter.getName();
				String packageName = NameUtil.getPackageNameFromType(parameterType);
				String className = NameUtil.getClassNameFromType(parameterType);
				parameterTypes[i] = typeMap.getTypeClassByClassName(packageName+"."+className);
				parameterValues[i] = message.getPart(parameterName);
			}

			//prepare execution context for this thread
			//ExecutionContext context = Execution.getContext();
			//context.setServiceDescripter(service);

			//TODO do we ever want this?
			//populate invocation context of service instance
			//InvocationContextUtil.populateInvocationContext(serviceInstance, message.getCorrelationId());
			
			RuntimeContext runtimeContext = null; //RuntimeContextRegistry.getRuntimeContext();
			runtimeContext.setCorrelationId(message.getCorrelationId());

			//invoke the service method 
			Method operationInstance = ReflectionUtil.findMethod(serviceInstance.getClass(), operationName, parameterTypes, resultType);
    		Object returnValue = ReflectionUtil.invokeMethod(serviceInstance, operationInstance, parameterValues);
    		org.aries.message.Message response = new org.aries.message.Message();
    		response.setCorrelationId(message.getCorrelationId());
    		if (resultName != null)
    			response.addPart(resultName, returnValue);
        	return response;
    	}
	}

	//TODO move this to shared class
    protected org.aries.message.Message processMessageOLD(Object serviceInstance, OperationDescripter operationDescripter, org.aries.message.Message message) throws JMSException {
		ServiceRepository serviceRepository = BeanContext.get("org.aries.serviceRepository");
		Service service = (Service) serviceRepository.getServiceDescripter(serviceId);
		
		Operation operation = ServiceUtil.getOperations(service).get(0);
		String operationName = operation.getName();
    	
    	if (operationName == null) {
        	operationName = "process";
        	Class<?> resultType = org.aries.message.Message.class;
        	Class<?>[] parameterTypes = new Class<?>[] { org.aries.message.Message.class };
        	Method operationInstance = ReflectionUtil.findMethod(serviceInstance.getClass(), operationName, parameterTypes, resultType);

        	Object[] parameterValues = new Object[] { message };
    		Object result = ReflectionUtil.invokeMethod(serviceInstance, operationInstance, parameterValues);
    		org.aries.message.Message response = (org.aries.message.Message) result;
        	return response;
        	
    	} else {
			//get return type
			TypeMap typeMap = TypeMap.INSTANCE;
			List<ResultDescripter> resultDescripters = operationDescripter.getResultDescripters();
			ResultDescripter resultDescripter = resultDescripters.get(0);
			String resultName = resultDescripter.getName();
			String returnTypeName = resultDescripter.getType();
        	Class<?> returnType = typeMap.getTypeClassByTypeName(returnTypeName);
        	
			//get parameter types
			List<ParameterDescripter> parameterDescripters = operationDescripter.getParameterDescripters();
			Iterator<ParameterDescripter> iterator = parameterDescripters.iterator();
			Class<?>[] parameterTypes = new Class<?>[parameterDescripters.size()];
			Object[] parameterValues = new Object[parameterTypes.length];
			for (int i=0; iterator.hasNext(); i++) {
				ParameterDescripter parameterDescripter = iterator.next();
				String parameterType = parameterDescripter.getType();
				String parameterName = parameterDescripter.getName();
				parameterTypes[i] = typeMap.getTypeClassByTypeName(parameterType);
				parameterValues[i] = message.getPart(parameterName);
			}

			//prepare execution context for this thread
			//ExecutionContext context = Execution.getContext();
			//context.setServiceDescripter(service);

			//invoke the service method 
			Method operationInstance = ReflectionUtil.findMethod(serviceInstance.getClass(), operationName, parameterTypes, returnType);
    		Object result = ReflectionUtil.invokeMethod(serviceInstance, operationInstance, parameterValues);
    		org.aries.message.Message response = new org.aries.message.Message();
//    		if (resultName == null)
//    			resultName = "result";
    		response.addPart(resultName, result);
        	return response;
    	}
	}

//    //TODO move this to shared class
//    protected org.aries.message.Message processMessageORIG(Object service, org.aries.message.Message message) throws JMSException {
//    	String methodName = "process";
//    	Class<?>[] parameterTypes = new Class<?>[] { org.aries.message.Message.class };
//    	Object[] parameterValues = new Object[] { message };
//    	Class<?> resultType = org.aries.message.Message.class;
//		Method method = ReflectionUtil.findMethod(service.getClass(), methodName, parameterTypes, resultType);
//		Object result = ReflectionUtil.invokeMethod(service, method, parameterValues);
//		org.aries.message.Message response = (org.aries.message.Message) result;
//    	return response;
//    }

	public Object getService(String serviceId) throws Exception {
		ServiceRepository serviceRepository = BeanContext.get("org.aries.serviceRepository");
		//log.info("====================================================="+serviceRepository+", "+serviceId);
		//log.info("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$"+ BeanContext.class.getClassLoader());
		//log.info("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$"+ BeanContext.class.getClassLoader().getParent());
		Object service = serviceRepository.getServiceInstance(serviceId);
        return service;
	}

	public void reset() throws RemoteException {
		//BeanContext.reset();
	}

}
