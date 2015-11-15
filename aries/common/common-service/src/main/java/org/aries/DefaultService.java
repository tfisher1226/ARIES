package org.aries;

import org.aries.message.Message;
import org.aries.workflow.ExecutorImpl;


public class DefaultService extends AbstractService {

	public DefaultService() {
		//do nothing by default
	}
	
	public DefaultService(String serviceId) {
		super(serviceId);
	}

	public Message process(Message request) throws Exception {
		//String processName = request.getPart("ARIESMethodName");
		ExecutorImpl executor = (ExecutorImpl) Execution.getExecutor(serviceId);
		Message response = executor.process(request);
	    return response;
	}

}
