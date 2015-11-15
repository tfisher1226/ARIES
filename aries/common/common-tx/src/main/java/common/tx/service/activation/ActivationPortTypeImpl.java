package common.tx.service.activation;

import javax.annotation.Resource;
import javax.jws.HandlerChain;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.ws.Action;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.soap.Addressing;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import common.tx.model.context.CreateCoordinationContextResponseType;
import common.tx.model.context.CreateCoordinationContextType;


/**
 * Actions:
 * -create coordinator
 * -create first activity
 * 
 * @author tfisher
 *
 */
@WebService(name = "ActivationPortType", 
		portName = "ActivationPortType",
		serviceName = "ActivationService",
		//wsdlLocation = "/WEB-INF/wsdl/wscoor-activation-binding.wsdl",
		targetNamespace = "http://docs.oasis-open.org/ws-tx/wscoor/2006/06") 
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
//@EndpointConfig(configName = "Standard WSAddressing Endpoint")
@HandlerChain(file="/jaxws-handlers-server-oneway.xml")
@Addressing(required=true)
public class ActivationPortTypeImpl implements ActivationPortType {

	private static Log log = LogFactory.getLog(ActivationPortTypeImpl.class);
	
	@Resource private WebServiceContext webServiceCtx;

	@WebMethod(operationName = "CreateCoordinationContextOperation", action = "http://docs.oasis-open.org/ws-tx/wscoor/2006/06/CreateCoordinationContext")
	@WebResult(name = "CreateCoordinationContextResponse", targetNamespace = "http://docs.oasis-open.org/ws-tx/wscoor/2006/06", partName = "parameters")
	@Action(input="http://docs.oasis-open.org/ws-tx/wscoor/2006/06/CreateCoordinationContext", output="http://docs.oasis-open.org/ws-tx/wscoor/2006/06/CreateCoordinationContextResponse")
	public CreateCoordinationContextResponseType createCoordinationContextOperation(
			@WebParam(name = "CreateCoordinationContext", targetNamespace = "http://docs.oasis-open.org/ws-tx/wscoor/2006/06", partName = "parameters") CreateCoordinationContextType parameters) {

		log.info("activationOperation");
		//MessageContext messageContext = webServiceCtx.getMessageContext();
		//HttpServletRequest request = (HttpServletRequest) messageContext.get(MessageContext.SERVLET_REQUEST);
		//boolean isSecure = "https".equals(request.getScheme());
		//MAP inboundMAP = AddressingHelper.inboundMap(messageContext);
		//return ActivationCoordinatorProcessor.getCoordinator().createCoordinationContext(parameters, inboundMAP, isSecure);
		return null;
	}

}
