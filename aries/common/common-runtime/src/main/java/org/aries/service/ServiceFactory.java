package org.aries.service;

import org.aries.service.model.ServiceDescripterMap;


public interface ServiceFactory {

	public ClassLoader getClassLoader();
	
	public void setClassLoader(ClassLoader classLoader);

	public void initialize(ServiceDescripterMap descriptors) throws Exception;

	//public Object createServiceInstance(Service service) throws Exception;

	public Object createServiceInstance(String serviceId, String className) throws Exception;

	//public Object createServiceInstance(ServiceDescripter Descriptor) throws Exception;

	//public Action createAction(ActionDescriptor Descriptor) throws Exception;

}
