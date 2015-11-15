package org.aries.service.model;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


//@Name("serviceMap")
public class ServiceMap {

	private Map<String, Object> services;
	
	
	public ServiceMap() {
		setDefaults();
	}

	protected void setDefaults() {
		services = new ConcurrentHashMap<String, Object>();		
	}

	public Object getService(String serviceId) {
		return services.get(serviceId);
	}

	public void addService(String serviceId, Object service) {
		services.put(serviceId, service);
	}
	
	public void addServices(ServiceMap services) {
		this.services.putAll(services.getServices());
	}

	public void removeService(String serviceId) {
		services.remove(serviceId);
	}

	public Map<String, Object> getServices() {
		return services;
	}

	public void setServices(Map<String, Object> services) {
		this.services = services;
	}

}
