package admin;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.Invoker;
import org.aries.message.Message;
import org.aries.message.util.MessageConstants;
import org.aries.runtime.BeanContext;


public class MessageInterceptor implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	protected final Log log = LogFactory.getLog(getClass());

	private Invoker invoker;

//	private String serviceId;
	
	
	public Invoker getInvoker() {
		return invoker;
	}

	public void setInvoker(Invoker invoker) {
		this.invoker = invoker;
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
		message.addPart(MessageConstants.PROPERTY_SERVICE_ID, invoker.getServiceId());
		message.addPart(MessageConstants.PROPERTY_OPERATION_NAME, operationName);
		User user = BeanContext.get("org.aries.user");
		if (user != null)
			message.addPart(MessageConstants.PROPERTY_USER_NAME, user.getUserName());
		return message;
	}
	
	protected Message invoke(Message request) {
		try {
			Message response = invoker.invoke(request);
			return response;
		} catch (Exception e) {
			Throwable cause = e.getCause();
			if (cause == null)
				cause = e;
			throw new RuntimeException(cause.getLocalizedMessage());
		}
	}
	
}
