package common.tx.service.activation;

import javax.annotation.Resource;
import javax.jws.HandlerChain;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.Action;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.soap.Addressing;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import common.tx.model.Fault;
import common.tx.model.context.RegisterResponseType;
import common.tx.model.context.RegisterType;


@WebService(name = "RegistrationPortType", 
		portName = "RegistrationPortType",
		serviceName = "RegistrationService",
		//wsdlLocation = "/WEB-INF/wsdl/wscoor-registration-binding.wsdl",
		targetNamespace = "http://docs.oasis-open.org/ws-tx/wscoor/2006/06")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
//@EndpointConfig(configName = "Standard WSAddressing Endpoint")
@HandlerChain(file="/jaxws-handlers-server-oneway.xml")
@Addressing(required=true)
public class RegistrationPortTypeImpl implements RegistrationPortType {

	private static Log log = LogFactory.getLog(RegistrationPortTypeImpl.class);
	
	@Resource 
	private WebServiceContext webServiceCtx;

	@WebResult(name = "RegisterResponse", partName = "parameters", targetNamespace = "http://docs.oasis-open.org/ws-tx/wscoor/2006/06")
	@WebMethod(operationName = "RegisterOperation", action = "http://docs.oasis-open.org/ws-tx/wscoor/2006/06/Register")
	@Action(input="http://docs.oasis-open.org/ws-tx/wscoor/2006/06/Register", output="http://docs.oasis-open.org/ws-tx/wscoor/2006/06/RegisterResponse")
	public RegisterResponseType registerOperation(
			@WebParam(targetNamespace = "http://docs.oasis-open.org/ws-tx/wscoor/2006/06", partName = "parameters", name = "Register") RegisterType parameters) {

		//log.info("registerOperation");
		//MessageContext messageContext = webServiceCtx.getMessageContext();
		//HttpServletRequest request = (HttpServletRequest) messageContext.get(MessageContext.SERVLET_REQUEST);
		//MAP inboundMap = AddressingHelper.inboundMap(messageContext);
		//boolean secure = "https".equals(request.getScheme());
		//try {
		//	AriesContext context = AriesContext.getCurrentContext(messageContext);
		//	return RegistrationCoordinatorProcessor.getCoordinator().register(context, inboundMap, parameters, secure);
		//} catch (Exception e) {
		//	//e.printStackTrace();
		//	throw new Fault(e);
		//}
		return null;
	}

}
