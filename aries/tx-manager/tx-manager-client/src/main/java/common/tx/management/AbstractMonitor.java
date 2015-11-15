package common.tx.management;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.management.JMX;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.Assert;
import org.aries.util.ExceptionUtil;
import org.aries.util.ReflectionUtil;

import common.jmx.MBeanUtil;
import common.jmx.bean.AbstractJmxClient;


public abstract class AbstractMonitor extends AbstractJmxClient {

	protected Log log = LogFactory.getLog(getClass());

	protected JMXConnector connection = null;
	

	public abstract String getMBeanName();

	protected abstract Class<?> getMBeanClass();

	
	@SuppressWarnings("unchecked")
	public <T> T invoke(String methodName) {
		return (T) invoke(getMBeanName(), methodName);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T invoke(String methodName, String parameterType, Object parameterValue) {
		return (T) invoke(getMBeanName(), methodName, parameterType, parameterValue);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T invoke(String methodName, String[] parameterTypes, Object[] parameterValues) {
		return (T) invoke(getMBeanName(), methodName, parameterTypes, parameterValues);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getObject(String methodName) {
		return (T) invoke(getMBeanName(), methodName);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getObject(String methodName, String parameterType, Object parameterValue) {
		return (T) invoke(getMBeanName(), methodName, parameterType, parameterValue);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getObject(String methodName, String[] parameterTypes, Object[] parameterValues) {
		return (T) invoke(getMBeanName(), methodName, parameterTypes, parameterValues);
	}
	
	@SuppressWarnings("unchecked")
	public <T> List<T> getList(String methodName) {
		return (List<T>) invoke(getMBeanName(), methodName);
	}
	
	@SuppressWarnings("unchecked")
	public <T> List<T> getList(String methodName, String parameterType, Object parameterValue) {
		return (List<T>) invoke(getMBeanName(), methodName, parameterType, parameterValue);
	}
	
	@SuppressWarnings("unchecked")
	public <T> List<T> getList(String methodName, String[] parameterTypes, Object[] parameterValues) {
		return (List<T>) invoke(getMBeanName(), methodName, parameterTypes, parameterValues);
	}
	
	@SuppressWarnings("unchecked")
	public <T> Set<T> getSet(String methodName) {
		return (Set<T>) invoke(getMBeanName(), methodName);
	}
	
	@SuppressWarnings("unchecked")
	public <T> Set<T> getSet(String methodName, String parameterType, Object parameterValue) {
		return (Set<T>) invoke(getMBeanName(), methodName, parameterType, parameterValue);
	}
	
	@SuppressWarnings("unchecked")
	public <T> Set<T> getSet(String methodName, String[] parameterTypes, Object[] parameterValues) {
		return (Set<T>) invoke(getMBeanName(), methodName, parameterTypes, parameterValues);
	}
	
	@SuppressWarnings("unchecked")
	public <K, T> Map<K, T> getMap(String methodName) {
		return (Map<K, T>) invoke(getMBeanName(), methodName);
	}
	
	@SuppressWarnings("unchecked")
	public <K, T> Map<K, T> getMap(String methodName, String parameterType, Object parameterValue) {
		return (Map<K, T>) invoke(getMBeanName(), methodName, parameterType, parameterValue);
	}
	
	@SuppressWarnings("unchecked")
	public <K, T> Map<K, T> getMap(String methodName, String[] parameterTypes, Object[] parameterValues) {
		return (Map<K, T>) invoke(getMBeanName(), methodName, parameterTypes, parameterValues);
	}

	
	protected Object invoke(String mBeanName, String methodName) {
		try {
			return MBeanUtil.invoke(getMBeanName(), methodName);
		} catch (Exception e) {
			throw ExceptionUtil.createRuntimeException(e);
		}
	}

	protected Object invoke(String mBeanName, String methodName, String parameterType, Object parameterValue) {
		try {
			return MBeanUtil.invoke(getMBeanName(), methodName, parameterType, parameterValue);
		} catch (Exception e) {
			throw ExceptionUtil.createRuntimeException(e);
		}
	}

	protected Object invoke(String mBeanName, String methodName, String[] parameterTypes, Object[] parameterValues) {
		try {
			return MBeanUtil.invoke(getMBeanName(), methodName, parameterTypes, parameterValues);
		} catch (Exception e) {
			throw ExceptionUtil.createRuntimeException(e);
		}
	}


	
	
//	public <T> T invoke(String methodName, String parameterType, Object parameterValue) {
//		String url = getURL();
//		
//		try {
//			if (connection == null)
//				connection = openConnection(url);
//			String mbeanName = getMBeanName();
//			Object mbeanProxy = createMBeanProxy(connection, mbeanName); 
//			log.info("Invoking \""+methodName+"\" method on MBean: " + mbeanName);
//			T value = invokeMBean(mbeanProxy, methodName);
//			return value;
//
//		} catch (Exception e) {
//			log.error("Error: ", e);
//			throw new RuntimeException(e);
//
//		} finally {
//			if (connection != null) {
//				closeConnection(connection, url);
//				connection = null;
//			}
//		}
//	}
//	
//	public <T> T invoke(Object mbeanProxy, String call, Object... parameters) throws Exception {
//		Method method = ReflectionUtil.findMethod(mbeanProxy.getClass(), call);
//		//Method method = findMethod(bean.getClass(), call, 0);
//		Assert.notNull(method, mbeanProxy.getClass().getName()+": method not found: "+call);
//		@SuppressWarnings("unchecked") T value = (T) method.invoke(mbeanProxy, parameters);
//		return value;
//	}
	

	
	public <T> T invokeMBean(String methodName, Object... parameters) {
		String url = getURL();
		
		try {
			if (connection == null)
				connection = openConnection(url);
			String mbeanName = getMBeanName();
			Object mbeanProxy = createMBeanProxy(connection, mbeanName); 
			log.info("Invoking \""+methodName+"\" method on MBean: " + mbeanName);
			T value = invokeMBean(mbeanProxy, methodName);
			return value;

		} catch (Exception e) {
			log.error("Error: ", e);
			throw new RuntimeException(e);

		} finally {
			if (connection != null) {
				closeConnection(connection, url);
				connection = null;
			}
		}
	}

	public <T> T invokeMBean(Object bean, String call, Object... parameters) throws Exception {
		Method method = ReflectionUtil.findMethod(bean.getClass(), call);
		//Method method = findMethod(bean.getClass(), call, 0);
		Assert.notNull(method, bean.getClass().getName()+": method not found: "+call);
		@SuppressWarnings("unchecked") T value = (T) method.invoke(bean, parameters);
		return value;
	}
	
    public Method findMethod(Class<?> objectType, String methodName, int parameterCount) {
        Method[] methods = objectType.getMethods();
        for (int i=0; i < methods.length; i++) {
            try {
                Method method = methods[i];
                if (method.getName().equals(methodName)) {
                	if (parameterCount == -1)
                		return method;
                	if (parameterCount == method.getParameterTypes().length)
                		return method;
                }
            } catch (SecurityException e) {
            	log.info("Error", e);
                break;
            }
        }
        return null;
    }
    
	// Construct proxy for the Agent
	protected <T> T createMBeanProxy(JMXConnector connection, String mbeanName) throws Exception {
		log.info("Creating MBeanProxy: "+mbeanName);
		ObjectName objectName = MBeanUtil.makeObjectName(mbeanName);
		MBeanServerConnection mbeanServer = connection.getMBeanServerConnection();
		@SuppressWarnings("unchecked") T mbeanProxy = (T) JMX.newMBeanProxy(mbeanServer, objectName, getMBeanClass(), true);
		log.info("Created MBeanProxy: "+mbeanName);
		return mbeanProxy;
	}

	
	// connecting to JMX
	public JMXConnector openConnection(String urlString) {
		try {
			log.info("Connecting with Remote JMX service: "+urlString);
			JMXServiceURL url = new JMXServiceURL(urlString);
			JMXConnector connection = JMXConnectorFactory.connect(url, null);
			log.info("Connected with Remote JMX service: "+urlString);
			return connection;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	// close and clean up connection
	public void closeConnection(JMXConnector connection, String url) {
		if (connection == null)
			return;
		try {
			log.info("Closing connection: "+url);
			connection.close();
			
		} catch (Throwable e) {
			String message = ExceptionUtils.getRootCauseMessage(e).toLowerCase();
			if (message != null && !message.contains("connection reset") && !message.contains("connection refused"))
				log.error("Error: ", e);
			try {
				if (connection.getMBeanServerConnection() != null)
					log.info("Error", e);
			} catch (Exception e2) {
				//ignore
			}
		} finally {
			log.info("Closed connection: "+url);
		}
	}
	
}
