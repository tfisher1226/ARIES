package org.aries.service.jaxws;

import javax.xml.ws.Endpoint;


public class EndpointFactory {

	public Endpoint createEndpoint(String url) {
		Endpoint endpoint = Endpoint.publish(url, new JAXWSServiceImpl());
		return endpoint;
	}
	
}
