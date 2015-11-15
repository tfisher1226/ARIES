package common.tx.service.activation;

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
import common.tx.model.context.CreateCoordinationContextResponseType;
import common.tx.model.context.CreateCoordinationContextType;


@Startup
@Stateless
@Addressing(required=false)
@WebService(name = "ActivationPortType", 
		portName = "ActivationPortType",
		serviceName = "ActivationService",
		wsdlLocation = "/META-INF/wsdl/wscoor-activation-binding.wsdl",
		targetNamespace = "http://docs.oasis-open.org/ws-tx/wscoor/2006/06")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@HandlerChain(file="/jaxws-handlers-server-oneway.xml")
public class ActivationPortTypeImpl implements ActivationPortType {

	private static Log log = LogFactory.getLog(ActivationPortType.class);
	
	@Resource
	private WebServiceContext webServiceCtx;

	public ActivationPortTypeImpl() {
		System.out.println();
	}
	
	@WebMethod(operationName = "CreateCoordinationContextOperation", action = "http://docs.oasis-open.org/ws-tx/wscoor/2006/06/CreateCoordinationContext")
	@WebResult(name = "CreateCoordinationContextResponse", targetNamespace = "http://docs.oasis-open.org/ws-tx/wscoor/2006/06", partName = "parameters")
	@Action(input="http://docs.oasis-open.org/ws-tx/wscoor/2006/06/CreateCoordinationContext", output="http://docs.oasis-open.org/ws-tx/wscoor/2006/06/CreateCoordinationContextResponse")
	public CreateCoordinationContextResponseType createCoordinationContextOperation(
			@WebParam(name = "CreateCoordinationContext", targetNamespace = "http://docs.oasis-open.org/ws-tx/wscoor/2006/06", partName = "parameters") 
			CreateCoordinationContextType request) {

		try {
			boolean isSecure = false;
			log.info("activationOperation");
			MessageContext messageContext = webServiceCtx.getMessageContext();
			Header header = Header.getHeader(messageContext);
			CreateCoordinationContextResponseType response = ActivationCoordinatorProcessor.getInstance().createCoordinationContext(header, request, isSecure);
			return response;
		} catch (Exception e) {
			return null;
		}
	}

}
