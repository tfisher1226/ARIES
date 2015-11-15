package common.jmx.bean;

import java.rmi.ConnectException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectName;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class Agent extends AbstractJmxClient implements AgentMBean {

	private static Log log = LogFactory.getLog(Agent.class);
	
	
	/*
	 * 
	 * -Dcom.sun.management.jmxremote 
	 * -Dcom.sun.management.jmxremote.authenticate=false 
	 * -Dcom.sun.management.jmxremote.ssl=false 
	 * -Dcom.sun.management.jmxremote.port=1099
	 */
	public static void main(String[] args) throws Exception {
		//URL propertiesUrl = ResourceUtil.getResource("jmx.properties");
		//FileReader fileReader = new FileReader(propertiesUrl.getFile());
		//System.getProperties().load(fileReader);
		Agent agent = new Agent();
		agent.initialize();
		agent.startConnectorServer();
		agent.startService("serviceName");
		while (!Thread.interrupted()) {
			// loop until interrupted
			System.out.print(".");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
		agent.stopConnectorServer();
	}

	
	private JMXConnectorServer jmxServer;

	private Runnable shutdownHandler;

	
	public Agent() {
	}

	public void initialize() throws Exception {
		jmxServer = createConnectorServer();
		startConnectorServer(jmxServer);
		registerAsMBean();
	}

	@Override
	public String getName() {
		return "JMX Controlled App";
	}

	public void addShutdownHandler(Runnable shutdownHandler) {
		this.shutdownHandler = shutdownHandler;
	}

	@Override
	public void startService(String serviceName) {
		// TODO: start application here
		log.debug("remote start called");
	}

	@Override
	public void stopService(String serviceName) {
		log.debug("Stop request processing started...");
		// TODO: stop application here
		try {
			if (shutdownHandler != null)
				shutdownHandler.run();
			
		} catch (Throwable e) {
			log.error("Error", e);
			
		} finally {
			// TODO: some final clean up could be here
			log.debug("Stop request processing complete");
			System.exit(0);
		}
	}

	public boolean isRunning() {
		return Thread.currentThread().isAlive();
	}

	
	
	public JMXConnectorServer createConnectorServer() throws Exception {
		registry = getRegistry(getPort());
		MBeanServer mbeanServer = MBeanServerFactory.createMBeanServer(); 
		JMXServiceURL url = new JMXServiceURL(getURL()); 
		JMXConnectorServer connectorServer = JMXConnectorServerFactory.newJMXConnectorServer(url, null, mbeanServer);
		return connectorServer;
	}
	
	public void startConnectorServer() throws Exception {
		startConnectorServer(jmxServer); 
	}

	protected void startConnectorServer(JMXConnectorServer jmxServer) throws Exception {
		jmxServer.start(); 
	}

	public void stopConnectorServer() throws Exception {
		stopConnectorServer(jmxServer);
	}

	protected void stopConnectorServer(JMXConnectorServer jmxServer) throws Exception {
		jmxServer.stop(); 
	}

	public void registerAsMBean() {
		try {
			//MBeanServer server = ManagementFactory.getPlatformMBeanServer();
			MBeanServer server = jmxServer.getMBeanServer();
			ObjectName name = new ObjectName("common.jmx:name=Agent");
			server.registerMBean(this, name);
			log.debug("JMX MBean registered: "+name);

		} catch (Exception e) {
			log.error("Error", e);
		}
	}

	public void shutdown() throws Exception {
		registry.unbind(getService());
	}

	
	/** This is to hold strong references to each Registry. */
	public static Map<Integer, Registry> map = new ConcurrentHashMap<Integer, Registry>();
	
	private static Object mutex = new Object();

	private static Registry registry;
	
	
	protected static Registry getRegistry(int port) {
		synchronized (mutex) {
			if (registry == null) {
				//first, try to get registry from cache
				registry = map.get(port);
				if (registry != null)
					return registry;

				try {
					//try next to locate it
					registry = locateRegistry(port);
					map.put(port, registry);
				} catch (ConnectException e) {
					//ignore, fall thru
				} catch (RemoteException e) {
					log.error("Error", e);
					registry = null;
					throw new RuntimeException(e);
				}

				try {
					//ok, none located, create new distributed registry
					registry = createRegistry(port);
					map.put(port, registry);
					if (registry != null)
						return registry;					
				} catch (RemoteException e) {
					//ignore, fall thru
				}
			}
			return registry;
		}
	}
	
	protected static Registry locateRegistry(int port) throws RemoteException {
		Registry registry = LocateRegistry.getRegistry(port);
		if (registry == null)
			log.error("Could not locate registry for port: "+port);
		return registry;
	}
	
	protected static Registry createRegistry(int port) throws RemoteException {
		log.info("Creating RMI service registry on port: "+port);
		Registry registry = LocateRegistry.createRegistry(port);
		if (registry == null)
			log.error("Could not create registry for port: "+port);
		return registry;
	}
	
}
