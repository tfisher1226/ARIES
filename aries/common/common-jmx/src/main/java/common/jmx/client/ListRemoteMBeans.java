package common.jmx.client;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryPoolMXBean;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.management.MBeanInfo;
import javax.management.MBeanServerConnection;
import javax.management.ObjectInstance;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;


public class ListRemoteMBeans {

	public static void main(String[] args) throws Exception {
		Map<String,String[]> environment = new HashMap<String, String[]>();
		String[] credentials = new String[] {"user", "pass"};
		environment.put(JMXConnector.CREDENTIALS, credentials);
		
		// SslRMIClientSocketFactory csf = new SslRMIClientSocketFactory();
		// SslRMIServerSocketFactory ssf = new SslRMIServerSocketFactory();
		// environment.put(RMIConnectorServer.RMI_CLIENT_SOCKET_FACTORY_ATTRIBUTE, csf);
		// environment.put(RMIConnectorServer.RMI_SERVER_SOCKET_FACTORY_ATTRIBUTE, ssf);
		
		JMXServiceURL address = new JMXServiceURL("service:rmi:///jndi/rmi://localhost:port/jmxrmi");
		JMXConnector connector = JMXConnectorFactory.connect(address, environment);
		MBeanServerConnection mbs = connector.getMBeanServerConnection();

		//get all mbeans
		Set<ObjectInstance> beans = mbs.queryMBeans(null,null);
		for (ObjectInstance instance : beans) {
			MBeanInfo info = mbs.getMBeanInfo(instance.getObjectName());
		}	
		
		List<MemoryPoolMXBean> memPoolBeans = ManagementFactory.getMemoryPoolMXBeans();
		for (MemoryPoolMXBean memPoolBean : memPoolBeans) {
		    System.out.println("Memory Pool: " + memPoolBean.getName());
		}
	}

}
