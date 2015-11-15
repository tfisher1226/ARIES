package simpleinvoke.helloworld;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.WebServiceRef;

/**
 * Generated by Nam.
 *
 */
@WebService(name = "SimpleInvoke", serviceName = "SimpleInvokeService", portName = "SimpleInvokePort", targetNamespace = "http://www.jboss.org/bpel/examples/wsdl")
public class SimpleInvokeProxy implements SimpleInvoke {

	@WebServiceRef(wsdlLocation="http://localhost:8180/SimpleInvoke?wsdl")
	private SimpleInvokeStub service;

	
	@WebMethod
	public String sayHelloTo(@WebParam(name = "request") String toWhom) {
		service = new SimpleInvokeStub();
		SimpleInvoke port = service.getSimpleInvokePort();
		String response = port.sayHelloTo(toWhom);
		return response;
	}

}