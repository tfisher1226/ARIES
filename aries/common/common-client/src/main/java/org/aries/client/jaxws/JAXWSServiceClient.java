package org.aries.client.jaxws;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;


@WebServiceClient(name = "JAXWSService", targetNamespace = "http://jaxws.aries.org/")
public class JAXWSServiceClient extends Service {
    
    public JAXWSServiceClient(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    @WebEndpoint(name = "JAXWSServicePort")
    public JAXWSService getPortalServicePort() {
        QName qName = new QName("http://jaxws.aries.org/", "JAXWSServicePort");
		JAXWSService port = getPort(qName, JAXWSService.class);
		return port;
    }

    @WebEndpoint(name = "JAXWSServicePort")
    public JAXWSService getPortalServicePort(WebServiceFeature... features) {
        QName qName = new QName("http://jaxws.aries.org/", "JAXWSServicePort");
		JAXWSService port = getPort(qName, JAXWSService.class, features);
		return port;
    }

}
