package bookshop2.buyer.incoming.purchaseBooks;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.launcher.ServiceLauncher;
import org.aries.tx.service.AbstractServiceInitializer;
import org.aries.tx.util.EndpointDescriptor;


public class PurchaseRejectedLauncher extends AbstractServiceInitializer {

	private static final Log log = LogFactory.getLog(PurchaseRejectedLauncher.class);

	public static PurchaseRejectedLauncher INSTANCE = new PurchaseRejectedLauncher();

	private ServiceLauncher launcher;


	public void initialize(String host, int port) {
		EndpointDescriptor descriptor = new EndpointDescriptor();
		descriptor.setNamespace("http://bookshop2/buyer");
		descriptor.setContext("/bookshop2/buyer");
		descriptor.setServiceName("purchaseRejectedService");
		descriptor.setPortTypeName("purchaseRejected");
		descriptor.setDescription("Launcher component for the purchaseRejected service");
		descriptor.setPackageName(PurchaseRejected.class.getPackage().getName());
		descriptor.setInterfaceClass(PurchaseRejected.class.getName());
		descriptor.setImplementationClass(PurchaseRejectedListenerForJAXWS.class.getName());
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
