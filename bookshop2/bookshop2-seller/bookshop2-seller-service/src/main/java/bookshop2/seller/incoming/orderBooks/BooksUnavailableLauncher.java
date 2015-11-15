package bookshop2.seller.incoming.orderBooks;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.launcher.ServiceLauncher;
import org.aries.tx.service.AbstractServiceInitializer;
import org.aries.tx.util.EndpointDescriptor;


public class BooksUnavailableLauncher extends AbstractServiceInitializer {
	
	private static final Log log = LogFactory.getLog(BooksUnavailableLauncher.class);
	
	public static BooksUnavailableLauncher INSTANCE = new BooksUnavailableLauncher();
	
	private ServiceLauncher launcher;
	
	
	public void initialize(String host, int port) {
		EndpointDescriptor descriptor = new EndpointDescriptor();
		descriptor.setNamespace("http://bookshop2/seller");
		descriptor.setContext("/bookshop2/seller");
		descriptor.setServiceName("booksUnavailableService");
		descriptor.setPortTypeName("booksUnavailable");
		descriptor.setDescription("Launcher component for the booksUnavailable service");
		descriptor.setPackageName(BooksUnavailable.class.getPackage().getName());
		descriptor.setInterfaceClass(BooksUnavailable.class.getName());
		descriptor.setImplementationClass(BooksUnavailableListenerForJAXWS.class.getName());
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
