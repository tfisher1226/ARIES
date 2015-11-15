package sample.restaurant;

import common.tx.service.AbstractServiceLauncher;
import common.tx.util.EndpointDescriptor;


public class RestaurantLauncher extends AbstractServiceLauncher {

	public static RestaurantLauncher INSTANCE = new RestaurantLauncher();
	
	public void startup(String host, int port) {
		EndpointDescriptor descriptor = new EndpointDescriptor();
		descriptor.setDescription("Restaurant Service");
		descriptor.setServiceName("RestaurantService");
		descriptor.setPortTypeName("RestaurantPortType");
		descriptor.setNamespace("http://www.aries.com/demo/Restaurant");
		descriptor.setImplementationClass(RestaurantPortTypeImpl.class.getName());
		descriptor.setInterfaceClass(RestaurantPortType.class.getName());
		descriptor.setBindAddress(host);
		descriptor.setBindPort(port);
		launch(descriptor);
	}

}
