package org.aries.rmi;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.JAXBException;

import nam.model.Operation;
import nam.model.Parameter;
import nam.model.Result;
import nam.model.util.OperationUtil;

import org.aries.Assert;
import org.aries.Processor;
import org.aries.jaxb.JAXBReader;
import org.aries.jaxb.JAXBSessionCache;
import org.aries.jaxb.JAXBWriter;
import org.aries.message.Message;
import org.aries.message.util.MessageConstants;
import org.aries.nam.model.old.ServiceDescripter;
import org.aries.runtime.BeanContext;
import org.aries.service.ServiceRepository;
import org.aries.util.ClassUtil;
import org.aries.util.ReflectionUtil;
import org.aries.util.TypeMap;


public class RMIMessageDispatcher implements Processor<Serializable, Serializable> {

	private String serviceId;
	
	private Object instance;

	//TODO externalize this
	private boolean seamRuntimeEnabled = true;
	
	private boolean seamRuntimeResetRequest = true;

	
	@SuppressWarnings("unused")
	private RMIMessageDispatcher() {
		//cannot use this constructor
	}
	
	public RMIMessageDispatcher(String serviceId) {
		this.serviceId = serviceId;
	}

	public RMIMessageDispatcher(String serviceId, Object instance) {
		this.serviceId = serviceId;
		this.instance = instance;
	}

	public boolean isSeamRuntimeEnabled() {
		return seamRuntimeEnabled;
	}

	public void setSeamRuntimeEnabled(boolean seamRuntimeEnabled) {
		this.seamRuntimeEnabled = seamRuntimeEnabled;
	}

	public boolean isSeamRuntimeResetRequest() {
		return seamRuntimeResetRequest;
	}

	public void setSeamRuntimeResetRequest(boolean seamRuntimeResetRequest) {
		this.seamRuntimeResetRequest = seamRuntimeResetRequest;
	}

	public Object getServiceInstance() {
        return instance;
	}

	public void setServiceInstance(Object instance) {
        this.instance = instance;
	}

	protected Object getServiceInstance(String serviceId) {
		//perform dynamic lookup if instance is null
		//SEAM if ((instance == null && seamRuntimeEnabled) || seamRuntimeResetRequest)
		//SEAM 	instance = Component.getInstance(serviceId);
		if (instance == null) {
			ServiceRepository serviceRepository = BeanContext.get("org.aries.serviceRepository");
			instance = serviceRepository.getServiceInstance(serviceId);
		}
		//TODO if null, check BeanContext?
        return instance;
	}

	@Override
	public Serializable process(Serializable xml) {
		//init thread context
   		//BeanContext.begin();
		//SEAM if (Lifecycle.isApplicationInitialized())
		//SEAM 	Lifecycle.beginCall();

    	//get service instance
    	Object serviceInstance = getServiceInstance(serviceId);
        Assert.notNull(serviceInstance, "Service unavailable: "+serviceId);

		try {
			//String domain = NameUtil.getPackageName(serviceId);
    		JAXBSessionCache jaxbSessionCache = BeanContext.get(serviceId + ".jaxbSessionCache");
			JAXBReader jaxbReader = jaxbSessionCache.getReader(serviceId);
			JAXBWriter jaxbWriter = jaxbSessionCache.getWriter(serviceId);
	        Message request = jaxbReader.unmarshal((String) xml);
	        Message response = processMessage(request);
	        xml = jaxbWriter.marshal(response);
	        return xml;
	        
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}

        //get correlation Id
		//Long correlationId = message.getPart("@AriesCorrelationId@");
        //Assert.notNull(correlationId, "Correlation ID not found: "+serviceId);
		//response.addPart("@AriesCorrelationId@", correlationId);
	}

	//TODO cleanup this method...
    //TODO move this to shared class
    protected Message processMessage(Message message) {
    	String methodName = null;
    	Class<?> resultType = null;
    	Class<?>[] parameterTypes = null;
    	Object[] parameterValues = null;
    	
//        ServletContext servletContext = RuntimeContext.getInstance().getServletContext();
//        if (servletContext != null && seamRuntimeEnabled) {
//			ServletLifecycle.beginApplication(servletContext);
//			Lifecycle.beginApplication(Lifecycle.getApplication());
//			Lifecycle.beginCall();
//        }
        
		String serviceId = message.getPart(MessageConstants.PROPERTY_SERVICE_ID);
		String operationName = message.getPart(MessageConstants.PROPERTY_OPERATION_NAME);
		
		//if method exists then create operation descriptor 
    	Operation operation = getOperation(serviceId, operationName);

    	//otherwise resort to using default:
    	if (operation == null) {
        	resultType = Message.class;
        	parameterTypes = new Class<?>[] { Message.class };
        	parameterValues = new Object[] { message };
        	Object service = getServiceInstance(serviceId);
        	Assert.notNull(service, "Service not found: "+serviceId);
       		methodName = ServiceDescripter.DEFAULT_SERVICE_METHOD;
    		Method method = ReflectionUtil.findMethod(service.getClass(), methodName, parameterTypes, resultType);
    		Object result = ReflectionUtil.invokeMethod(service, method, parameterValues);
    		Message response = (Message) result;
        	return response;
    	}
    	
		methodName = operation.getName();
		Result resultDescripter = OperationUtil.getFirstResult(operation);
    	Assert.notNull(resultDescripter, "Result not found: "+serviceId);
    	resultType = TypeMap.INSTANCE.getTypeClassByTypeName(resultDescripter.getType());
    	if (resultType == null)
			try {
				//TODO fix this to work properly
				//TODO this should *only* happen for collection types...
				resultType = ClassUtil.loadClass(resultDescripter.getType());
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
       	List<Parameter> parameterDescripters = operation.getParameters();
       	parameterTypes = new Class<?>[parameterDescripters.size()];
       	parameterValues = new Object[parameterDescripters.size()];
		Iterator<Parameter> iterator = parameterDescripters.iterator();
		for (int i=0; iterator.hasNext(); i++) {
			Parameter parameterDescripter = iterator.next();
			String parameterType = parameterDescripter.getType();
			String parameterName = parameterDescripter.getName();
			Class<?> parameterClassObject = TypeMap.INSTANCE.getTypeClassByTypeName(parameterType);
			parameterTypes[i] = parameterClassObject;
			
			//Handle the special case here where the parameter IS the Message
			if (!parameterClassObject.equals(org.aries.message.Message.class)) {
				parameterValues[i] = message.getPart(parameterName);
			} else {
				parameterValues[i] = message;
			}
		}

    	if (methodName == null)
    		methodName = ServiceDescripter.DEFAULT_SERVICE_METHOD;
    	Object serviceInstance = getServiceInstance(serviceId);
    	Assert.notNull(serviceInstance, "Service not found: "+serviceId);
		Method method = ReflectionUtil.findMethod(serviceInstance.getClass(), methodName, parameterTypes, resultType);
		Object result = ReflectionUtil.invokeMethod(serviceInstance, method, parameterValues);
		Assert.notNull(result, "Result not found");
		if (result instanceof Message) {
			Message response = (Message) result;
	    	return response;
			
		} else {
			String resultName = resultDescripter.getName();
			//TODO adopt all parts from incoming message
			Message response = new Message();
			response.addPart(resultName, result);
	    	return response;
		}
    }

    protected Operation getOperation(String serviceId, String operationName) {
    	Assert.notNull(this.instance, "Service instance null, serviceId: "+serviceId);
    	Assert.equals(this.serviceId, serviceId, "Unexpected serviceId: "+serviceId+", expected: "+this.serviceId);
		Method method = ReflectionUtil.findMethod(instance.getClass(), operationName);
		if (method != null) {
			Operation operation = OperationUtil.createOperation(method);
			return operation;
		}
		return null;
	}

//	protected String getServiceMethod(String serviceId) {
//		ServiceRepository serviceRepository = BeanContext.get("org.aries.serviceRepository");
//		String method = serviceRepository.getServiceMethod(serviceId);
//        return method;
//	}

//	protected Operation getProcessDescripter(String serviceId) {
//		ServiceRepository serviceRepository = BeanContext.get("org.aries.serviceRepository");
//		Service serviceDescripter = serviceRepository.getServiceDescripter(serviceId);
//		if (serviceDescripter != null && serviceDescripter.getOperations().size() > 0) {
//			Operation operation = serviceDescripter.getOperations().get(0);
//			return operation;
//		} 
//		return null;
//	}

//	protected ProcessDescripter getProcessDescripter(String serviceId) {
//		ServiceRepositoryOLD serviceRepository = BeanContext.get("org.aries.serviceRepository");
//		ServiceDescripter serviceDescripter = serviceRepository.getServiceDescripter(serviceId);
//		if (serviceDescripter != null && serviceDescripter.getProcessDescripters().size() > 0) {
//			ProcessDescripter processDescripter = serviceDescripter.getProcessDescripters().get(0);
//			return processDescripter;
//		} 
//		return null;
//	}

//	@Override
//	public String getServiceMethod(String serviceId) {
//		ServiceDescripter serviceDescripter = getServiceDescripter(serviceId);
//		if (serviceDescripter != null && serviceDescripter.getMethodName() != null)
//			return serviceDescripter.getMethodName();
//		ProcessDescripter processDescripter = serviceDescripter.getProcessDescripters().get(0);
//		if (processDescripter != null && processDescripter.getProcessName() != null)
//			return processDescripter.getProcessName();
//		return ServiceDescripter.DEFAULT_SERVICE_METHOD;
//	}

//	protected Object getServiceInstanceOLD(String serviceId) {
//		ServiceRepositoryOLD serviceRepository = BeanContext.get("org.aries.serviceRepository");
//		Object instance = serviceRepository.getServiceInstance(serviceId);
//        return instance;
//	}

}
