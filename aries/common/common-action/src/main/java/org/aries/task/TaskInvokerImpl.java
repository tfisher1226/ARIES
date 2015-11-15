package org.aries.task;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

import org.aries.Assert;
import org.aries.runtime.BeanContext;
import org.aries.util.ExceptionUtil;
import org.aries.util.ReflectionUtil;


public class TaskInvokerImpl implements TaskInvoker {

	private Object objectInstance;
	
	//private String correlationId;

	private List<Class<?>> parameterTypes;

	private List<Object> parameterValues;

	private Class<?> returnType;

	private String methodName;

	private Method method;
	
	
	public TaskInvokerImpl(Object objectInstance) {
		this.objectInstance = objectInstance;
		parameterTypes = new ArrayList<Class<?>>();
		parameterValues = new ArrayList<Object>();
		returnType = void.class;
	}

	@Override
	public void setMethodName(String name) {
		this.methodName = name;
	}

	@Override
	public void addParameter(String value) {
		addParameter(String.class, value);
	}

	@Override
	public void addParameter(Number value) {
		addParameter(Number.class, value);
	}

	@Override
	public void addParameter(Boolean value) {
		addParameter(Boolean.class, value);
	}

	@Override
	public void addParameter(Object value) {
		addParameter(value.getClass(), value);
	}

	public void addParameter(Class<?> type, Object value) {
		parameterTypes.add(type);
		parameterValues.add(value);
	}
	
	@Override
	public void setReturnType(Class<?> type) {
		this.returnType = type;
	}
	
	@Override
	public Object invoke() throws Exception {
		Assert.notNull(objectInstance, "ObjectInstance must be specified");
		Assert.notEmpty(methodName, "MethodName must be specified");
		//Assert.notEmpty(correlationId, "CorrelationId must be specified");
		Assert.notNull(returnType, "ReturnType must be specified");
		
		try {
			refreshExecutionContext();
			Class<?> objectClass = objectInstance.getClass();
			method = ReflectionUtil.findMethod(objectClass, methodName, parameterTypes, returnType);
			Assert.notNull(method, "Method not found, class="+objectClass+", method="+methodName);
			Object[] parameterArray = parameterValues.toArray(new Object[parameterValues.size()]);
			Object result = method.invoke(objectInstance, parameterArray);
			return result;
	
		} catch (Throwable e) {
			throw ExceptionUtil.rewrap(e);
		}
	}

	protected void refreshExecutionContext() {
		//do nothing by default
	}


}
