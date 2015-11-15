package common.tx.service.registration;

import javax.annotation.Resource;
import javax.ejb.Startup;
import javax.ejb.Stateless;
import javax.jws.HandlerChain;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.ws.Action;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.soap.Addressing;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import common.tx.model.Header;
import common.tx.model.context.RegisterResponseType;
import common.tx.model.context.RegisterType;


@Startup
@Stateless
@Addressing(required=false)
@WebService(name = "RegistrationPortType", 
		portName = "RegistrationPortType",
		serviceName = "RegistrationService",
		wsdlLocation = "/META-INF/wsdl/wscoor-registration-binding.wsdl",
		targetNamespace = "http://docs.oasis-open.org/ws-tx/wscoor/2006/06")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@HandlerChain(file="/jaxws-handlers-server-oneway.xml")
public class RegistrationPortTypeImpl implements RegistrationPortType {

	private static Log log = LogFactory.getLog(RegistrationPortType.class);
	
	@Resource
	private WebServiceContext webServiceCtx;

	@WebMethod(operationName = "RegisterOperation", action = "http://docs.oasis-open.org/ws-tx/wscoor/2006/06/Register")
	@WebResult(name = "RegisterResponse", partName = "parameters", targetNamespace = "http://docs.oasis-open.org/ws-tx/wscoor/2006/06")
	@Action(input="http://docs.oasis-open.org/ws-tx/wscoor/2006/06/Register", output="http://docs.oasis-open.org/ws-tx/wscoor/2006/06/RegisterResponse")
	public RegisterResponseType registerOperation(
			@WebParam(targetNamespace = "http://docs.oasis-open.org/ws-tx/wscoor/2006/06", partName = "parameters", name = "Register") 
			RegisterType request) {

		try {
			boolean isSecure = false;
			log.info("registerOperation");
			MessageContext messageContext = webServiceCtx.getMessageContext();
			Header header = Header.getHeader(messageContext);
			RegisterResponseType response = RegistrationCoordinatorProcessor.getCoordinator().register(header, request, isSecure);
			return response;
		} catch (Exception e) {
			return null;
		}
	}

}
