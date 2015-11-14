package redhat.jee_migration_example.incoming.lookupItem;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.launcher.ServiceLauncher;
import org.aries.tx.service.AbstractServiceInitializer;
import org.aries.tx.util.EndpointDescriptor;


public class LookupItemLauncher extends AbstractServiceInitializer {
	
	private static final Log log = LogFactory.getLog(LookupItemLauncher.class);
	
	public static LookupItemLauncher INSTANCE = new LookupItemLauncher();
	
	private ServiceLauncher launcher;
	
	
	public void initialize(String host, int port) {
		EndpointDescriptor descriptor = new EndpointDescriptor();
		descriptor.setNamespace("http://www.redhat.com/jee-migration-example");
		descriptor.setContext("/redhat/jee_migration_example");
		descriptor.setServiceName("lookupItemService");
		descriptor.setPortTypeName("lookupItem");
		descriptor.setDescription("Launcher component for the lookupItem service");
		descriptor.setPackageName(LookupItem.class.getPackage().getName());
		descriptor.setInterfaceClass(LookupItem.class.getName());
		descriptor.setImplementationClass(LookupItemListenerForJAXWS.class.getName());
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
