package org.aries;

import javax.annotation.Resource;
import javax.xml.ws.WebServiceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.workflow.ExecutorImpl;


public abstract class AbstractService {

	Log log = LogFactory.getLog(getClass());

	@Resource
	WebServiceContext webServiceContext;
	
	ClassLoader classLoader;

	String serviceId;

	
	public AbstractService() {
		initialize(getServiceId());
	}

	public AbstractService(String serviceId) {
		initialize(serviceId);
	}

	protected void initialize(String serviceId) {
		//ServiceRepository serviceRepository = BeanContext.get("org.aries.serviceRepository");
		//serviceRepository.addServiceInstance(serviceId, this);
		this.serviceId = serviceId;
	}
	
	public String getServiceId() {
		return serviceId;
	}

	protected ClassLoader getClassLoader() {
		return classLoader;
	}

	public void setClassLoader(ClassLoader classLoader) {
		this.classLoader = classLoader;
	}

//	protected ExecutionManager<T> createWorkflowManager() {
//		ExecutionManager<T> executionManager = new ExecutionManager<T>(webServiceContext);
//		return executionManager;
//	}
	
//	protected ExecutionManager<T> createWorkflowManager(T state) {
//		ExecutionManager<T> executionManager = new ExecutionManager<T>(webServiceContext, state);
//		return executionManager;
//	}
	
	protected ExecutorImpl createWorkflowManager() {
		//ServiceRepository serviceRepository = BeanContext.get("org.aries.serviceRepository");
		//ServiceDescripter serviceDescripter = serviceRepository.getServiceDescripter(getServiceId());
		//ExecutorImpl workflowManager = new ExecutorImpl(serviceDescripter, webServiceContext);
		//return workflowManager;
		return null;
	}

}
