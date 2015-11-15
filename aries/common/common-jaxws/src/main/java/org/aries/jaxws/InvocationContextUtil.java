package org.aries.jaxws;


import java.lang.reflect.Field;

import javax.xml.ws.handler.MessageContext;

import org.aries.service.jaxws.MessageContextImpl;
import org.aries.service.jaxws.WebServiceContextImpl;


public class InvocationContextUtil {

	public static void populateInvocationContext(Object serviceInstance, Object correlationId) {
		Field webServiceContextField = null;

		try {
			webServiceContextField = serviceInstance.getClass().getDeclaredField("webServiceContext");
		} catch (SecurityException e1) {
			e1.printStackTrace();
		} catch (NoSuchFieldException e1) {
			//ignore
		}
		
		if (webServiceContextField != null) {
			WebServiceContextImpl webServiceContext = new WebServiceContextImpl();
			webServiceContext.setMessageContext(new MessageContextImpl());
			
			if (correlationId != null) {
				MessageContext messageContext = webServiceContext.getMessageContext();
				messageContext.put("correlationId", correlationId);
			}
			
			try {
				webServiceContextField.setAccessible(true);
				webServiceContextField.set(serviceInstance, webServiceContext);
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		
		/*
		try {
			String methodName = "setInvocationContext";
			Class<?> returnType = void.class;
			List<Class<?>> parameterTypes = new ArrayList<Class<?>>();
			parameterTypes.add(InvocationContext.class);
			Method method = ReflectionUtil.findMethod(serviceInstance.getClass(), methodName, parameterTypes, returnType);
			if (method != null) {
				Object[] parameterValues = new Object[1];
				
				InvocationContext invocationContext = new InvocationContext();
				invocationContext.setCorrelationId(correlationId);
				parameterValues[0] = invocationContext;
	
				//The invocation call occurs here
				ReflectionUtil.invokeMethod(serviceInstance, method, parameterValues);
			}
			
		} catch (Exception e) {
			Throwable rootCause = ExceptionUtils.getRootCause(e);
			//TODO Throw a meaningful exception from here
			throw new RuntimeException(rootCause);
		}
		*/
		
	}

}
