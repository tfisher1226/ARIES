package bookshop2.supplier.management.reservedBooksManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.launcher.ServiceLauncher;
import org.aries.tx.service.AbstractServiceInitializer;
import org.aries.tx.util.EndpointDescriptor;


public class ReservedBooksManagerLauncher extends AbstractServiceInitializer {
	
	private static final Log log = LogFactory.getLog(ReservedBooksManagerLauncher.class);
	
	public static ReservedBooksManagerLauncher INSTANCE = new ReservedBooksManagerLauncher();
	
	private ServiceLauncher launcher;
	
	
	public void initialize(String host, int port) {
		EndpointDescriptor descriptor = new EndpointDescriptor();
		descriptor.setNamespace("http://bookshop2/supplier");
		descriptor.setContext("/bookshop2/supplier");
		descriptor.setServiceName("reservedBooksManagerService");
		descriptor.setPortTypeName("reservedBooksManager");
		descriptor.setDescription("Launcher component for the reservedBooksManager service");
		descriptor.setPackageName(ReservedBooksManager.class.getPackage().getName());
		descriptor.setInterfaceClass(ReservedBooksManager.class.getName());
		descriptor.setImplementationClass(ReservedBooksManagerListenerForJAXWS.class.getName());
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
