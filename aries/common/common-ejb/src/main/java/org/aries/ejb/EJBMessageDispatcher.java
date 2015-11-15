package org.aries.ejb;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;

import javax.naming.NamingException;
import javax.xml.bind.JAXBException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.Assert;
import org.aries.jaxb.JAXBReader;
import org.aries.jaxb.JAXBSessionCache;
import org.aries.jaxb.JAXBWriter;
import org.aries.message.Message;
import org.aries.message.util.MessageUtil;
import org.aries.nam.model.old.ParameterDescripter;
import org.aries.nam.model.old.ResultDescripter;
import org.aries.runtime.BeanContext;
import org.aries.service.ServiceRepository;
import org.aries.util.ClassUtil;
import org.aries.util.NameUtil;
import org.aries.util.ReflectionUtil;
import org.aries.util.TypeMap;
import org.aries.util.TypeUtil;


public class EJBMessageDispatcher {

	private static Log log = LogFactory.getLog(EJBMessageDispatcher.class);

	private EJBEndpointContext endpointContext;

	private String serviceId;

	
	public EJBMessageDispatcher() {
		//System.out.println();
	}
	
	public EJBMessageDispatcher(EJBEndpointContext endpointContext) {
		this.endpointContext = endpointContext;
		this.serviceId = endpointContext.getServiceId();
	}


	@SuppressWarnings("unchecked")
	public <T extends Serializable> T process(Serializable request, String serviceId, String correlationId) throws Exception {
		//init thread context
   		//BeanContext.begin();
		
		//SEAM if (Lifecycle.isApplicationInitialized())
		//SEAM 	Lifecycle.beginCall();

    	//get service instance
    	//Object serviceInstance = getServiceInstance(serviceId);
        //Assert.notNull(serviceInstance, "Service unavailable: "+serviceId);

		if (request instanceof String)
			return (T) processAsXML((String) request, serviceId, correlationId);
		if (request instanceof String)
			return (T) processMessage((Message) request, serviceId, correlationId);
		return null;
	}
	
	protected String processAsXML(String xml, String serviceId, String correlationId) throws Exception {
		try {
			String domain = NameUtil.getPackageName(serviceId);
    		JAXBSessionCache jaxbSessionCache = BeanContext.get(domain + ".jaxbSessionCache");
			JAXBReader jaxbReader = jaxbSessionCache.getReader(serviceId);
			JAXBWriter jaxbWriter = jaxbSessionCache.getWriter(serviceId);
	        Message request = jaxbReader.unmarshal(xml);
	        
	        Message response = processMessage(request, serviceId, correlationId);
	        xml = jaxbWriter.marshal(response);
	        return xml;
	        
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
	}

	//TODO cleanup this method...
    //TODO move this to shared class
	protected Message processMessage(Message request, String serviceId, Object correlationId) throws Exception {
    	//String serviceId = request.getPart("ARIESServiceName");
    	//String operationName = request.getPart("ARIESMethodName");
		
    	endpointContext = EJBEndpointMap.INSTANCE.getEndpointContext(serviceId);
		String methodName = endpointContext.getOperationDescripter().getName();

		//String resultType = endpointContext.getOperationDescripter().getResultDescripter().getType();
		//String resultName = endpointContext.getOperationDescripter().getResultDescripter().getName();
		Object serviceInstance = null;
		
		List<ParameterDescripter> parameterDescripters = endpointContext.getOperationDescripter().getParameterDescripters();
		List<ResultDescripter> resultDescripters = endpointContext.getOperationDescripter().getResultDescripters();

		String resultName = null;
		if (!resultDescripters.isEmpty()) {
			ResultDescripter resultDescripter = resultDescripters.get(0);
			resultName = resultDescripter.getName();
		} 

//		String correlationId = request.getCorrelationId();
//		if (correlationId != null) {
//			propagateCorrelationIdToHeaderHandler(correlationId);
//			((BindingProvider) port).getRequestContext().put("correlationId", correlationId);
//			((BindingProvider) port).getResponseContext().put("correlationId", correlationId);
//		}
		
		int parameterCount = parameterDescripters.size();
		if (methodName.equals("process")) {
			parameterCount = 1;
		}
		
		Class<?>[] parameterTypes = new Class<?>[parameterCount];
		Object[] parameterValues = new Object[parameterCount];
		Class<?> returnType = void.class;

		if (methodName.equals("process")) {
			returnType = Message.class;
			parameterTypes[0] = Message.class;
			parameterValues[0] = request;  
		} else {
			if (!StringUtils.isEmpty(resultName))
				returnType = Object.class;
			Iterator<ParameterDescripter> iterator = parameterDescripters.iterator();
			for (int i=0; iterator.hasNext(); i++) {
				ParameterDescripter parameterDescripter = iterator.next();
				String packageName = TypeUtil.getPackageName(parameterDescripter.getType());
				String className = TypeUtil.getClassName(parameterDescripter.getType());
				Class<?> parameterClass = ClassUtil.loadClass(packageName+"."+className);
				Object parameterValue = request.getPart(parameterDescripter.getName());
				parameterTypes[i] = parameterClass;
				parameterValues[i] = parameterValue;
			}
		}
		
		String jndiName = endpointContext.getJndiName();
		serviceInstance = lookupServiceInstance(jndiName);
		//serviceInstance = getServiceInstance(serviceId);
		
		//TODO
		//InvocationContextUtil.populateInvocationContext(serviceInstance, correlationId);
		
		//Method method = ReflectionUtil.findMethodByName(serviceInstance.getClass(), methodName);
		Method method = ReflectionUtil.findMethod(serviceInstance.getClass(), methodName, parameterTypes, returnType);

		Assert.notNull(method, "Method not found: "+methodName);
		Assert.notNull(method.getReturnType(), "Return type not specified");
		
		try {
			logInvokeServiceStarted(serviceId, methodName);
			
			//The invocation call occurs here
			//Object resultValue = method.invoke(serviceInstance, parameterValues);
			Object resultValue = ReflectionUtil.invokeMethod(serviceInstance, method, parameterValues);
			
			//assure we got result only if we are expecting one
			if (resultValue == null && !StringUtils.isEmpty(resultName))
				Assert.notNull(resultValue, "Unexpected no result");
			
			Message response = null;
			if (resultValue instanceof Message) {
				response = (Message) resultValue;
				
			} else {
				response = new Message();
				//TODO adopt all parts from incoming message
				MessageUtil.addPart(response, resultName, resultValue);
				//response.addPart(resultName, resultValue);
			}

			logInvokeServiceComplete(serviceId, methodName);
	    	return response;

		} catch (Exception e) {
			String message = ExceptionUtils.getRootCauseMessage(e);
			logInvokeServiceAborted(serviceId, methodName, message);
			//TODO Throw a meaningful exception from here
			throw new RuntimeException(e);
		}
	}

	
//    protected Message processMessageOLD(Message message) {
//    	String methodName = null;
//    	Class<?> resultType = null;
//    	Class<?>[] parameterTypes = null;
//    	Object[] parameterValues = null;
//    	ProcessDescripter processDescripter = getProcessDescripter(serviceId);
//    	if (processDescripter == null) {
//        	resultType = Message.class;
//        	parameterTypes = new Class<?>[] { Message.class };
//        	parameterValues = new Object[] { message };
//        	Object service = getServiceInstance(serviceId);
//        	Assert.notNull(service, "Service not found: "+serviceId);
//       		methodName = ServiceDescripter.DEFAULT_SERVICE_METHOD;
//    		Method method = ReflectionUtil.findMethod(service.getClass(), methodName, parameterTypes, resultType);
//    		Object result = ReflectionUtil.invokeMethod(service, method, parameterValues);
//    		Message response = (Message) result;
//        	return response;
//        	
//    	} else {
//    		methodName = processDescripter.getProcessName();
//        	ResultDescripter resultDescripter = processDescripter.getResultDescripter();
//        	Assert.notNull(resultDescripter, "ResultDescripter not found: "+serviceId);
//        	resultType = TypeMap.INSTANCE.getTypeClassByTypeName(resultDescripter.getType());
//           	List<ParameterDescripter> parameterDescripters = processDescripter.getParameterDescripters();
//           	parameterTypes = new Class<?>[parameterDescripters.size()];
//           	parameterValues = new Object[parameterDescripters.size()];
//   			Iterator<ParameterDescripter> iterator = parameterDescripters.iterator();
//   			for (int i=0; iterator.hasNext(); i++) {
//   				ParameterDescripter parameterDescripter = iterator.next();
//   				String parameterType = parameterDescripter.getType();
//   				String parameterName = parameterDescripter.getName();
//   				Class<?> parameterClassObject = TypeMap.INSTANCE.getTypeClassByTypeName(parameterType);
//				parameterTypes[i] = parameterClassObject;
//   				
//				//Handle the special case here where the parameter IS the Message
//   				if (!parameterClassObject.equals(org.aries.message.Message.class)) {
//   					parameterValues[i] = message.getPart(parameterName);
//   				} else {
//   					parameterValues[i] = message;
//   				}
//   			}
//
//        	if (methodName == null)
//        		methodName = ServiceDescripter.DEFAULT_SERVICE_METHOD;
//        	Object service = getServiceInstance(serviceId);
//	    	Assert.notNull(service, "Service not found: "+serviceId);
//			Method method = ReflectionUtil.findMethod(service.getClass(), methodName, parameterTypes, resultType);
//			Object result = ReflectionUtil.invokeMethod(service, method, parameterValues);
//			Assert.notNull(result, "Result not found");
//			if (result instanceof Message) {
//				Message response = (Message) result;
//		    	return response;
//				
//			} else {
//				String resultName = resultDescripter.getName();
//				//TODO adopt all parts from incoming message
//				Message response = new Message();
//				response.addPart(resultName, result);
//		    	return response;
//			}
//    	}
//    }

    protected Class<?> getResultType(ResultDescripter resultDescripter) {
    	if (resultDescripter != null && resultDescripter.getType() != null)
    		return TypeMap.INSTANCE.getTypeClassByTypeName(resultDescripter.getType());
		return Message.class;
	}

//	protected String getServiceMethod(String serviceId) {
//		ServiceRepository serviceRepository = BeanContext.get("org.aries.serviceRepository");
//		String method = serviceRepository.getServiceMethod(serviceId);
//        return method;
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

	protected Object getServiceInstance(String serviceId) {
		ServiceRepository serviceRepository = BeanContext.get("org.aries.serviceRepository");
		Object instance = serviceRepository.getServiceInstance(serviceId);
        return instance;
	}

	protected Object lookupServiceInstance(String jndiName) throws NamingException {
		Object instance = endpointContext.getJndiContext().lookupObject(jndiName);
        return instance;
	}
	
	protected void logInvokeServiceStarted(String jndiName, String methodName) {
		//EventLog.getInstance().trace("Invoke Service started: "+serviceURL+"/"+operation);
		log.info("Invoke Service started: "+jndiName+"/"+methodName);
	}

	protected void logInvokeServiceComplete(String jndiName, String methodName) {
		//EventLog.getInstance().trace("Invoke Service complete: "+serviceURL+"/"+operation);
		log.info("Invoke Service complete: "+jndiName+"/"+methodName);
	}

	protected void logInvokeServiceAborted(String jndiName, String methodName, String exception) {
		//EventLog.getInstance().trace("Invoke Service aborted: "+serviceURL+"/"+methodName);
		log.info("Invoke Service aborted: "+jndiName+"/"+methodName);
	}

}
