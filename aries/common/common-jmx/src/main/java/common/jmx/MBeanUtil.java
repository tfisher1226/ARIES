/*
 * MBeanUtil.java
 * Created on Jun 2, 2005
 * 
 * 
 */
package common.jmx;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import javax.management.JMException;
import javax.management.JMRuntimeException;
import javax.management.MBeanInfo;
import javax.management.MBeanOperationInfo;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;
import javax.management.QueryExp;
import javax.management.ReflectionException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.util.ExceptionUtil;


/*
 * By default this class is meant to be used from within the application server.
 * 
 */
public class MBeanUtil {

	private static Log log = LogFactory.getLog(MBeanUtil.class);

	//public static MBeanServer INSTANCE = MBeanServerFactory.createMBeanServer();

	public static MBeanServer INSTANCE = ManagementFactory.getPlatformMBeanServer();

	private static List<ObjectName> registeredMBeans = new ArrayList<ObjectName>();

	private static Object mutex = new Object();


	/*
	 * MBeanServer
	 */

	public static MBeanServer getMBeanServer() {
		return INSTANCE;
	}

	public static void setMBeanServer(MBeanServer mbeanServer) {
		synchronized (mutex) {
			registeredMBeans.clear();
			INSTANCE = mbeanServer;
		}
	}

	public static ObjectName makeObjectName(String mbeanName) {
		try {
			ObjectName objectName = new ObjectName(mbeanName);
			return objectName;
		} catch (MalformedObjectNameException e) {
			log.warn("Could not resolve name: "+mbeanName);
			throw new RuntimeException(e);
		} catch (Exception e) {
			log.error("Unexpected error resolving name: "+mbeanName);
			throw new RuntimeException(e);
		}
	}

	/*
	 * Registration
	 */

	public static void registerMBean(Object object, String mbeanName) {
		synchronized (mutex) {
			ObjectName objectName = makeObjectName(mbeanName);
			registerMBean(object, objectName);
			registeredMBeans.add(objectName);
		}
	}

	public static void registerMBean(Object object, ObjectName objectName) {
		try {
			if (existsMBean(objectName))
				return;
			getMBeanServer().registerMBean(object, objectName);
			//Set<ObjectInstance> queryMBeans = getMBeanServer().queryMBeans(objectName, null);
			log.debug("JMX MBean registered: "+objectName);

		} catch (InstanceAlreadyExistsException e) {
			log.warn("Instance already exists: "+objectName);
			throw new RuntimeException(e);
		} catch (NotCompliantMBeanException e) {
			log.error("Not a compliant MBean: "+objectName);
			throw new RuntimeException(e);
		} catch (MBeanRegistrationException e) {
			log.error("Unexpected error registering MBean: "+objectName);
			throw new RuntimeException(e);
		} catch (Exception e) {
			log.error("Unexpected error registering MBean: "+objectName);
			throw new RuntimeException(e);
		}
	}

	/*
	 * Unregistration
	 */

	public static void unregisterMBeans() {
		synchronized (mutex) {
			List<ObjectName> exceptions = null;
			Iterator<ObjectName> iterator = registeredMBeans.iterator();
			while (iterator.hasNext()) {
				ObjectName objectName = iterator.next();
				while (iterator.hasNext()) {
					objectName = iterator.next();
					try {
						unregisterMBean(objectName);
					} catch (Exception e) {
						if (exceptions == null)
							exceptions = new ArrayList<ObjectName>();
						exceptions.add(objectName);
					}
				}
			}
			if (exceptions != null) {
				StringBuffer message = new StringBuffer();
				Iterator<ObjectName> exceptionIterator = exceptions.iterator();
				while (exceptionIterator.hasNext()) {
					ObjectName objectName = exceptionIterator.next();
					message.append("Cannot unregister "+objectName);
					message.append(objectName);
				}
				RuntimeException exception = new RuntimeException(message.toString());
				throw exception;
			}
		}
	}

	public static void unregisterMBean(String mbeanName) {
		ObjectName objectName = makeObjectName(mbeanName);
		unregisterMBean(objectName);
	}

	public static void unregisterMBean(ObjectName objectName) {
		try {
			getMBeanServer().unregisterMBean(objectName);
			log.debug("JMX MBean unregistered: "+objectName);
		} catch (InstanceNotFoundException e) {
			log.debug("JMX MBean not found: "+objectName);
		} catch (MBeanRegistrationException e) {
			log.error("Unexpected error unregistering MBean: "+objectName);
			throw new RuntimeException(e);
		}
	}


	/*
	 * Query methods
	 */

	public static boolean existsMBean(ObjectName objectName) throws Exception {
		Set<ObjectName> objectNames = queryObjectNames(objectName, null);
		boolean result = objectNames != null && objectNames.size() > 0;
		return result;
	}

	public static Set<ObjectName> queryObjectNames() throws Exception {
		return queryObjectNames(null);
	}

	public static Set<ObjectName> queryObjectNames(String objectMask) throws Exception {
		ObjectName criteria = null;
		if (objectMask != null)
			criteria = MBeanUtil.makeObjectName(objectMask);
		return queryObjectNames(criteria, null);
	}

	public static Set<ObjectName> queryObjectNames(ObjectName criteria, QueryExp queryExp) throws Exception {
		return getMBeanServer().queryNames(criteria, null);
	}

	public static Set<ObjectName> getObjectNames(MBeanServerConnection connection, String objectMask) {
		try {
			ObjectName criteria = MBeanUtil.makeObjectName(objectMask);
			Set<ObjectName> objectNames = connection.queryNames(criteria, null);
			return objectNames;
		} catch (IOException e) {
			log.warn("Query failed for: "+objectMask, e);
			return new HashSet<ObjectName>();
		}
	}


	/*
	 * Attribute methods
	 */

	public static <T> T getAttribute(String mbeanName, String attribute) throws Exception {
		T object = getAttribute(makeObjectName(mbeanName), attribute);
		return object;
	}

	public static <T> T getAttribute(ObjectName name, String attribute) throws Exception {
		T object = getAttribute(getMBeanServer(), name, attribute);
		return object;
	}

	@SuppressWarnings("unchecked")
	public static <T> T getAttribute(MBeanServerConnection connection, ObjectName name, String attribute) throws Exception {
		log.debug("getAttribute: invoked: "+name+"."+attribute+"()");
		Throwable exception = null;

		try {
			log.debug("Invoking " + name.getCanonicalName() + " attribute=" + attribute);
			MBeanInfo mBeanInfo = connection.getMBeanInfo(name);
			//Set<ObjectName> objectNames = connection.queryNames(null, null);
			T result = (T) connection.getAttribute(name, attribute);
			log.debug("getAttribute: returns: "+result);
			return  result;

		} catch (JMException e) {
			exception = e;
		} catch (JMRuntimeException e) {
			exception = e;
		} catch (Throwable e) {
			exception = e;
		}

		Exception cause = ExceptionUtil.getRootCause(exception);
		log.debug("getAttribute: throws: "+cause);
		log.error("Error: ", cause);
		throw cause;
	}

	
	public static MBeanOperationInfo[] getOperations(String mbeanName) throws Exception {
		return getOperations(makeObjectName(mbeanName));
	}
	
	public static MBeanOperationInfo[] getOperations(ObjectName objectName) throws Exception {
		return getOperations(getMBeanServer(), objectName);
	}

	public static MBeanOperationInfo[] getOperations(MBeanServerConnection connection, ObjectName name) throws Exception {
		log.debug("getOperations: invoked: "+name);
		Throwable exception = null;

		try {
			MBeanInfo mBeanInfo = connection.getMBeanInfo(name);
			MBeanOperationInfo[] operations = mBeanInfo.getOperations();
			log.debug("getOperations: returns: "+operations);
			return  operations;

		} catch (JMException e) {
			exception = e;
		} catch (JMRuntimeException e) {
			exception = e;
		} catch (Throwable e) {
			exception = e;
		}

		Exception cause = ExceptionUtil.getRootCause(exception);
		log.debug("getOperations: throws: "+cause);
		log.error("Error: ", cause);
		throw cause;
	}
	

	/*
	 * Invocation methods
	 */

	public static <T> T invoke(String mbeanName, String methodName) throws Exception {
		T object = invoke(new ObjectName(mbeanName), methodName);
		return object;
	}

	public static <T, V> T invoke(String mbeanName, String methodName, V value) throws Exception {
		T object = invoke(new ObjectName(mbeanName), methodName, value.getClass().getCanonicalName(), value);
		return object;
	}

	public static <T, V> T invoke(String mbeanName, String methodName, String type, V value) throws Exception {
		T object = invoke(new ObjectName(mbeanName), methodName, type, value);
		return object;
	}

	public static <T, V> T invoke(ObjectName objectName, String methodName, String type, V value) throws Exception {
		T object = invoke(objectName, methodName, new String[] { type }, new Object[] { value });
		return object;
	}

	public static <T> T invoke(ObjectName objectName, String methodName) throws Exception {
		T object = invoke(getMBeanServer(), objectName, methodName, new String[] {}, new Object[] {});
		return object;
	}

	public static <T> T invoke(String mbeanName, String methodName, String[] signature, Object[] parameters) throws Exception {
		T object = invoke(getMBeanServer(), new ObjectName(mbeanName), methodName, signature, parameters);
		return object;
	}

	public static <T> T invoke(ObjectName objectName, String methodName, String[] signature, Object[] parameters) throws Exception {
		T object = invoke(getMBeanServer(), objectName, methodName, signature, parameters);
		return object;
	}

	@SuppressWarnings("unchecked")
	public static <T> T invoke(MBeanServerConnection connection, ObjectName name, String method, String[] signature, Object[] parameters) throws Exception {
		log.debug("invoke: invoking: "+name+"."+method+"()");
		Throwable exception = null;

		try {
			//Set<ObjectName> queryNames = queryNames();
			if (parameters != null)
				log.debug("invoke: args=" + Arrays.asList(parameters));
			//MBeanInfo mBeanInfo = connection.getMBeanInfo(name);
			//Set<ObjectName> objectNames = connection.queryNames(null, null);
			T result = (T) connection.invoke(name, method, parameters, signature);
			log.debug("invoke: returns: "+result);
			return  result;

		} catch (ReflectionException e) {
			Exception targetException = e.getTargetException();
			if (targetException instanceof NoSuchMethodException) {
				String attribute = method.substring(3);
				try {
					T result = (T) connection.getAttribute(name, attribute);
					log.debug("invoke: returns: "+result);
					return  result;
				} catch (JMException e2) {
					exception = e2;
				} catch (JMRuntimeException e2) {
					exception = e2;
				} catch (Throwable e2) {
					exception = e2;
				}
			} else
				exception = e;
		} catch (JMException e) {
			exception = e;
		} catch (JMRuntimeException e) {
			exception = e;
		} catch (IOException e) {
			if (e.toString().contains("WAITING"))
				//TODO throw a timout exception
				System.out.println("TIMEOUT");
			exception = e;
		} catch (Throwable e) {
			exception = e;
		}

		Exception cause = ExceptionUtil.getRootCause(exception);
		log.error("invoke: throws: "+cause);
		throw cause;
	}

}
