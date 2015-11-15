package simpleinvoke.helloworld;

import javax.xml.ws.Endpoint;


public class JAXWSServiceLauncher {

	public static void launch(String url, Object instance) {
		//Bus bus = BusFactory.newInstance().createBus();
		//BusFactory.setThreadDefaultBus(bus);
		Endpoint.publish(url, instance);
	}

}
