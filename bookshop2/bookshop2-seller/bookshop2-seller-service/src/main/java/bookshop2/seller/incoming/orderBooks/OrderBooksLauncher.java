package bookshop2.seller.incoming.orderBooks;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.launcher.ServiceLauncher;
import org.aries.tx.service.AbstractServiceInitializer;
import org.aries.tx.util.EndpointDescriptor;


public class OrderBooksLauncher extends AbstractServiceInitializer {

	private static final Log log = LogFactory.getLog(OrderBooksLauncher.class);

	public static OrderBooksLauncher INSTANCE = new OrderBooksLauncher();

	private ServiceLauncher launcher;


	public void initialize(String host, int port) {
		EndpointDescriptor descriptor = new EndpointDescriptor();
		descriptor.setNamespace("http://bookshop2/seller");
		descriptor.setContext("/bookshop2/seller");
		descriptor.setServiceName("orderBooksService");
		descriptor.setPortTypeName("orderBooks");
		descriptor.setDescription("Launcher component for the orderBooks service");
		descriptor.setPackageName(OrderBooks.class.getPackage().getName());
		descriptor.setInterfaceClass(OrderBooks.class.getName());
		descriptor.setImplementationClass(OrderBooksListenerForJAXWS.class.getName());
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
