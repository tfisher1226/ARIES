package redhat.jee_migration_example.incoming.logEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.launcher.ServiceLauncher;
import org.aries.tx.service.AbstractServiceInitializer;
import org.aries.tx.util.EndpointDescriptor;


public class LogEventLauncher extends AbstractServiceInitializer {
	
	private static final Log log = LogFactory.getLog(LogEventLauncher.class);
	
	public static LogEventLauncher INSTANCE = new LogEventLauncher();
	
	private ServiceLauncher launcher;
	
	
	public void initialize(String host, int port) {
		EndpointDescriptor descriptor = new EndpointDescriptor();
		descriptor.setNamespace("http://www.redhat.com/jee-migration-example");
		descriptor.setContext("/redhat/jee_migration_example");
		descriptor.setServiceName("logEventService");
		descriptor.setPortTypeName("logEvent");
		descriptor.setDescription("Launcher component for the logEvent service");
		descriptor.setPackageName(LogEvent.class.getPackage().getName());
		descriptor.setInterfaceClass(LogEvent.class.getName());
		descriptor.setImplementationClass(LogEventListenerForJAXWS.class.getName());
		descriptor.setBindAddress(host);
		descriptor.setBindPort(port);
		launcher = launch(descriptor);
	}
	
	public void shutdown() {
		if (launcher != null)
			launcher.shutdown();
		launcher = null;
	}
	
}
