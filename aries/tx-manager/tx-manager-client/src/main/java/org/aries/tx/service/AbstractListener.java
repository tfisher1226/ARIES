package org.aries.tx.service;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.Assert;
import org.aries.client.AbstractEndpoint;
import org.aries.jaxb.JAXBReader;
import org.aries.jaxb.JAXBSessionCache;
import org.aries.jaxb.JAXBWriter;
import org.aries.message.Message;
import org.aries.message.util.MessageConstants;
import org.aries.runtime.BeanContext;
import org.aries.tx.ConversationContext;
import org.aries.tx.module.Bootstrapper;
import org.aries.util.ExceptionUtil;
import org.aries.util.NameUtil;
import org.aries.util.ReflectionUtil;


public abstract class AbstractListener extends AbstractEndpoint implements Listener {

	public abstract String getModuleId();
	
	public abstract String getServiceId();
	
	public abstract Object getDelegate();

	
	protected void validate(Message message) {
		//nothing for now
	}
	
	protected ConversationContext getContext() {
		return null;
	}
	
	
	protected Log log = LogFactory.getLog(getClass());

	private Map<String, Method> methodCache = new HashMap<String, Method>();
	

	public boolean isInitialized() {
		return isInitialized(getModuleId());
	}
	
	public boolean isInitialized(String moduleId) {
		if (Bootstrapper.isInitialized(moduleId))
			return true;
		return false;
	}
	
	protected String processAsText(String requestXml) {
		try {
			String serviceId = getServiceId();
			String domain = NameUtil.getPackageName(serviceId);
    		JAXBSessionCache jaxbSessionCache = BeanContext.get(domain + ".jaxbSessionCache");
			JAXBReader jaxbReader = jaxbSessionCache.getReader(serviceId);
			JAXBWriter jaxbWriter = jaxbSessionCache.getWriter(serviceId);
			
			Message input = jaxbReader.unmarshal(requestXml);
			Serializable output = processAsBinary(input);
			String responseXml = jaxbWriter.marshal(output);
			return responseXml;
			
		} catch (RuntimeException e) {
			throw e;
			
		} catch (Throwable e) {
			log.error(e);
			//TODO send this exception to "invalid" queue
			//String reason = e.getMessage();
			//throw new JMSException(reason);
			Exception cause = ExceptionUtil.getRootCause(e);
			throw new RuntimeException(cause.getLocalizedMessage());
		}
	}
	
	protected Message processAsBinary(Message request) {
		try {
			validate(request);
			Message response = invokeDelegate(request);
			return response;
			
		} catch (Throwable e) {
			log.error(e);
			//TODO send this exception to "invalid" queue
			Exception cause = getExceptionToThrow(e);
			throw new RuntimeException(cause.getLocalizedMessage(), cause);
		}
	}

	protected Exception getExceptionToThrow(Throwable e) {
		Exception cause = ExceptionUtil.getRootCause(e);
		//String reason = e.getMessage();
		//throw new JMSException(reason);
		return cause;
	}

	protected Method getMethod(String methodName) {
		Method method = methodCache.get(methodName);
		if (method != null)
			return method;
		method = ReflectionUtil.findMethod(getDelegate().getClass(), methodName);
		if (method != null)
			return method;
		return null;
	}

	protected Message invokeDelegate(Message request) {
		String methodName = request.getPart(MessageConstants.PROPERTY_OPERATION_NAME);
		Method method = getMethod(methodName);
		Object result = ReflectionUtil.invokeMethod(getDelegate(), method, request);
		if (result == null)
			return null;
		if (result instanceof Message == false)
			throw new RuntimeException("Unexpected result: "+result.getClass());
		Message response = (Message) result;
		Exception exception = response.getPart("exception");
		if (exception != null)
			throw new RuntimeException(exception.getMessage());
		return (Message) result;
	}

	protected String getCorrelationId(Message message) throws Exception {
		Assert.notNull(message, "Object not found in message");
		String correlationId  = message.getPart("CorrelationId");
		//String correlationId = message.getStringProperty("CorrelationId");
		//Assert.notNull(correlationId, "CorrelationId not found");
		return correlationId;
	}

	protected String getTransactionId(Message message) throws Exception {
		Assert.notNull(message, "Object not found in message");
		String transactionId  = message.getPart("TransactionId");
		//String transactionId = message.getStringProperty("TransactionId");
		//Assert.notNull(transactionId, "TransactionId not found");
		return transactionId;
	}

	protected Object getTransactionContext(Message message) throws Exception {
		Assert.notNull(message, "Object not found in message");
		Object transactionContext = message.getPart("TransactionContext");
		//Object transactionContext = message.getObjectProperty("TransactionContext");
		Assert.notNull(transactionContext, "TransactionContext not found");
		return transactionContext;
	}

	protected <T> T getPayload(Message message) throws Exception {
		Assert.notNull(message, "Object not found in message");
		T payload = message.getPart("Payload");
		return payload;
	}
	
}
