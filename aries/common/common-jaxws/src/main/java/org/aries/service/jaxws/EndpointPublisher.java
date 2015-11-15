package org.aries.service.jaxws;

import java.io.IOException;

import javax.xml.ws.Endpoint;


public class EndpointPublisher {

	/**
	 * Holds url of service to publish, for example see below:
	 * http://localhost:8080/ws.aries.org
	 */
	private String _url;
	
	
	public EndpointPublisher(String url) {
		_url = url;
	}
	
	public Endpoint publish() throws IOException {
		Endpoint endpoint = Endpoint.publish(_url, new JAXWSServiceImpl());
		return endpoint;
	}
	

	public void publishAndWait() throws IOException {
		Endpoint endpoint = Endpoint.publish(_url, new JAXWSServiceImpl());
        //stops if receives: http://localhost:9090/stop
        new EndpointStopper(9090, endpoint);
	}

}
