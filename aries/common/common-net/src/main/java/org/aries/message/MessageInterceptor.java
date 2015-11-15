package org.aries.message;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.bean.Proxy;
import org.aries.message.util.MessageConstants;
import org.aries.runtime.BeanContext;


public class MessageInterceptor<T> {
	
	protected final Log log = LogFactory.getLog(getClass());

	protected Proxy<T> proxy;

//	private String serviceId;
	
	
	protected Proxy<T> getEndpoint() {
		return proxy;
	}
	
	public Proxy<T> getProxy() {
		return proxy;
	}

	public void setProxy(Proxy<T> proxy) {
		this.proxy = proxy;	
	}
	
//	public String getServiceId() {
//		return serviceId;
//	}
//	
//	protected void setServiceId(String serviceId) {
//		this.serviceId = serviceId;
//	}
	
//	public void initialize() throws Exception {
//		invoker.initialize();
//	}
	
	protected Message createMessage(String operationName) {
		Message message = new Message();
		//message.addPart("operationName", operationName);
		message.addPart(MessageConstants.PROPERTY_SERVICE_ID, proxy.getServiceId());
		message.addPart(MessageConstants.PROPERTY_OPERATION_NAME, operationName);
		message.addPart(MessageConstants.PROPERTY_USER_NAME, BeanContext.get("org.aries.userName"));
		return message;
	}
	
	protected Message invoke(Message request) {
		try {
			Message response = proxy.invoke(request);
			return response;
		} catch (Exception e) {
			Throwable cause = e.getCause();
			if (cause == null)
				cause = e;
			throw new RuntimeException(cause.getLocalizedMessage());
		}
	}
	
}
