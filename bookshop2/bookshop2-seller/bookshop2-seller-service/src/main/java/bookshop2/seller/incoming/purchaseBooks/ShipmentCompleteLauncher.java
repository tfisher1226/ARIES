package bookshop2.seller.incoming.purchaseBooks;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.launcher.ServiceLauncher;
import org.aries.tx.service.AbstractServiceInitializer;
import org.aries.tx.util.EndpointDescriptor;


public class ShipmentCompleteLauncher extends AbstractServiceInitializer {
	
	private static final Log log = LogFactory.getLog(ShipmentCompleteLauncher.class);
	
	public static ShipmentCompleteLauncher INSTANCE = new ShipmentCompleteLauncher();
	
	private ServiceLauncher launcher;
	
	
	public void initialize(String host, int port) {
		EndpointDescriptor descriptor = new EndpointDescriptor();
		descriptor.setNamespace("http://bookshop2/seller");
		descriptor.setContext("/bookshop2/seller");
		descriptor.setServiceName("shipmentCompleteService");
		descriptor.setPortTypeName("shipmentComplete");
		descriptor.setDescription("Launcher component for the shipmentComplete service");
		descriptor.setPackageName(ShipmentComplete.class.getPackage().getName());
		descriptor.setInterfaceClass(ShipmentComplete.class.getName());
		descriptor.setImplementationClass(ShipmentCompleteListenerForJAXWS.class.getName());
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
