package sample.hotel;

import common.tx.service.AbstractServiceLauncher;
import common.tx.util.EndpointDescriptor;


public class HotelLauncher extends AbstractServiceLauncher {

	public static HotelLauncher INSTANCE = new HotelLauncher();
	
	public void startup(String host, int port) {
		EndpointDescriptor descriptor = new EndpointDescriptor();
		descriptor.setDescription("Hotel Service");
		descriptor.setServiceName("HotelService");
		descriptor.setPortTypeName("HotelPortType");
		descriptor.setNamespace("http://www.aries.com/demo/Hotel");
		descriptor.setImplementationClass(HotelPortTypeImpl.class.getName());
		descriptor.setInterfaceClass(HotelPortType.class.getName());
		descriptor.setBindAddress(host);
		descriptor.setBindPort(port);
		launch(descriptor);
	}

}
