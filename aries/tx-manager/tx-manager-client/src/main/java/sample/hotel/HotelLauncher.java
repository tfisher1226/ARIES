package sample.hotel;

import org.aries.tx.service.AbstractServiceInitializer;
import org.aries.tx.util.EndpointDescriptor;


public class HotelLauncher extends AbstractServiceInitializer {

	public static HotelLauncher INSTANCE = new HotelLauncher();
	
	public void initialize(String host, int port) {
		EndpointDescriptor descriptor = new EndpointDescriptor();
		descriptor.setDescription("Hotel Service");
		descriptor.setServiceName("HotelService");
		descriptor.setPortTypeName("HotelPortType");
		descriptor.setNamespace("http://www.aries.com/demo/Hotel");
		descriptor.setImplementationClass(HotelPortTypeImpl.class.getName());
		descriptor.setInterfaceClass(HotelPortType.class.getName());
		descriptor.setBindAddress(host);
		descriptor.setBindPort(port);
		descriptor.setContext("/tx-service");
		launch(descriptor);
	}

}
