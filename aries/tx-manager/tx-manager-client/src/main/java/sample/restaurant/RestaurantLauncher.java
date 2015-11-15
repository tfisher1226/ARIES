package sample.restaurant;

import org.aries.launcher.ServiceLauncher;
import org.aries.tx.service.AbstractServiceInitializer;
import org.aries.tx.util.EndpointDescriptor;


public class RestaurantLauncher extends AbstractServiceInitializer {

	public static RestaurantLauncher INSTANCE = new RestaurantLauncher();
	
	private ServiceLauncher launcher;
	
	
	public void initialize(String host, int port) {
		EndpointDescriptor descriptor = new EndpointDescriptor();
		descriptor.setDescription("Restaurant Service");
		descriptor.setServiceName("RestaurantService");
		descriptor.setPortTypeName("RestaurantPortType");
		descriptor.setNamespace("http://www.aries.com/demo/Restaurant");
		descriptor.setImplementationClass(RestaurantPortTypeImpl.class.getName());
		descriptor.setInterfaceClass(RestaurantPortType.class.getName());
		descriptor.setBindAddress(host);
		descriptor.setBindPort(port);
		descriptor.setContext("/tx-service");
		launcher = launch(descriptor);
	}

	public void shutdown() {
		if (launcher != null)
			launcher.shutdown();
		launcher = null;
	}
	
}
