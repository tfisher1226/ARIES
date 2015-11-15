package sample.theatre;

import common.tx.service.AbstractServiceLauncher;
import common.tx.util.EndpointDescriptor;


public class TheatreLauncher extends AbstractServiceLauncher {

	public static TheatreLauncher INSTANCE = new TheatreLauncher();
	
	public void startup(String host, int port) {
		EndpointDescriptor descriptor = new EndpointDescriptor();
		descriptor.setDescription("Theatre Service");
		descriptor.setServiceName("TheatreService");
		descriptor.setPortTypeName("TheatrePortType");
		descriptor.setNamespace("http://www.aries.com/demo/Theatre");
		descriptor.setImplementationClass(TheatrePortTypeImpl.class.getName());
		descriptor.setInterfaceClass(TheatrePortType.class.getName());
		descriptor.setBindAddress(host);
		descriptor.setBindPort(port);
		launch(descriptor);
	}

}
