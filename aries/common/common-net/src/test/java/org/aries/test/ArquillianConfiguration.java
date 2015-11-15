package org.aries.test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;


public class ArquillianConfiguration {

	private static Logger log = Logger.getLogger(ArquillianConfiguration.class);

	private static final String BIND_ADDRESS = "bindAddress";
	
	private static final String MANAGEMENT_PORT = "managementPort";

	private static final String USERNAME = "username";
	
	private static final String PASSWORD = "password";

	public static final String ADMINISTERED_QUEUE = "administered.queue";
	
	public static final String ADMINISTERED_TOPIC = "administered.topic";
	
	public static final String LOCAL_CONNECTION_FACTORY_NAME = "localConnectionFactory";
	
	public static final String REMOTE_CONNECTION_FACTORY_NAME = "remoteConnectionFactory";
	
	public static final String XA_CONNECTION_FACTORY_NAME = "xaConnectionFactory";

	public static final String DEFAULT_JNDI_FILE_NAME = "node1-jndi-local.properties";

	public static final String DEFAULT_JMS_FILE_NAME = "node1-jms.properties";

	public static final String DEFAULT_CONNECTION_FACTORY = "/ConnectionFactory";
	
	
	private String username;
	
	private String password;
	
	private String bindAddress;

	private String managementPort;

	private String jndiPropertyFileName;

	private String jmsPropertyFileName;

	private Properties jndiProperties;
	
	private Properties jmsProperties;

	private String localConnectionFactoryName;

	private String remoteConnectionFactoryName;

	private String xaConnectionFactoryName;

	private String queueDestinationName;
	
	private String topicDestinationName;


	public ArquillianConfiguration() {
		jndiPropertyFileName = DEFAULT_JNDI_FILE_NAME;
		jmsPropertyFileName = DEFAULT_JMS_FILE_NAME;
	}
	
	public void initialize() {
		jndiProperties = loadJNDIProperties(jndiPropertyFileName);
		jmsProperties = loadJMSProperties(jmsPropertyFileName);
		setJMSProperties(jmsProperties);
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

	public String getJndiPropertyFileName() {
		return jndiPropertyFileName;
	}

	public void setJndiPropertyFileName(String jndiPropertyFileName) {
		this.jndiPropertyFileName = jndiPropertyFileName;
	}

	public String getJmsPropertyFileName() {
		return jmsPropertyFileName;
	}

	public void setJmsPropertyFileName(String jmsPropertyFileName) {
		this.jmsPropertyFileName = jmsPropertyFileName;
	}

	private void setJMSProperties(Properties props) {
		bindAddress = props.getProperty(BIND_ADDRESS);
		managementPort = props.getProperty(MANAGEMENT_PORT);
		username = props.getProperty(USERNAME);
		password = props.getProperty(PASSWORD);
		localConnectionFactoryName = props.getProperty(LOCAL_CONNECTION_FACTORY_NAME);
		remoteConnectionFactoryName = props.getProperty(REMOTE_CONNECTION_FACTORY_NAME);
		xaConnectionFactoryName = props.getProperty(XA_CONNECTION_FACTORY_NAME);
		queueDestinationName = props.getProperty(ADMINISTERED_QUEUE);
		topicDestinationName = props.getProperty(ADMINISTERED_TOPIC);
	}

	public Properties loadJMSProperties(String name) {
		log.info("loadJMSProperties() started: "+name);
		Properties props = new Properties();
		try {
			log.info("loadJMSProperties() creating file: "+name);
			InputStream stream = openStream(name);
			props = new Properties();
			log.info("loadJMSProperties() loading file: "+stream);
			props.load(stream);
		} catch (IOException e) {
			log.error(e);
		}
		log.info("loadJMSProperties() returns: "+props);
		return props;
	}

	private InputStream openStream(String name) throws FileNotFoundException {
		log.info("openStream() invoked: "+name);
		InputStream stream = getClass().getResourceAsStream("/" + name);
		log.info("openStream() returns: "+stream);
		return stream;
	}

	public Properties loadJNDIProperties(String name) {
		log.info("loadJNDIProperties() started: "+name);
		Properties props = null;
		try {
			log.info("loadJNDIProperties() opening file: "+name);
			InputStream stream = openStream(name);
			props = new Properties();
			log.info("loadJNDIProperties() loading file: "+stream);
			props.load(stream);
		} catch (Exception e) {
			log.error(e);
		} finally {
			log.info(props);
		}
		log.info("loadJNDIProperties() returns: "+props);
		return props;
	}

	// public List<DestinationFactory> lookupDestinationFactories()
	// throws NamingException {
	// log.info("Creating InitialContext with properties: " + jndiProperties);
	// InitialContext ic = new InitialContext(getJndiProperties());
	// String destinationPattern = getJMSDestinations();
	// String[] split = destinationPattern.split(";");
	// List<DestinationFactory> factories = new ArrayList<DestinationFactory>();
	//
	// for (String dest : split) {
	// try {
	// DestinationFactory factory = new AdminDestinationFactory(ic,
	// dest);
	// if (factory.getType().equals(type)) {
	// factories.add(factory);
	// }
	// } catch (NamingException e) {
	// log.error(e);
	// }
	// }
	// return factories;
	// }

	private Properties getJndiProperties() {
		if (jndiProperties == null) {
			jndiProperties = loadJNDIProperties(DEFAULT_JNDI_FILE_NAME);
		}
		return jndiProperties;
	}

	// /**
	// *
	// * @return destination defined for this type
	// * @throws NamingException
	// */
	// public Destination lookupDestination() throws NamingException {
	// InitialContext ic = getInitialContext();
	// String destinationPattern = getJMSDestinations();
	// log.debug("Looking for administered destination: " + destinationPattern);
	// Destination dest = (Destination) ic.lookup(destinationPattern);
	// if (dest == null) {
	// log.error("Destination not found using JNDI: " + destinationPattern);
	// } else if ((dest instanceof Queue && !isQueueConfig())
	// || (dest instanceof Topic && isQueueConfig())) {
	// log.error("Incorrect type of destination.");
	// }
	// return dest;
	// }

	public String getLocalConnectionFactoryName() {
		return localConnectionFactoryName;
	}

	public String getRemoteConnectionFactoryName() {
		return remoteConnectionFactoryName;
	}

	public String getXAConnectionFactoryName() {
		return xaConnectionFactoryName;
	}

	public String getTopicDestinationName() {
		return topicDestinationName;
	}

	public String getQueueDestinationName() {
		return queueDestinationName;
	}

	public Properties getInitialContextProperties() {
		return getJndiProperties();
	}

}
