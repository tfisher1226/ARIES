package org.aries.service;

import java.util.Collection;


public interface ServiceRepository {

	public Collection<String> getServiceIds();

	public Object getServiceDescripter(String serviceId);

	public Collection<Object> getServiceDescripters();

	public Collection<Object> getServiceInstances();

	public Object getServiceInstance(String serviceId);

	public void addServiceInstance(String serviceId, Object serviceDescripter, Object serviceInstance);

	public void removeServiceInstance(String serviceId);
	
	public void close();

}
