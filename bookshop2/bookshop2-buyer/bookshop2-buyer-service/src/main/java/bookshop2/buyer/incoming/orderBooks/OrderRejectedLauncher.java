package bookshop2.buyer.incoming.orderBooks;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.launcher.ServiceLauncher;
import org.aries.tx.service.AbstractServiceInitializer;
import org.aries.tx.util.EndpointDescriptor;


public class OrderRejectedLauncher extends AbstractServiceInitializer {

	private static final Log log = LogFactory.getLog(OrderRejectedLauncher.class);

	public static OrderRejectedLauncher INSTANCE = new OrderRejectedLauncher();

	private ServiceLauncher launcher;


	public void initialize(String host, int port) {
		EndpointDescriptor descriptor = new EndpointDescriptor();
		descriptor.setNamespace("http://bookshop2/buyer");
		descriptor.setContext("/bookshop2/buyer");
		descriptor.setServiceName("orderRejectedService");
		descriptor.setPortTypeName("orderRejected");
		descriptor.setDescription("Launcher component for the orderRejected service");
		descriptor.setPackageName(OrderRejected.class.getPackage().getName());
		descriptor.setInterfaceClass(OrderRejected.class.getName());
		descriptor.setImplementationClass(OrderRejectedListenerForJAXWS.class.getName());
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
