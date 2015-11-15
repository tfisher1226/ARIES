package bookshop2.supplier.incoming.queryBooks;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.launcher.ServiceLauncher;
import org.aries.tx.service.AbstractServiceInitializer;
import org.aries.tx.util.EndpointDescriptor;


public class QueryBooksLauncher extends AbstractServiceInitializer {
	
	private static final Log log = LogFactory.getLog(QueryBooksLauncher.class);
	
	public static QueryBooksLauncher INSTANCE = new QueryBooksLauncher();
	
	private ServiceLauncher launcher;
	
	
	public void initialize(String host, int port) {
		EndpointDescriptor descriptor = new EndpointDescriptor();
		descriptor.setNamespace("http://bookshop2/supplier");
		descriptor.setContext("/bookshop2/supplier");
		descriptor.setServiceName("queryBooksService");
		descriptor.setPortTypeName("queryBooks");
		descriptor.setDescription("Launcher component for the queryBooks service");
		descriptor.setPackageName(QueryBooks.class.getPackage().getName());
		descriptor.setInterfaceClass(QueryBooks.class.getName());
		descriptor.setImplementationClass(QueryBooksListenerForJAXWS.class.getName());
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
