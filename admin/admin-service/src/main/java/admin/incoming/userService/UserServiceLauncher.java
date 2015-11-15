package admin.incoming.userService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.launcher.ServiceLauncher;
import org.aries.tx.service.AbstractServiceInitializer;
import org.aries.tx.util.EndpointDescriptor;


public class UserServiceLauncher extends AbstractServiceInitializer {
	
	private static final Log log = LogFactory.getLog(UserServiceLauncher.class);
	
	public static UserServiceLauncher INSTANCE = new UserServiceLauncher();
	
	private ServiceLauncher launcher;
	
	
	public void initialize(String host, int port) {
		EndpointDescriptor descriptor = new EndpointDescriptor();
		descriptor.setNamespace("http://admin");
		descriptor.setContext("/admin-service");
		descriptor.setServiceName("userServiceService");
		descriptor.setPortTypeName("userService");
		descriptor.setDescription("Launcher component for the userService service");
		descriptor.setPackageName(UserService.class.getPackage().getName());
		descriptor.setInterfaceClass(UserService.class.getName());
		descriptor.setImplementationClass(UserServiceListenerForJAXWS.class.getName());
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
