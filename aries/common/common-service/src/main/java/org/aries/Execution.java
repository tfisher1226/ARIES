package org.aries;

import javax.xml.ws.WebServiceContext;

import org.aries.nam.model.old.ServiceDescripter;
import org.aries.runtime.BeanContext;
import org.aries.service.ServiceRepository;
import org.aries.workflow.ExecutionContext;
import org.aries.workflow.Executor;
import org.aries.workflow.ExecutorFactory;


public class Execution {

	private static Cache cache = new Cache();


	public static synchronized ExecutionContext getContext() {
		return cache.getContext();
	}

	protected static class Cache extends ThreadLocal<ExecutionContext> {
		public ExecutionContext initialValue() {
			return new ExecutionContext();
		}

		public ExecutionContext getContext() { 
			return super.get();
		}
	}

	public static void initialize(ServiceDescripter serviceDescripter, WebServiceContext webServiceContext) {
		getContext().initializeContext(serviceDescripter, webServiceContext);
	}

	public static Executor getExecutor(String serviceId) {
		//ServiceRepository serviceRepository = BeanContext.get("org.aries.serviceRepository");
		//ServiceDescripter serviceDescripter = serviceRepository.getServiceDescripter(serviceId);
		//Executor executor = getExecutor(serviceDescripter);
		//return executor;
		return null;
	}

	public static Executor getExecutor(String serviceId, WebServiceContext webServiceContext) {
		ServiceRepository serviceRepository = BeanContext.get("org.aries.serviceRepository");
		//ServiceDescripter serviceDescripter = serviceRepository.getServiceDescripter(serviceId);
		//Executor executor = getExecutor(serviceDescripter, webServiceContext);
		//return executor;
		return null;
	}

	public static Executor getExecutor(ServiceDescripter serviceDescripter) {
		getContext().initializeContext(serviceDescripter, null);
		Executor executor = getExecutor();
		return executor;
	}

	public static Executor getExecutor(ServiceDescripter serviceDescripter, WebServiceContext webServiceContext) {
		getContext().initializeContext(serviceDescripter, webServiceContext);
		Executor executor = getExecutor();
		return executor;
	}
	
	public static Executor getExecutor() {
		ExecutorFactory factory = ExecutorFactory.getInstance();
		ServiceDescripter serviceDescripter = getContext().getServiceDescripter();
		WebServiceContext webServiceContext = getContext().getWebServiceContext();
		Executor executor = factory.createExecutor(serviceDescripter, webServiceContext);
		return executor;
	}

}
