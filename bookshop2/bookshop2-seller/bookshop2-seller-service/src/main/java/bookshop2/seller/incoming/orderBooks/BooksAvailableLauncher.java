package bookshop2.seller.incoming.orderBooks;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.launcher.ServiceLauncher;
import org.aries.tx.service.AbstractServiceInitializer;
import org.aries.tx.util.EndpointDescriptor;


public class BooksAvailableLauncher extends AbstractServiceInitializer {
	
	private static final Log log = LogFactory.getLog(BooksAvailableLauncher.class);
	
	public static BooksAvailableLauncher INSTANCE = new BooksAvailableLauncher();
	
	private ServiceLauncher launcher;
	
	
	public void initialize(String host, int port) {
		EndpointDescriptor descriptor = new EndpointDescriptor();
		descriptor.setNamespace("http://bookshop2/seller");
		descriptor.setContext("/bookshop2/seller");
		descriptor.setServiceName("booksAvailableService");
		descriptor.setPortTypeName("booksAvailable");
		descriptor.setDescription("Launcher component for the booksAvailable service");
		descriptor.setPackageName(BooksAvailable.class.getPackage().getName());
		descriptor.setInterfaceClass(BooksAvailable.class.getName());
		descriptor.setImplementationClass(BooksAvailableListenerForJAXWS.class.getName());
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
