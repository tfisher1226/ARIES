package org.aries.task;


public interface TaskInvoker {

	public void setMethodName(String methodName);

	//public void setCorrelationId(String correlationId);

	public void addParameter(String parameterValue);

	public void addParameter(Number parameterValue);

	public void addParameter(Boolean parameterValue);

	public void addParameter(Object parameterValue);

	public void setReturnType(Class<?> returnType);

	public Object invoke() throws Exception;

}
