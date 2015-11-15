
package org.aries.client.jaxws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


@WebService(name = "JAXWSService", targetNamespace = "http://jaxws.aries.org")
@XmlSeeAlso({ElementFactory.class})
public interface JAXWSService {

    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "invokeRequest", targetNamespace = "http://jaxws.aries.org", className = "org.aries.jaxws.InvokeRequest")
    @ResponseWrapper(localName = "invokeResponse", targetNamespace = "http://jaxws.aries.org", className = "org.aries.jaxws.InvokeResponse")
    public String invoke(@WebParam(name = "request", targetNamespace = "") String request) throws JAXWSServiceException;

}
