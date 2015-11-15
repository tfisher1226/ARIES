package bookshop2.seller.incoming.purchaseBooks;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.launcher.ServiceLauncher;
import org.aries.tx.service.AbstractServiceInitializer;
import org.aries.tx.util.EndpointDescriptor;


public class PurchaseBooksLauncher extends AbstractServiceInitializer {

	private static final Log log = LogFactory.getLog(PurchaseBooksLauncher.class);

	public static PurchaseBooksLauncher INSTANCE = new PurchaseBooksLauncher();

	private ServiceLauncher launcher;


	public void initialize(String host, int port) {
		EndpointDescriptor descriptor = new EndpointDescriptor();
		descriptor.setNamespace("http://bookshop2/seller");
		descriptor.setContext("/bookshop2/seller");
		descriptor.setServiceName("purchaseBooksService");
		descriptor.setPortTypeName("purchaseBooks");
		descriptor.setDescription("Launcher component for the purchaseBooks service");
		descriptor.setPackageName(PurchaseBooks.class.getPackage().getName());
		descriptor.setInterfaceClass(PurchaseBooks.class.getName());
		descriptor.setImplementationClass(PurchaseBooksListenerForJAXWS.class.getName());
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
