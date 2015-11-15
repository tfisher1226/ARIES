
package org.aries.client.jaxws;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebServiceFeature;


public class JAXWSPortFactory extends Service {

	private QName portQName;
	
	private Class<?> interfaceClass;

	
	public JAXWSPortFactory(URL wsdlUrl, QName serviceQName, QName portQName, Class<?> interfaceClass) {
		super(wsdlUrl, serviceQName);
		this.portQName = portQName;
		this.interfaceClass = interfaceClass;
		//TODO add validatation
	}

	public Object getPort() {
		Object port = super.getPort(portQName, interfaceClass);
		return port;
	}

	public Object getPort(WebServiceFeature... features) {
		Object port = super.getPort(portQName, interfaceClass, features);
		return port;
	}
	
}
