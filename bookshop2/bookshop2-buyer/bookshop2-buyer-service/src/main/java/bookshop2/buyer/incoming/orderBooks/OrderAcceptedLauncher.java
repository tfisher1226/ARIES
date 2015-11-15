package bookshop2.buyer.incoming.orderBooks;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.launcher.ServiceLauncher;
import org.aries.tx.service.AbstractServiceInitializer;
import org.aries.tx.util.EndpointDescriptor;


public class OrderAcceptedLauncher extends AbstractServiceInitializer {

	private static final Log log = LogFactory.getLog(OrderAcceptedLauncher.class);

	public static OrderAcceptedLauncher INSTANCE = new OrderAcceptedLauncher();

	private ServiceLauncher launcher;


	public void initialize(String host, int port) {
		EndpointDescriptor descriptor = new EndpointDescriptor();
		descriptor.setNamespace("http://bookshop2/buyer");
		descriptor.setContext("/bookshop2/buyer");
		descriptor.setServiceName("orderAcceptedService");
		descriptor.setPortTypeName("orderAccepted");
		descriptor.setDescription("Launcher component for the orderAccepted service");
		descriptor.setPackageName(OrderAccepted.class.getPackage().getName());
		descriptor.setInterfaceClass(OrderAccepted.class.getName());
		descriptor.setImplementationClass(OrderAcceptedListenerForJAXWS.class.getName());
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
