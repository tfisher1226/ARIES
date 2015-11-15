package org.aries.bean;

import java.util.HashMap;
import java.util.Map;

import org.aries.transport.TransportType;


public class ProxyLocator {

	private Map<String, Map<String, Object>> map = new HashMap<String, Map<String, Object>>();
	
	private Object mutex = new Object();
	
	
	public ProxyLocator() {
		
	}
	
	public <T> T get(String name) {
		synchronized (mutex) {
			String key = name.toLowerCase() + "Proxy";
			@SuppressWarnings("unchecked") Map<String, T> list = (Map<String, T>) map.get(key);
			if (list == null)
				return null;
			T ejbProxy = list.get("EJB");
			T rmiProxy = list.get("RMI");
			T jmsProxy = list.get("JMS");
			T httpProxy = list.get("HTTP");
			if (ejbProxy != null)
				return ejbProxy;
			if (rmiProxy != null)
				return rmiProxy;
			if (jmsProxy != null)
				return jmsProxy;
			if (httpProxy != null)
				return httpProxy;
			return null;
		}
	}
	
	public <T> T get(String name, TransportType transportType) {
		String key = name.toLowerCase() + "Proxy";
		@SuppressWarnings("unchecked") Map<String, T> list = (Map<String, T>) map.get(key);
		if (list == null)
			return null;
		T proxy = null;
		if (transportType != null)
			proxy = list.get(transportType.name());
		else proxy = get(name);
		return proxy;
	}
	
	public <T> void add(String name, T object, TransportType transportType) {
		add(name, object, transportType.name());
	}
	
	public <T> void add(String name, T object, String transportType) {
		synchronized (mutex) {
			String key = name.toLowerCase() + "Proxy";
			Map<String, Object> list = (Map<String, Object>) map.get(key);
			if (list == null) {
				list = new HashMap<String, Object>();
				map.put(key, list);
			}
			list.put(transportType, object);
		}
	}
	
	public void clear() {
		synchronized (mutex) {
			map.clear();
		}
	}

//	public <T> T get(String key, String host, int port) {
//		return null;
//	}
	
}
