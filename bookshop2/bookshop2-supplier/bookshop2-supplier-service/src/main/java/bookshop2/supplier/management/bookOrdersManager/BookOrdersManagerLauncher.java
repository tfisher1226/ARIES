package bookshop2.supplier.management.bookOrdersManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.launcher.ServiceLauncher;
import org.aries.tx.service.AbstractServiceInitializer;
import org.aries.tx.util.EndpointDescriptor;


public class BookOrdersManagerLauncher extends AbstractServiceInitializer {
	
	private static final Log log = LogFactory.getLog(BookOrdersManagerLauncher.class);
	
	public static BookOrdersManagerLauncher INSTANCE = new BookOrdersManagerLauncher();
	
	private ServiceLauncher launcher;
	
	
	public void initialize(String host, int port) {
		EndpointDescriptor descriptor = new EndpointDescriptor();
		descriptor.setNamespace("http://bookshop2/supplier");
		descriptor.setContext("/bookshop2/supplier");
		descriptor.setServiceName("bookOrdersManagerService");
		descriptor.setPortTypeName("bookOrdersManager");
		descriptor.setDescription("Launcher component for the bookOrdersManager service");
		descriptor.setPackageName(BookOrdersManager.class.getPackage().getName());
		descriptor.setInterfaceClass(BookOrdersManager.class.getName());
		descriptor.setImplementationClass(BookOrdersManagerListenerForJAXWS.class.getName());
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
