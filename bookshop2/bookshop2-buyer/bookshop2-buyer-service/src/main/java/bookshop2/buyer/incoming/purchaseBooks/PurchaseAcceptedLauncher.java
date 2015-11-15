package bookshop2.buyer.incoming.purchaseBooks;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.launcher.ServiceLauncher;
import org.aries.tx.service.AbstractServiceInitializer;
import org.aries.tx.util.EndpointDescriptor;


public class PurchaseAcceptedLauncher extends AbstractServiceInitializer {

	private static final Log log = LogFactory.getLog(PurchaseAcceptedLauncher.class);

	public static PurchaseAcceptedLauncher INSTANCE = new PurchaseAcceptedLauncher();

	private ServiceLauncher launcher;


	public void initialize(String host, int port) {
		EndpointDescriptor descriptor = new EndpointDescriptor();
		descriptor.setNamespace("http://bookshop2/buyer");
		descriptor.setContext("/bookshop2/buyer");
		descriptor.setServiceName("purchaseAcceptedService");
		descriptor.setPortTypeName("purchaseAccepted");
		descriptor.setDescription("Launcher component for the purchaseAccepted service");
		descriptor.setPackageName(PurchaseAccepted.class.getPackage().getName());
		descriptor.setInterfaceClass(PurchaseAccepted.class.getName());
		descriptor.setImplementationClass(PurchaseAcceptedListenerForJAXWS.class.getName());
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
