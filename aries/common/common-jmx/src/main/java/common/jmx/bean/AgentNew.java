package common.jmx.bean;

import java.rmi.registry.LocateRegistry;

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectName;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;


public class AgentNew implements AgentMBean {

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
		AgentNew agent = new AgentNew();
		agent.initialize();
		agent.startConnectorServer();
		agent.startService("serviceName");
		//TODO
		agent.stopConnectorServer();
	}

	
	private JMXConnectorServer jmxServer;

	
	public AgentNew() {
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

	@Override
	public void startService(String serviceName) {
		// TODO: start application here
		System.out.println("remote start called");
	}

	@Override
	public void stopService(String serviceName) {
		// TODO: stop application here
		System.out.println("remote stop called");
		//TODO
		System.exit(0);
	}

	public boolean isRunning() {
		return Thread.currentThread().isAlive();
	}

	
	
	public JMXConnectorServer createConnectorServer() throws Exception {
		LocateRegistry.createRegistry(1199);
		MBeanServer mbs = MBeanServerFactory.createMBeanServer(); 
		JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://localhost:1199/jmxrmi"); 
		JMXConnectorServer connectorServer = JMXConnectorServerFactory.newJMXConnectorServer(url, null, mbs);
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
			System.out.println("JMX started");
			AgentMBean monitor = new AgentNew();
			//MBeanServer server = ManagementFactory.getPlatformMBeanServer();
			MBeanServer server = jmxServer.getMBeanServer();
			ObjectName name = new ObjectName("common.jmx:name=Agent");
			server.registerMBean(monitor, name);

		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			// TODO: some final clean up could be here also
			System.out.println("JMX stopped");
		}
	}


}
