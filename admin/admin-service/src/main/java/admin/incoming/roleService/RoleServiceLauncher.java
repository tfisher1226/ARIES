package admin.incoming.roleService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.launcher.ServiceLauncher;
import org.aries.tx.service.AbstractServiceInitializer;
import org.aries.tx.util.EndpointDescriptor;


public class RoleServiceLauncher extends AbstractServiceInitializer {
	
	private static final Log log = LogFactory.getLog(RoleServiceLauncher.class);
	
	public static RoleServiceLauncher INSTANCE = new RoleServiceLauncher();
	
	private ServiceLauncher launcher;
	
	
	public void initialize(String host, int port) {
		EndpointDescriptor descriptor = new EndpointDescriptor();
		descriptor.setNamespace("http://admin");
		descriptor.setContext("/admin-service");
		descriptor.setServiceName("roleServiceService");
		descriptor.setPortTypeName("roleService");
		descriptor.setDescription("Launcher component for the roleService service");
		descriptor.setPackageName(RoleService.class.getPackage().getName());
		descriptor.setInterfaceClass(RoleService.class.getName());
		descriptor.setImplementationClass(RoleServiceListenerForJAXWS.class.getName());
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
