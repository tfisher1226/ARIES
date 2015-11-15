package org.aries.runtime;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.transaction.UserTransaction;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.runtime.exception.BeanNotFoundException;
import org.aries.util.ExceptionUtil;
import org.aries.util.ObjectUtil;
import org.jboss.seam.Component;
import org.jboss.seam.ScopeType;
import org.jboss.seam.contexts.Lifecycle;


public class BeanContext {

	private static Log log = LogFactory.getLog(BeanContext.class);

	//private static Map<String, Object> beans = new ConcurrentHashMap<String, Object>();
	
	//TODO use ThreadLocal maps of beans to represent different contexts (when Seam is not being used)
	private static BeanMap beanMap = new BeanMap();

	private static Object mutex = new Object();
	

	private static boolean isSeamEnabled() {
		try {
			return Lifecycle.isApplicationInitialized();
		} catch (Throwable e) {
			Exception cause = ExceptionUtil.getRootCause(e);
			if (e instanceof NoClassDefFoundError == false)
				log.warn("Seam is not available: "+cause.getMessage());
			return false;
		}
	}

	public static void initialize(Map<String, Object> beans) {
		Iterator<String> iterator = beans.keySet().iterator();
		while (iterator.hasNext()) {
			String key = (String) iterator.next();
			Object value = beans.get(key);
			set(key, value);
		}
	}

	//TODO This should only remove Session scoped beans 
	//TODO These other beans should be made Application scope 
	public static void clear() {
		synchronized (mutex) {
			if (beanMap != null) {
				//beanMap.clear();
				//TODO filter these here for now - fix this soon
				Collection<String> keysToRemove = new ArrayList<String>();
				Set<String> keySet = beanMap.getBeans().keySet();
				Iterator<String> iterator = keySet.iterator();
				while (iterator.hasNext()) {
					String key = iterator.next();
					if (!key.endsWith(".initializer") && !key.endsWith(".notificationDispatcher")) {
						keysToRemove.add(key);
					}
				}
				iterator = keysToRemove.iterator();
				while (iterator.hasNext()) {
					String key = iterator.next();
					beanMap.removeBean(key);
				}
			}			
		}
	}
	
    public static BeanManager getBeanManager() {
        try {
        	//CDI.current().getBeanManager();
            InitialContext initialContext = new InitialContext();
            return (BeanManager) initialContext.lookup("java:comp/BeanManager");
        } catch (NamingException e) {
            log.warn("Cannot access BeanManager through JNDI");
            return null;
        }
    }

    public static void setBeanManager(BeanManager beanManager) {
        try {
        	//CDI.current().getBeanManager();
            InitialContext initialContext = new InitialContext();
            BeanManager currentBeanManager = (BeanManager) initialContext.lookup("java:comp/BeanManager");
            if (currentBeanManager == null)
            	initialContext.rebind("java:comp/BeanManager", beanManager);
        } catch (NamingException e) {
            log.warn("Cannot add BeanManager to JNDI");
        }
    }
    
    @SuppressWarnings("unchecked") 
	public static <T> T getBeanByName(String name) {
        BeanManager beanManager = getBeanManager();
        if (beanManager != null) {
            Set<Bean<?>> beans = beanManager.getBeans(name);
            if (beans != null && beans.size() > 0) {
				Bean<?> bean = beans.iterator().next();
	            CreationalContext<?> creationalContext = beanManager.createCreationalContext(bean); // could be inlined below
	            T instance = (T) beanManager.getReference(bean, bean.getBeanClass(), creationalContext); // could be inlined with return
	            return instance;
            }
        }
		T instance = beanMap.getBean(name);
        return instance;
    }
    
	@SuppressWarnings("unchecked") 
    public static <T> T getBeanByType(Class<T> classObject) {
		 BeanManager beanManager = getBeanManager();
        if (beanManager != null) {
            Set<Bean<?>> beans = beanManager.getBeans(classObject);
            if (beans != null && beans.size() > 0) {
				Bean<?> bean = beans.iterator().next();
		        CreationalContext<?> creationalContext = beanManager.createCreationalContext(bean);
		        T instance = (T) beanManager.getReference(bean, classObject, creationalContext); // this could be inlined, but intentionally left this way
		        return instance;
	        }
        }
        return null;
    }

	public static <T> T getFromApplication(String name) throws BeanNotFoundException {
		T bean = getBeanByName(name);
		if (bean == null)
			bean = get(name, ScopeType.APPLICATION);
		return bean;
	}
	
	public static <T> T getFromSession(String name) throws BeanNotFoundException {
		T bean = getBeanByName(name);
		if (bean == null)
			bean = get(name, ScopeType.SESSION);
		return bean;
	}
	
	public static <T> T getFromConversation(String name) throws BeanNotFoundException {
		T bean = getBeanByName(name);
		if (bean == null)
			bean = get(name, ScopeType.CONVERSATION);
		return bean;
	}
	
	public static <T> T getFromEvent(String name) throws BeanNotFoundException {
		T bean = getBeanByName(name);
		if (bean == null)
			bean = get(name, ScopeType.EVENT);
		return bean;
	}
	
	public static <T> T get(String name) throws BeanNotFoundException {
		T bean = getBeanByName(name);
		if (bean == null)
			bean = get(name, (ScopeType) null);
		return bean;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T get(String name, ScopeType scope) throws BeanNotFoundException {
		synchronized (mutex) {
			T instance = null;
			try {
				if (isSeamEnabled()) {
					if (scope != null)
						instance = (T) Component.getInstance(name, scope);
					else instance = (T) Component.getInstance(name);
				}
			} catch (IllegalStateException e) {
				//ignore 
			}
			if (instance == null)
				instance = beanMap.getBean(name);
			return instance;
		}
	}
	
	//TODO use a more appropriate key combination?
	public static <T> T get(String name, String correlationId) throws BeanNotFoundException {
		return get(name+correlationId);
	}

	public static <T> T get(Class<T> classObject, String correlationId) {
		String key = classObject.getCanonicalName();
		T instance = get(key, correlationId);
		if (instance == null) {
			try {
				instance = ObjectUtil.newInstance(classObject, correlationId);
				BeanContext.set(key, correlationId, instance);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		return instance;
	}

	/*
	 * If bean already exists for the specified name,
	 * then we overwrite that bean with the new bean. 
	 */
	public static void set(String name, Object bean) {
//		if (name.equals("aries.org.nam.nam-view.projects"))
//			System.out.println();
		synchronized (mutex) {
			//if (beanMap.getBean(name) == null)
				beanMap.addBean(name, bean);
		}
	}
	
	//TODO use a more appropriate key combination?
	public static void set(String name, String correlationId, Object bean) {
		synchronized (mutex) {
			beanMap.addBean(name+correlationId, bean);
		}
	}
	
	
//	public static <T> T addToApplication(String name, T object) throws BeanNotFoundException {
//		return add(name, object, ScopeType.APPLICATION);
//	}
//	
//	public static <T> T addToSession(String name, T object) throws BeanNotFoundException {
//		return add(name, object, ScopeType.SESSION);
//	}
//	
//	public static <T> T addToConversation(String name, T object) throws BeanNotFoundException {
//		return add(name, object, ScopeType.CONVERSATION);
//	}
//	
//	public static <T> T addToEvent(String name, T object) throws BeanNotFoundException {
//		return add(name, object, ScopeType.EVENT);
//	}
//	
//	public static <T> T add(String name, T object) throws BeanNotFoundException {
//		return add(name, object, (ScopeType) null);
//	}
//	
//	@SuppressWarnings("unchecked")
//	public static <T> T add(String name, T instance, ScopeType scope) throws BeanNotFoundException {
//		synchronized (mutex) {
//			try {
//				if (isSeamEnabled()) {
//					if (scope != null)
//						Component.getInstanceFromFactory(name)getInstance(name, scope);
//					else instance = (T) Component.getInstance(name);
//				}
//			} catch (IllegalStateException e) {
//				//ignore 
//			}
//			if (instance == null)
//				beanMap.addBean(name, object);
//			return instance;
//		}
//	}

	
	public static void removeFromSession(String instanceId) {
		//Contexts.getSessionContext().remove(instanceId);
		
	}

	
	public static UserTransaction getUserTransactionFromJNDI() {
		InitialContext context;
		Object result;
		try {
			context = new InitialContext();
			result = context.lookup("java:comp/UserTransaction"); // lookup in local context
		} catch (NamingException ex) {
			throw new RuntimeException("UserTransaction could not be found in JNDI", ex);
		}
		return (UserTransaction) result;
	}
	 
	public static <T> T getFromJNDI(Class<T> instanceClass, String instanceId) {
		T instance = getBeanByName(instanceId);
		if (instance == null)
			instance = getBeanByType(instanceClass);
		return instance; 
	}
	
	//public static <T> T getFromJNDI(Class<T> instanceClass, JndiName jndiName) {
	//	return BeanLocator.lookup(instanceClass, jndiName.toString());
	//}
	
	//this is just here for reference...
	//public static JndiName getJndiName(String instanceId) {
	//	JndiName jndiName = new JndiName();
	//	jndiName.setApplicationName("bookshop2-app");
	//	jndiName.setModuleName("admin-service-0.0.1-SNAPSHOT");
	//	jndiName.setBeanName(instanceId);
	//	return jndiName;
	//}


	public static void begin(ServletContext servletContext) {
		//SEAM ServletLifecycle.beginApplication(servletContext);
		//seamContext = Lifecycle.getApplication();
		//SEAM Lifecycle.beginCall();
	}

	public static void begin(Map<String, Object> application) {
		//SEAM Lifecycle.beginApplication(application);
		//SEAM ifecycle.beginCall();
	}

	public static void begin() {
//		if (seamContext != null)
//			begin(seamContext);
	}

	public static void end(ServletContext servletContext) {
		//SEAM ServletLifecycle.endApplication(servletContext);
	}

	public static void end(Map<String, Object> application) {
		//SEAM Context context = Contexts.getSessionContext();
		//SEAM if (context != null) {
		//SEAM 	Lifecycle.endCall();
		//SEAM }
	}

	public static void end() {
		//SEAM Lifecycle.endCall();
	}
	
	public static void endApplication() {
		//SEAM Lifecycle.endCall();
		//SEAM Lifecycle.endApplication();
	}

	public static void endApplication(Map<String, Object> application) {
		//SEAM Context context = Contexts.getSessionContext();
		//SEAM if (context != null) {
		//SEAM	Lifecycle.endCall();
		//SEAM	Lifecycle.endApplication();
		//SEAM }
	}
	
	public static void printValues(Map<String, Object> map) {
		Iterator<String> iterator = map.keySet().iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			Object value = map.get(key);
			log.info("Name: "+key+", "+value);
		}
	}

	public static void printNames(String[] names) {
		for (int i=0; i < names.length; i++)
			log.info("Name: "+names[i]);
	}

}
