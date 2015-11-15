package org.aries.jndi;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class JndiProxy implements JndiContext {

    private static Log log = LogFactory.getLog(JndiProxy.class);
    
    private InitialContext initialContext;

    private Map<String, String> properties;

    private boolean refreshNeeded;

    private boolean cacheImportedObjects;

    private boolean cacheExportedObjects;

    /** Cache of objects imported by this node. */ 
    private Map<String, Object> importedObjectCache;

    /** Cache of objects exported by this node. */ 
	private Map<String, Object> exportedObjectCache;

	private Object contextLock;

	private Object importLock;

	private Object exportLock;

	
	public JndiProxy() {
		initialize();
	}
	
	public JndiProxy(Map<String, String> properties) {
    	this.properties = properties;
		initialize();
    }

    protected void initialize() {
		properties = new HashMap<String, String>();
		cacheImportedObjects = true;
		cacheExportedObjects = true;
		importedObjectCache = new HashMap<String, Object>();
		exportedObjectCache = new HashMap<String, Object>();
		contextLock = new Object();
		importLock = new Object();
		exportLock = new Object();
	}

    public String getConnectionUrl() {
        return properties.get(Context.PROVIDER_URL);
    }

    public void setConnectionUrl(String connectionUrl) {
    	properties.put(Context.PROVIDER_URL, connectionUrl);
    }

    public String getContextFactory() {
        return properties.get(Context.INITIAL_CONTEXT_FACTORY);
    }

    public void setContextFactory(String contextFactory) {
    	properties.put(Context.INITIAL_CONTEXT_FACTORY, contextFactory);
    }

	public String getUserName() {
        return properties.get(Context.SECURITY_PRINCIPAL);
    }

    public void setUserName(String value) {
    	properties.put(Context.SECURITY_PRINCIPAL, value);
    }
    
    public String getPassword() {
        return properties.get(Context.SECURITY_CREDENTIALS);
    }

    public void setPassword(String value) {
    	properties.put(Context.SECURITY_CREDENTIALS, value);
    }
    
    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> value) {
    	properties.putAll(value);
    }

    public boolean isServiceChangeNeeded() {
        return refreshNeeded;
    }

    public void setServiceChangeNeeded(boolean value) {
        refreshNeeded = value;
        importedObjectCache.clear();
    }

    public boolean getCacheImportedObjects() {
		return cacheImportedObjects;
	}

	public void setCacheImportedObjects(boolean value) {
		cacheImportedObjects = value;
	}

	public boolean getCacheExportedObjects() {
		return cacheExportedObjects;
	}

	public void setCacheExportedObjects(boolean value) {
		cacheExportedObjects = value;
	}

	
	@SuppressWarnings("unchecked")
	public <T> T lookup(Class<T> classObject, String name) {
        try {
            final Object object = getInitialContext().lookup(name);
            if (classObject.isAssignableFrom(object.getClass())) {
                return (T) object;
            } else {
                throw new RuntimeException(String.format(
                        "Class found: %s cannot be assigned to type: %s",
                        object.getClass(), classObject));
            }
 
        } catch (NamingException e) {
            String message = String.format("Unable to find object for %s", classObject.getName());
			throw new RuntimeException(message, e);
        }
	}
	
	public Object lookupObject(String name) throws NamingException {
		synchronized (importLock) {
			Object object = null;
			if (cacheImportedObjects)
				object = importedObjectCache.get(name);
	        if (object == null)
	            object = lookupObjectFromService(name);
	        //keep object cached even it became null at server
	        if (cacheImportedObjects && object != null)
	        	importedObjectCache.put(name, object);
	        return object;
	    }
	}
	
    protected Object lookupObjectFromService(String name) throws NamingException {
        try {
            log.info("Looking up object: "+name);
    		//log.info(ExceptionUtil.getTrace(new Exception()));
            Object object = getInitialContext().lookup(name);
            return object;
        } catch (NamingException e) {
            log.warn("Failed to lookup object: "+name+", "+e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Error"+name, e);
            throw new RuntimeException(e);
        }
    }


    // Creates the JNDI initial context.
    protected Context getInitialContext() throws NamingException {
    	synchronized (contextLock) {
	        if (initialContext == null || refreshNeeded) {
	        	if (properties.size() == 0) {
		            initialContext = new InitialContext();
	        	} else {
		            Hashtable<String, String> contextProperties = new Hashtable<String, String>(properties);
		            //contextProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.naming");
		            //contextProperties.put(Context.SECURITY_PRINCIPAL, "consume");
		            //contextProperties.put(Context.SECURITY_CREDENTIALS, "guest");
//		            if (externalProperties != null && externalProperties.size() > 0) {
//		            	Iterator<Object> iterator = externalProperties.keySet().iterator();
//		            	while (iterator.hasNext()) {
//		                	Object key = iterator.next();
//		    				Object value = externalProperties.get(key);
//		                	contextProperties.put(key, value);
//						}
//		            }
		            //now get the InitialContext
		            //initialContext = new InitialContext();
		            initialContext = new InitialContext(contextProperties);
	        	}
	        	reregisterExportedObjects();
	        }
	        return initialContext;
	    }
    }
    
	protected void setInitialContext(InitialContext initialContext) {
    	synchronized (contextLock) {
    		this.initialContext = initialContext;
    	}
    }
    

    private void reregisterExportedObjects() throws NamingException {
		synchronized (exportLock) {
			Iterator<String> iterator = exportedObjectCache.keySet().iterator();
			while (iterator.hasNext()) {
				String key = iterator.next();
				Object value = exportedObjectCache.get(key);
				initialContext.rebind(key, value);
				log.info("Re-registered objects with JNDI");
			}
		}
    }
    
	public void registerObject(String key, Object value) throws NamingException {
		if (key == null || key.length() == 0)
			throw new NamingException("key must be set");
		try {
			Context context = getInitialContext();
			context.rebind(key, value);
			synchronized (exportLock) {
		    	exportedObjectCache.put(key, value);
			}
			log.info("Registered object with JNDI: "+key);
		} catch (NamingException e) {
			log.error(e);
			throw e;
		}
	}
	
	public void unregisterObject(String key) throws NamingException {
    	synchronized (exportLock) {
	    	Context context = getInitialContext();
			context.removeFromEnvironment(key);
	    	exportedObjectCache.remove(key);
    	}
	}

	public void dispose() {
		//TODO: implement this
	}
	
	@Override
	public String toString() {
		return initialContext.toString();
	}
	
}
