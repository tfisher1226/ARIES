package org.aries.service.model;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.aries.nam.model.old.ServiceDescripter;


//@Name("serviceDescripterMap")
public class ServiceDescripterMap {

    private Map<String, ServiceDescripter> _Descripters;

    
	public ServiceDescripterMap() {
		setDefaults();
	}

	protected void setDefaults() {
		_Descripters = new ConcurrentHashMap<String, ServiceDescripter>();		
	}

    public Map<String, ServiceDescripter> getServiceDescripters() {
        return _Descripters;
    }

    public ServiceDescripter getServiceDescripter(String name) {
        return (ServiceDescripter) _Descripters.get(name);
    }

    public void addServiceDescripter(ServiceDescripter Descripter) {
    	_Descripters.put(Descripter.getServiceId(), Descripter);
    }

	public void add(ServiceDescripterMap Descripters) {
    	_Descripters.putAll(Descripters.getServiceDescripters());
	}
    
}
