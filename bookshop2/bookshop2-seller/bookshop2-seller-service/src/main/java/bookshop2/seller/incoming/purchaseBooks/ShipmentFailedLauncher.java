package bookshop2.seller.incoming.purchaseBooks;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.launcher.ServiceLauncher;
import org.aries.tx.service.AbstractServiceInitializer;
import org.aries.tx.util.EndpointDescriptor;


public class ShipmentFailedLauncher extends AbstractServiceInitializer {
	
	private static final Log log = LogFactory.getLog(ShipmentFailedLauncher.class);
	
	public static ShipmentFailedLauncher INSTANCE = new ShipmentFailedLauncher();
	
	private ServiceLauncher launcher;
	
	
	public void initialize(String host, int port) {
		EndpointDescriptor descriptor = new EndpointDescriptor();
		descriptor.setNamespace("http://bookshop2/seller");
		descriptor.setContext("/bookshop2/seller");
		descriptor.setServiceName("shipmentFailedService");
		descriptor.setPortTypeName("shipmentFailed");
		descriptor.setDescription("Launcher component for the shipmentFailed service");
		descriptor.setPackageName(ShipmentFailed.class.getPackage().getName());
		descriptor.setInterfaceClass(ShipmentFailed.class.getName());
		descriptor.setImplementationClass(ShipmentFailedListenerForJAXWS.class.getName());
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
