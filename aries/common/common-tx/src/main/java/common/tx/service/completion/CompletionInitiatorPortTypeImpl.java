package common.tx.service.completion;

import javax.annotation.Resource;
import javax.jws.HandlerChain;
import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.ws.Action;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.soap.Addressing;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import common.tx.model.Fault;
import common.tx.model.Notification;


@WebService(name = "CompletionInitiatorPortType", 
		portName = "CompletionInitiatorPortType",
		serviceName = "CompletionInitiatorService",
		//wsdlLocation = "/WEB-INF/wsdl/wsat-completion-initiator-binding.wsdl",
		targetNamespace = "http://docs.oasis-open.org/ws-tx/wsat/2006/06")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@HandlerChain(file="/jaxws-handlers-server-twoway.xml")
@Addressing(required=true)
public class CompletionInitiatorPortTypeImpl { //implements CompletionInitiatorPortType {

	private static Log log = LogFactory.getLog(CompletionInitiatorPortTypeImpl.class);
	
	@Resource
	private WebServiceContext webServiceCtx;

	
	@Oneway
	@WebMethod(operationName = "CommittedOperation", action = "http://docs.oasis-open.org/ws-tx/wsat/2006/06/Committed")
	@Action(input="http://docs.oasis-open.org/ws-tx/wsat/2006/06/Committed")
	public void committedOperation(@WebParam(name = "Committed", targetNamespace = "http://docs.oasis-open.org/ws-tx/wsat/2006/06", partName = "parameters") Notification notification) {
		log.info("committedOperation");
		
		//MessageContext ctx = webServiceCtx.getMessageContext();
		//final Notification committed = notification;
		//final MAP inboundMap = AddressingHelper.inboundMap(ctx);
		//final AriesContext context = AriesContext.getCurrentContext(ctx);

		//TaskManager.getInstance().queueTask(new Task() {
		//	public void executeTask() {
		//		try {
		//			CompletionInitiatorProcessor.getProcessor().handleCommitted(committed, inboundMap, context);
		//		} catch (Throwable e) {
		//			e.printStackTrace();
		//		}
		//	}
		//});
	}

	@Oneway
	@WebMethod(operationName = "AbortedOperation", action = "http://docs.oasis-open.org/ws-tx/wsat/2006/06/Aborted")
	@Action(input="http://docs.oasis-open.org/ws-tx/wsat/2006/06/Aborted")
	public void abortedOperation(@WebParam(name = "Aborted", targetNamespace = "http://docs.oasis-open.org/ws-tx/wsat/2006/06", partName = "parameters") Notification notification) {
		log.info("abortedOperation");
		
		//MessageContext ctx = webServiceCtx.getMessageContext();
		//final Notification aborted = notification;
		//final MAP inboundMap = AddressingHelper.inboundMap(ctx);
		//final AriesContext context = AriesContext.getCurrentContext(ctx);

		//TaskManager.getInstance().queueTask(new Task() {
		//	public void executeTask() {
		//		try {
		//			CompletionInitiatorProcessor.getProcessor().handleAborted(aborted, inboundMap, context);
		//		} catch (Throwable e) {
		//			e.printStackTrace();
		//		}
		//	}
		//});
	}

	@Oneway
	@WebMethod(operationName = "Fault", action = "http://docs.oasis-open.org/ws-tx/wsat/2006/06/fault")
	@Action(input="http://docs.oasis-open.org/ws-tx/wsat/2006/06/fault")
	public void fault(@WebParam(name = "Fault", targetNamespace = "http://schemas.xmlsoap.org/soap/envelope/", partName = "parameters") Fault fault) {
		log.info("fault");
		
		//MessageContext ctx = webServiceCtx.getMessageContext();
		//final MAP inboundMap = AddressingHelper.inboundMap(ctx);
		//final AriesContext context = AriesContext.getCurrentContext(ctx);
		//final Fault fault2 = FaultUtil.convertToFault(fault);

		//TaskManager.getInstance().queueTask(new Task() {
		//	public void executeTask() {
		//		try {
		//			CompletionInitiatorProcessor.getProcessor().handleFault(fault2, inboundMap, context);
		//		} catch (Throwable e) {
		//			e.printStackTrace();
		//		}
		//	}
		//});
	}

}
