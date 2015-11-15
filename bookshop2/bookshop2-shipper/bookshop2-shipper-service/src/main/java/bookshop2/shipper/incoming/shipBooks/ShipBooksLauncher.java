package bookshop2.shipper.incoming.shipBooks;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.launcher.ServiceLauncher;
import org.aries.tx.service.AbstractServiceInitializer;
import org.aries.tx.util.EndpointDescriptor;


public class ShipBooksLauncher extends AbstractServiceInitializer {
	
	private static final Log log = LogFactory.getLog(ShipBooksLauncher.class);
	
	public static ShipBooksLauncher INSTANCE = new ShipBooksLauncher();
	
	private ServiceLauncher launcher;
	
	
	public void initialize(String host, int port) {
		EndpointDescriptor descriptor = new EndpointDescriptor();
		descriptor.setNamespace("http://bookshop2/shipper");
		descriptor.setContext("/bookshop2/shipper");
		descriptor.setServiceName("shipBooksService");
		descriptor.setPortTypeName("shipBooks");
		descriptor.setDescription("Launcher component for the shipBooks service");
		descriptor.setPackageName(ShipBooks.class.getPackage().getName());
		descriptor.setInterfaceClass(ShipBooks.class.getName());
		descriptor.setImplementationClass(ShipBooksListenerForJAXWS.class.getName());
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
