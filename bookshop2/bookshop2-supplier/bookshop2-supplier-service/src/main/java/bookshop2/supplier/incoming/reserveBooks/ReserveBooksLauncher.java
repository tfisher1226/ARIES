package bookshop2.supplier.incoming.reserveBooks;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.launcher.ServiceLauncher;
import org.aries.tx.service.AbstractServiceInitializer;
import org.aries.tx.util.EndpointDescriptor;


public class ReserveBooksLauncher extends AbstractServiceInitializer {
	
	private static final Log log = LogFactory.getLog(ReserveBooksLauncher.class);
	
	public static ReserveBooksLauncher INSTANCE = new ReserveBooksLauncher();
	
	private ServiceLauncher launcher;
	
	
	public void initialize(String host, int port) {
		EndpointDescriptor descriptor = new EndpointDescriptor();
		descriptor.setNamespace("http://bookshop2/supplier");
		descriptor.setContext("/bookshop2/supplier");
		descriptor.setServiceName("reserveBooksService");
		descriptor.setPortTypeName("reserveBooks");
		descriptor.setDescription("Launcher component for the reserveBooks service");
		descriptor.setPackageName(ReserveBooks.class.getPackage().getName());
		descriptor.setInterfaceClass(ReserveBooks.class.getName());
		descriptor.setImplementationClass(ReserveBooksListenerForJAXWS.class.getName());
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
