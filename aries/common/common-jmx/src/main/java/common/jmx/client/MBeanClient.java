package common.jmx.client;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanInfo;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.Notification;
import javax.management.NotificationListener;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.Assert;
import org.aries.util.ExceptionUtil;

import common.jmx.MBeanUtil;


public class MBeanClient {

	protected final Log log = LogFactory.getLog(getClass());

	private String username;
	
	private String password;
	
	private String bindAddress;

	private String managementPort;

	private MBeanServerConnection connection;

	private AtomicBoolean initialized = new AtomicBoolean();
	
	private Object mutex = new Object();
	
	private boolean isLocal;
	
	
	public MBeanClient() {
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getBindAddress() {
		return bindAddress;
	}

	public void setBindAddress(String bindAddress) {
		this.bindAddress = bindAddress;
	}

	public String getManagementPort() {
		return managementPort;
	}

	public void setManagementPort(String managementPort) {
		this.managementPort = managementPort;
	}
	
	public MBeanServerConnection getMBeanServerConnection() {
		synchronized (mutex) {
			return connection;
		}
	}
	
	public void setMBeanServerConnection(MBeanServerConnection connection) {
		synchronized (mutex) {
			this.connection = connection;
			this.initialized.set(true);
		}
	}
	
	public boolean isLocal() {
		return isLocal;
	}

	public void setLocal(boolean isLocal) {
		this.isLocal = isLocal;
	}


	
//	protected String getContextFactory() {
//		return "org.jnp.interfaces.NamingContextFactory";
//	} 
//
//	protected String getProviderURL() {
//		return "jnp://"+host+":"+port;
//	}

	
//	private MBeanRegistry _mbeanRegistry;
//	
//	public MBeanRegistry getMBeanRegistry() throws NamingException {
//		//synchronized (_lock) {
//			if (_mbeanRegistry == null) {
//				try {
//					ObjectName objectName = new ObjectName(ServerConstants.MBEAN_REGISTRY);
//					_mbeanRegistry = (MBeanRegistry) getMBeanServer().queryMBeans(objectName, null);
//				} catch (MalformedObjectNameException e) {
//					e.printStackTrace();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//			return _mbeanRegistry;
//		//}
//	}
	
	public boolean validateConfiguration() {
		try {
			Assert.notEmpty(bindAddress);
			Assert.notEmpty(managementPort);
			Assert.isTrue(new Integer(managementPort) > 0);
			return true;
			
		} catch (Throwable e) {
			return false;
		}
	}
	
	public MBeanServerConnection getMBeanServer() throws Exception {
		synchronized (mutex) {
			if (!initialized.get()) {
				if (!validateConfiguration() || isLocal) {
					//org.jboss.remotingjmx.RemotingConnectorProvide
					this.connection = ManagementFactory.getPlatformMBeanServer();
					initialized.set(true);
					
//					MBeanServer mbeanServer2 = MBeanServerFactory.createMBeanServer();
//					Set<ObjectName> queryNames = connection.queryNames(null, null);
//					Set<ObjectName> queryNames2 = mbeanServer2.queryNames(null, null);
//
//					Map<String,String[]> environment = new HashMap<String, String[]>();
//					String[] credentials = new String[] { "testuser", "password" };
//					environment.put(JMXConnector.CREDENTIALS, credentials);
//	
//					JMXServiceURL url = new JMXServiceURL("service:jmx:remoting-jmx://172.18.0.1:9999");
//					JMXConnector jmxConnector = JMXConnectorFactory.connect(url, environment);
//					MBeanServerConnection mbeanServer = jmxConnector.getMBeanServerConnection();
//					Set<ObjectName> queryNames3 = mbeanServer.queryNames(null, null);
//					this.connection = mbeanServer;

				} else {
					Map<String,String[]> environment = new HashMap<String, String[]>();
					String[] credentials = new String[] {username, password};
					environment.put(JMXConnector.CREDENTIALS, credentials);
	
					//JMXServiceURL url = new JMXServiceURL("service:jmx:rmi://"+host+"/jndi/rmi://"+host+":"+port+"/jmxrmi");
					//JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://"+host+":"+port+"/jmxrmi");
					JMXServiceURL url = new JMXServiceURL("service:jmx:remoting-jmx://" + bindAddress + ":" + managementPort);
					JMXConnector jmxConnector = JMXConnectorFactory.connect(url, environment);
					MBeanServerConnection mbeanServer = jmxConnector.getMBeanServerConnection();
					this.connection = mbeanServer;
					initialized.set(true);
				}
			}
			return connection;
		}
	}

	public long getMBeanCount() throws Exception {
		return getMBeanServer().getMBeanCount();
	}

	public MBeanInfo getMBeanInfo(ObjectName objectName) throws Exception {
		return getMBeanServer().getMBeanInfo(objectName);
	}
	
	public Set<ObjectName> queryNames() throws Exception {
		return getMBeanServer().queryNames(null, null);
	}
	
	public Object invoke(ObjectName name, String method) throws Exception {
		return MBeanUtil.invoke(getMBeanServer(), name, method, new String[] {}, new Object[] {});
	}

	public Object invoke(ObjectName name, String method, Object[] parameters, String[] signature) throws Exception {
		return MBeanUtil.invoke(getMBeanServer(), name, method, signature, parameters);
	}

//	public Object invoke(MBeanServerConnection connection, ObjectName name, String method, Object[] parameters, String[] signature) throws Exception {
//		log.info("invoke: invoked: "+name+"."+method+"()");
//		Throwable exception = null;
//		
//		try {
//			Set<ObjectName> queryNames = queryNames();
//			log.debug("Invoking " + name.getCanonicalName() + " method=" + method);
//			if (parameters != null)
//				log.debug("args=" + Arrays.asList(parameters));
//			Object result = connection.invoke(name, method, parameters, signature);
//			log.info("invoke: returns: "+result);
//			return result;
//
//		} catch (JMException e) {
//			exception = e;
//		} catch (JMRuntimeException e) {
//			exception = e;
//		} catch (Throwable e) {
//			exception = e;
//		}
//		
//		Exception cause = ExceptionUtil.getRootCause(exception);
//		log.info("invoke: throws: "+cause);
//		log.error("Error: ", cause);
//		throw cause;
//	}

	public void addNotificationListener(ObjectName objectName, NotificationListener notificationListener) throws Exception {
		//log.debug("addNotificationListener: invoked: "+objectName);
		Throwable exception = null;

		try {
			getMBeanServer().addNotificationListener(objectName, notificationListener, null, null);
			return;
			
		} catch (InstanceNotFoundException e) {
			//Set<ObjectName> queryNames = getMBeanServer().queryNames(null, null);
			exception = e;
		} catch (IOException e) {
			exception = e;
		} catch (Exception e) {
			exception = e;
		}

		Exception cause = ExceptionUtil.getRootCause(exception);
		log.error("addNotificationListener: throws: "+cause);
		throw cause;
	}
	
	public void removeNotificationListener(ObjectName objectName, NotificationListener notificationListener) throws Exception {
		getMBeanServer().removeNotificationListener(objectName, notificationListener);
	}
	
	
    public void createMBean(String className, String mbeanName) throws Exception {
    	synchronized (mutex) {
	        ObjectName objectName = makeObjectName(mbeanName);
	        createMBean(className, objectName);
	    }
    }
    
    public void createMBean(String className, ObjectName objectName) throws Exception {
		//log.debug("createMBean: invoked: "+className+": "+objectName);
		Throwable exception = null;

		try {
            getMBeanServer().createMBean(className, objectName);
			log.debug("JMX MBean created: "+objectName);
        } catch (ReflectionException e) {
			exception = e;
		} catch (MBeanException e) {
			exception = e;
		} catch (IOException e) {
			exception = e;
		} catch (Exception e) {
			exception = e;
		}

		Exception cause = ExceptionUtil.getRootCause(exception);
		log.error("createMBean: throws: "+cause);
		throw cause;
    }
    
    public ObjectName makeObjectName(String mbeanName) {
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

    
	
    public void sendNotification(String mbeanName, Notification notification) throws Exception {
		ObjectName objectName = MBeanUtil.makeObjectName(mbeanName);
		sendNotification(objectName, notification);
	}
	
	public void sendNotification(ObjectName objectName, Notification notification) throws Exception {
		Object[] parameters = { notification };
		String[] signature = { "javax.management.Notification" };
		connection.invoke(objectName, "sendNotification", parameters, signature);
	}
	
	
	
//	protected Object lookupMBean(String mbeanName) throws Exception {
//		ObjectName objectName = MBeanUtil.makeObjectName(mbeanName);
//		return lookupMBean(objectName);
//	}
//
//	protected Object lookupMBean(ObjectName objectName) throws Exception {
//		Set<ObjectInstance> mbeans = ManagementFactory.getPlatformMBeanServer().queryMBeans(objectName, null);
//		//Set<ObjectName> objectNames = platformMBeanServer.queryNames(null, null);
//		Assert.isTrue(mbeans.size() == 1, "");
//		return mbeans[0];
//	}
}
