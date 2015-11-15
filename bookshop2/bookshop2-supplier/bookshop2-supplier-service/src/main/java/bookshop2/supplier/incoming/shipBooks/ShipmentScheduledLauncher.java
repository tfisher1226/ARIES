package bookshop2.supplier.incoming.shipBooks;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.launcher.ServiceLauncher;
import org.aries.tx.service.AbstractServiceInitializer;
import org.aries.tx.util.EndpointDescriptor;


public class ShipmentScheduledLauncher extends AbstractServiceInitializer {
	
	private static final Log log = LogFactory.getLog(ShipmentScheduledLauncher.class);
	
	public static ShipmentScheduledLauncher INSTANCE = new ShipmentScheduledLauncher();
	
	private ServiceLauncher launcher;
	
	
	public void initialize(String host, int port) {
		EndpointDescriptor descriptor = new EndpointDescriptor();
		descriptor.setNamespace("http://bookshop2/supplier");
		descriptor.setContext("/bookshop2/supplier");
		descriptor.setServiceName("shipmentScheduledService");
		descriptor.setPortTypeName("shipmentScheduled");
		descriptor.setDescription("Launcher component for the shipmentScheduled service");
		descriptor.setPackageName(ShipmentScheduled.class.getPackage().getName());
		descriptor.setInterfaceClass(ShipmentScheduled.class.getName());
		descriptor.setImplementationClass(ShipmentScheduledListenerForJAXWS.class.getName());
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
