package org.aries.ejb;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class EJBEndpointMap {

	public static final EJBEndpointMap INSTANCE = new EJBEndpointMap();

	private Map<String, EJBEndpointContext> map = new ConcurrentHashMap<String, EJBEndpointContext>();


	public EJBEndpointContext getEndpointContext(String serviceId) {
		return map.get(serviceId);
	}
	
	public void addEndpointContext(String serviceId, EJBEndpointContext endpointContext) {
		map.put(serviceId, endpointContext);
	}

}
