package simpleinvoke.helloworld;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;


/**
 * Generated by Nam.
 *
 */
@WebService(name = "HelloWorldWS", portName = "HelloWorldWSPort", serviceName = "HelloWorldWSService", targetNamespace = "http://www.aries.org/simple_invoke/helloworld")
public class HelloWorldWSImpl implements HelloWorldWS {

	@WebMethod
	@WebResult(name = "response")
	//@RequestWrapper(targetNamespace="http://www.aries.org/simple_invoke/helloworld", className="simpleinvoke.helloworld.SayHello")
	//@ResponseWrapper(targetNamespace="http://www.aries.org/simple_invoke/helloworld", className="simpleinvoke.helloworld.SayHelloResponse")
	public SayHelloResponse sayHello(@WebParam(name = "request") SayHello sayHello) {
		SayHelloResponse response = new SayHelloResponse();
		String reply = new String("Hello "+sayHello.getToWhom());
		response.setReply(reply);
		return response;
	}
	
	@WebMethod
	@WebResult(name = "response")
	public SayGoodbyeResponse sayGoodbye(@WebParam(name = "request") SayGoodbye sayGoodbye) {
		SayGoodbyeResponse response = new SayGoodbyeResponse();
		return response;
	}

}