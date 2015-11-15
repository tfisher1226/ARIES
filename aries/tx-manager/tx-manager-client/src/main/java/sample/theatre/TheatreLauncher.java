package sample.theatre;

import org.aries.tx.service.AbstractServiceInitializer;
import org.aries.tx.util.EndpointDescriptor;


public class TheatreLauncher extends AbstractServiceInitializer {

	public static TheatreLauncher INSTANCE = new TheatreLauncher();
	
	public void initialize(String host, int port) {
		EndpointDescriptor descriptor = new EndpointDescriptor();
		descriptor.setDescription("Theatre Service");
		descriptor.setServiceName("TheatreService");
		descriptor.setPortTypeName("TheatrePortType");
		descriptor.setNamespace("http://www.aries.com/demo/Theatre");
		descriptor.setImplementationClass(TheatrePortTypeImpl.class.getName());
		descriptor.setInterfaceClass(TheatrePortType.class.getName());
		descriptor.setBindAddress(host);
		descriptor.setBindPort(port);
		descriptor.setContext("/tx-service");
		launch(descriptor);
	}

}
