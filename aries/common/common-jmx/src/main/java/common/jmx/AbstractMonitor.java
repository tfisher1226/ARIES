package common.jmx;

import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import common.jmx.bean.AbstractJmxClient;


public abstract class AbstractMonitor extends AbstractJmxClient {

	protected Log log = LogFactory.getLog(getClass());

	protected JMXConnector connection = null;
	
	
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
