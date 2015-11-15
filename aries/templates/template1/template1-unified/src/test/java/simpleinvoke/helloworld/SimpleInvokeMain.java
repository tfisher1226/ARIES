package simpleinvoke.helloworld;

import javax.xml.ws.Endpoint;


public class SimpleInvokeMain {

	public static void main(String[] args) {
		try {
			String url = "http://localhost:8180/SimpleInvoke";
			Object instance = new SimpleInvokeImpl();
			launchAsWebService(url, instance);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void launchAsWebService(String url, Object instance) {
		Endpoint.publish(url, instance);
	}

}
