package org.aries.workflow;

import javax.xml.ws.WebServiceContext;

import org.aries.nam.model.old.ServiceDescripter;


public class ExecutorFactory {

	private static ExecutorFactory instance = new ExecutorFactory();

	public static ExecutorFactory getInstance() {
		return instance;
	}

	public Executor createExecutor(ServiceDescripter serviceDescriptor, WebServiceContext webServiceContext) {
		ExecutorImpl executor = new ExecutorImpl(serviceDescriptor, webServiceContext);
		return executor;
	}
	
}
