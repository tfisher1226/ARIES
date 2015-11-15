package admin.incoming.skinService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.launcher.ServiceLauncher;
import org.aries.tx.service.AbstractServiceInitializer;
import org.aries.tx.util.EndpointDescriptor;


public class SkinServiceLauncher extends AbstractServiceInitializer {
	
	private static final Log log = LogFactory.getLog(SkinServiceLauncher.class);
	
	public static SkinServiceLauncher INSTANCE = new SkinServiceLauncher();
	
	private ServiceLauncher launcher;
	
	
	public void initialize(String host, int port) {
		EndpointDescriptor descriptor = new EndpointDescriptor();
		descriptor.setNamespace("http://admin");
		descriptor.setContext("/admin-service");
		descriptor.setServiceName("skinServiceService");
		descriptor.setPortTypeName("skinService");
		descriptor.setDescription("Launcher component for the skinService service");
		descriptor.setPackageName(SkinService.class.getPackage().getName());
		descriptor.setInterfaceClass(SkinService.class.getName());
		descriptor.setImplementationClass(SkinServiceListenerForJAXWS.class.getName());
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
