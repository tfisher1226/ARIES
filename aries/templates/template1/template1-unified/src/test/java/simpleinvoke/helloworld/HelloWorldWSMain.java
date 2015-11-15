package simpleinvoke.helloworld;

import javax.xml.ws.Endpoint;


public class HelloWorldWSMain {

	public static void main(String[] args) {
		try {
			String url = "http://localhost:8080/SimpleInvoke/HelloWorld";
			Object instance = new HelloWorldWSImpl();
			launchAsWebService(url, instance);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void launchAsWebService(String url, Object instance) {
		Endpoint.publish(url, instance);
	}

}
