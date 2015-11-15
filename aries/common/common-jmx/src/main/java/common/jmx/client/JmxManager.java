package common.jmx.client;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.management.MBeanServerConnection;
import javax.management.NotificationListener;
import javax.management.ObjectName;
import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.util.ExceptionUtil;


public class JmxManager {

	protected Log log = LogFactory.getLog(getClass());
	
	public final static String SERVER_INFO = "jboss.system:type=ServerInfo";

	public final static String SERVER_CONFIG = "jboss.system:type=ServerConfig";

	protected Properties jndiProperties;
	
	protected MBeanClient client;


	public JmxManager() {
		client = new MBeanClient();
	}

	public boolean isLocal() {
		return client.isLocal();
	}

	public void setLocal(boolean isLocal) {
		client.setLocal(isLocal);
	}
	
	public String getUsername() {
		return client.getUsername();
	}

	public void setUsername(String username) {
		client.setUsername(username);
	}

	public String getPassword() {
		return client.getPassword();
	}

	public void setPassword(String password) {
		client.setPassword(password);
	}

	public String getBindAddress() {
		return client.getBindAddress();
	}

	public void setBindAddress(String bindAddress) {
		client.setBindAddress(bindAddress);
	}

	public String getManagementPort() {
		return client.getManagementPort();
	}

	public void setManagementPort(String managementPort) {
		client.setManagementPort(managementPort);
	}
	
	public Properties getJndiProperties() {
		return jndiProperties;
	}

	public void setJndiProperties(Properties jndiProperties) {
		this.jndiProperties = jndiProperties;
	}

	public void initialize() throws Exception {
		//nothing for now
	}
	
	public boolean ping() throws Exception {
		try {
			getHostName();
		} catch (Throwable e) {
			log.error(e);
		}
		return true;
	}
	
	public MBeanServerConnection getMBeanServerConnection() {
		try {
			return client.getMBeanServer();
		} catch (Exception e) {
			throw ExceptionUtil.rewrap(e);
		}
	}
	
	public long getMBeanCount() throws Exception {
		return client.getMBeanCount();
	}
	
//	public void registerMBean(ObjectName objectName, Object mbean) {
//		client.getMBeanServerConnection().
//	}
	
	public Set<ObjectName> queryObjectNames() throws Exception {
		return client.queryNames();
	}

	public List<ObjectName> getSortedObjectNames() throws Exception {
		Set<ObjectName> objectNames = queryObjectNames();
		List<ObjectName> sortedList = new ArrayList<ObjectName>(objectNames);
		Collections.sort(sortedList, new Comparator<ObjectName>() {
			public int compare(ObjectName o1, ObjectName o2) {
				return 0;
			}
		});
		return sortedList;
	}

	public void printObjectNames() throws Exception {
		List<ObjectName> sortedObjectNames = getSortedObjectNames();
		Iterator<ObjectName> iterator = sortedObjectNames.iterator();
		while (iterator.hasNext()) {
			ObjectName objectName = (ObjectName) iterator.next();
			System.err.println(objectName);
		}
	}

	public String getHostName() throws Exception {
		ObjectName objectName = new ObjectName(SERVER_INFO);
		String name = (String) client.getMBeanServer().getAttribute(objectName, "HostName");
		//String name = (String) client.invoke(objectName, "getHostName");
		return name;
	}
	
	public URL getHomeURL() throws Exception {
		ObjectName objectName = new ObjectName(SERVER_CONFIG);
		//URL url = (URL) client.getMBeanServer().getAttribute(objectName, "jbossHome");
		URL url = (URL) client.invoke(objectName, "getJBossHome");
		return url;
	}
	
	public URL getServerHomeURL() throws Exception {
		ObjectName objectName = new ObjectName(SERVER_CONFIG);
		//URL url = (URL) client.getMBeanServer().getAttribute(objectName, "serverHomeLocation");
		URL url = (URL) client.invoke(objectName, "getServerHomeLocation");
		return url;
	}

	public Object invoke(String mbeanName, String methodName) throws Exception {
		return client.invoke(new ObjectName(mbeanName), methodName);
	}

	public Object invoke(ObjectName objectName, String methodName) throws Exception {
		return client.invoke(objectName, methodName);
	}

	public Object invoke(String mbeanName, String methodName, Object[] parameters, String[] signature) throws Exception {
		return client.invoke(new ObjectName(mbeanName), methodName, parameters, signature);
	}

	public Object invoke(ObjectName objectName, String methodName, Object[] parameters, String[] signature) throws Exception {
		return client.invoke(objectName, methodName, parameters, signature);
	}


	public void addNotificationListener(ObjectName objectName, NotificationListener notificationListener) throws Exception {
		client.addNotificationListener(objectName, notificationListener);
	}
	
	public void removeNotificationListener(ObjectName objectName, NotificationListener notificationListener) throws Exception {
		client.removeNotificationListener(objectName, notificationListener);
	}
	

	public <T> T lookupObject(String jndiName) throws Exception {
		//InitialContext initialContext = new InitialContext();
		//Hashtable jndiProperties = new Hashtable();
		//jndiProperties.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.NamingContextFactory");
		//jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
		//Context initialContext = new InitialContext(jndiProperties);
		Context initialContext = getInitialContext(getJndiProperties());
		try {
			@SuppressWarnings("unchecked") T object = (T) initialContext.lookup(jndiName);
			return object;
		} catch (Exception e) {
			return null;
		}
	}
	
	public InitialContext getInitialContext(Properties properties) throws Exception {
		InitialContext initialContext = new InitialContext(properties);
		return initialContext;
	}

}
