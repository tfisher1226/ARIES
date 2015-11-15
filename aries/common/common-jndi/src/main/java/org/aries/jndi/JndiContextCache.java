package org.aries.jndi;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.aries.Assert;


public class JndiContextCache {

    protected static JndiContextCache _instance;
    
	public static JndiContextCache getInstance() {
		return _instance;
	}

	private Map<String, JndiContext> _map;

	private Object _mutex;
	
	
	private JndiContextCache() {
		_instance = this;
		_map = new ConcurrentHashMap<String, JndiContext>();
		_mutex = new Object();
	}

	public JndiContext getContext(String connectionUrl) {
		synchronized (_mutex) {
			Assert.notNull(connectionUrl);
	    	return _map.get(connectionUrl);
		}
	}
	
	public void addContext(JndiContext jndiContext) {
		synchronized (_mutex) {
			Assert.notNull(jndiContext);
	    	_map.put(jndiContext.getConnectionUrl(), jndiContext);
		}
	}
	
	public void removeContext(JndiContext jndiContext) {
		synchronized (_mutex) {
			Assert.notNull(jndiContext);
	    	_map.put(jndiContext.getConnectionUrl(), jndiContext);
		}
	}
	
	public JndiContext createContext(String url, String factory) {
    	JndiProxy jndiContext = new JndiProxy();
    	jndiContext.setConnectionUrl(url);
    	jndiContext.setContextFactory(factory);
    	return jndiContext;
    }
	
}
