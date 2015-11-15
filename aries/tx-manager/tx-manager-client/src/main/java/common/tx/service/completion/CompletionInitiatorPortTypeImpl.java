package common.tx.service.completion;

import javax.annotation.Resource;
import javax.ejb.Startup;
import javax.ejb.Stateless;
import javax.jws.HandlerChain;
import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.ws.Action;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.soap.Addressing;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aries.tx.util.Task;
import org.aries.tx.util.TaskManager;
import org.xmlsoap.schemas.soap.envelope.Fault;

import common.tx.model.Header;
import common.tx.model.Notification;


@Startup
@Stateless
@Addressing(required=false)
@WebService(name = "CompletionInitiatorPortType", 
		portName = "CompletionInitiatorPortType",
		serviceName = "CompletionInitiatorService",
		wsdlLocation = "/META-INF/wsdl/wsat-completion-initiator-binding.wsdl",
		targetNamespace = "http://docs.oasis-open.org/ws-tx/wsat/2006/06")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@HandlerChain(file="/jaxws-handlers-server-twoway.xml")
public class CompletionInitiatorPortTypeImpl { //implements CompletionInitiatorPortType {

	private static Log log = LogFactory.getLog(CompletionInitiatorPortTypeImpl.class);
	
	@Resource
	private WebServiceContext webServiceCtx;

	@Oneway
	@WebMethod(operationName = "CommittedOperation", action = "http://docs.oasis-open.org/ws-tx/wsat/2006/06/Committed")
	@Action(input="http://docs.oasis-open.org/ws-tx/wsat/2006/06/Committed")
	public void committedOperation(
			@WebParam(name = "Committed", targetNamespace = "http://docs.oasis-open.org/ws-tx/wsat/2006/06", partName = "parameters") 
			Notification notification) {
		
		log.info("committedOperation");
		MessageContext messageContext = webServiceCtx.getMessageContext();
		final Header header = Header.getHeader(messageContext);
		final Notification parameters = notification;

		TaskManager.getManager().queueTask(new Task() {
			public void executeTask() {
				CompletionInitiatorProcessorImpl.getProcessor().handleCommitted(header, parameters);
			}
		});
	}

	@Oneway
	@WebMethod(operationName = "AbortedOperation", action = "http://docs.oasis-open.org/ws-tx/wsat/2006/06/Aborted")
	@Action(input="http://docs.oasis-open.org/ws-tx/wsat/2006/06/Aborted")
	public void abortedOperation(
			@WebParam(name = "Aborted", targetNamespace = "http://docs.oasis-open.org/ws-tx/wsat/2006/06", partName = "parameters") 
			Notification notification) {
		
		log.info("abortedOperation");
		MessageContext messageContext = webServiceCtx.getMessageContext();
		final Header header = Header.getHeader(messageContext);
		final Notification parameters = notification;

		TaskManager.getManager().queueTask(new Task() {
			public void executeTask() {
				CompletionInitiatorProcessorImpl.getProcessor().handleAborted(header, parameters);
			}
		});
	}

	@Oneway
	@WebMethod(operationName = "Fault", action = "http://docs.oasis-open.org/ws-tx/wsat/2006/06/fault")
	@Action(input="http://docs.oasis-open.org/ws-tx/wsat/2006/06/fault")
	public void fault(
			@WebParam(name = "Fault", targetNamespace = "http://schemas.xmlsoap.org/soap/envelope/", partName = "parameters") 
			final Fault fault) {

		log.info("fault");
		MessageContext messageContext = webServiceCtx.getMessageContext();
		final Header header = Header.getHeader(messageContext);
		//final Fault fault2 = FaultUtil.convertToFault(fault);

		TaskManager.getManager().queueTask(new Task() {
			public void executeTask() {
				CompletionInitiatorProcessorImpl.getProcessor().handleFault(header, fault);
			}
		});
	}

}
