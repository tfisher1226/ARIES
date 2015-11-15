package org.aries.service.jaxws;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;


@WebService(
		name = "PortalService",
		targetNamespace = "http://ws.aries.org"
)	
@SOAPBinding(
		style = SOAPBinding.Style.DOCUMENT, 
		use = SOAPBinding.Use.LITERAL
)
public interface JAXWSService {

	public String invoke(@WebParam(name = "request") String request) throws Exception;

	//public Message invoke2(@WebParam(name = "request") Message request) throws Exception;

	//public Response invoke2(@WebParam(name = "request") Request request) throws Exception;

}
