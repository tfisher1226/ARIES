package org.aries.client;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import org.aries.notification.NotificationDispatcher;
import org.aries.runtime.BeanContext;


public class ClientInvokedHandler implements InvocationHandler {

	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//		Operation operation = new Operation();
//		operation.setName(method.getName());
//		operation.getParameters().addAll(createParameters(method, args));
//		operation.setResult(createResult(method.getReturnType()));
		if (method.getName().equals("setCorrelationId")) {
			fireClientInvokedNotification(null);
		}
		return null;
	}

	protected void fireClientInvokedNotification(Object userData) throws Exception {
		NotificationDispatcher dispatcher = BeanContext.get("bookshop.buyer.notificationDispatcher");
		dispatcher.fireClientInvokedNotification(this, userData);
	}
	
//	protected Collection<Parameter> createParameters(Method method, Object[] args) {
//		Collection<Parameter> parameters = new ArrayList<Parameter>();
//		Class<?>[] parameterTypes = method.getParameterTypes();
//		for (int i = 0; i < args.length; i++) {
//			String className = parameterTypes[i].getCanonicalName();
//			String parameterName = args[i].toString();
//			Parameter parameter = new Parameter();
//			parameter.setName(parameterName);
//			parameter.setType(className);
//			//parameter.set
//			parameters.add(parameter);
//		}
//		return parameters;
//	}

//	protected Result createResult(Class<?> returnType) {
//		Result result = new Result();
//		result.setType(returnType.getName());
//		result.setName("result");
//		return result;
//	}
	
} 


