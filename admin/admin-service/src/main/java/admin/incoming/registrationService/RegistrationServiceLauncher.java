package admin.incoming.registrationService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.launcher.ServiceLauncher;
import org.aries.tx.service.AbstractServiceInitializer;
import org.aries.tx.util.EndpointDescriptor;


public class RegistrationServiceLauncher extends AbstractServiceInitializer {
	
	private static final Log log = LogFactory.getLog(RegistrationServiceLauncher.class);
	
	public static RegistrationServiceLauncher INSTANCE = new RegistrationServiceLauncher();
	
	private ServiceLauncher launcher;
	
	
	public void initialize(String host, int port) {
		EndpointDescriptor descriptor = new EndpointDescriptor();
		descriptor.setNamespace("http://admin");
		descriptor.setContext("/admin-service");
		descriptor.setServiceName("registrationServiceService");
		descriptor.setPortTypeName("registrationService");
		descriptor.setDescription("Launcher component for the registrationService service");
		descriptor.setPackageName(RegistrationService.class.getPackage().getName());
		descriptor.setInterfaceClass(RegistrationService.class.getName());
		descriptor.setImplementationClass(RegistrationServiceListenerForJAXWS.class.getName());
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
