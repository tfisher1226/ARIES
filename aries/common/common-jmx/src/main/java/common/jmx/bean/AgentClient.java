package common.jmx.bean;

import javax.management.JMX;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class AgentClient extends AbstractJmxClient {

	private static Log log = LogFactory.getLog(AgentClient.class);

	private JMXConnector connection;


	public static void main(String[] args) throws Exception { 
		AgentClient client = new AgentClient();
		client.stop("serviceName");
	}

	public void start(String serviceName) throws Exception {
		String url = getURL();
		try {
			connection = openConnection(url);
			ObjectName mbeanName = new ObjectName("common.jmx:name=Agent");
			log.info("Invoking \"start\" method on remote MBean: "+mbeanName);
			AgentMBean agentProxy = createProxy(mbeanName); 
			agentProxy.startService(serviceName);
			log.info("Invoked \"start\" method on remote MBean: "+mbeanName);

		} catch (Exception e) {
			log.error("Error: ", e);

		} finally {
			closeConnection(connection, url);
		}
	}

	public void stop(String serviceName) throws Exception {
		String url = getURL();
		try {
			connection = openConnection(url);
			ObjectName mbeanName = new ObjectName("common.jmx:name=Agent");
			log.info("Invoking \"stop\" method on remote MBean: "+mbeanName);
			AgentMBean agentProxy = createProxy(mbeanName); 
			agentProxy.stopService(serviceName);
			log.info("Invoked \"stop\" method on remote MBean: "+mbeanName);
			
		} catch (Throwable e) {
			//Throwable rootCause = ExceptionUtils.getRootCause(e);
			String message = ExceptionUtils.getRootCauseMessage(e).toLowerCase();
			if (message != null && !message.contains("connection reset") && !message.contains("connection refused"))
				log.error("Error: ", e);
			
		} finally {
			closeConnection(connection, url);
		}
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

	// Construct proxy for the Agent
	protected AgentMBean createProxy(ObjectName mbeanName) throws Exception {
		log.info("Creating MBeanProxy: "+mbeanName);
		MBeanServerConnection mbsc = connection.getMBeanServerConnection();
		AgentMBean mbeanProxy = JMX.newMBeanProxy(mbsc, mbeanName, AgentMBean.class, true);
		log.info("Created MBeanProxy: "+mbeanName);
		log.info("Remote application is"+(mbeanProxy.isRunning() ? "" : "not")+" running");
		return mbeanProxy;
	}

	// close and clean up connection
	public void closeConnection(JMXConnector connection, String url) throws Exception {
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
