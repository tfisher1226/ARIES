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

import common.tx.model.Notification;


@WebService(name = "CompletionCoordinatorPortType", 
		portName = "CompletionCoordinatorPortType",
		serviceName = "CompletionCoordinatorService",
		//wsdlLocation = "/WEB-INF/wsdl/wsat-completion-coordinator-binding.wsdl",
		targetNamespace = "http://docs.oasis-open.org/ws-tx/wsat/2006/06")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@HandlerChain(file="/jaxws-handlers-server-twoway.xml")
@Addressing(required=true)
public class CompletionCoordinatorPortTypeImpl { //implements CompletionCoordinatorPortType {

	private static Log log = LogFactory.getLog(CompletionCoordinatorPortTypeImpl.class);
	
	@Resource
	private WebServiceContext webServiceCtx;

	
	@Oneway
	@WebMethod(operationName = "CommitOperation", action = "http://docs.oasis-open.org/ws-tx/wsat/2006/06/Commit")
	@Action(input="http://docs.oasis-open.org/ws-tx/wsat/2006/06/Commit")
	public void commitOperation(@WebParam(name = "Commit", targetNamespace = "http://docs.oasis-open.org/ws-tx/wsat/2006/06", partName = "parameters") Notification notification) {
		log.info("commitOperation");
		
		//MessageContext messageContext = webServiceCtx.getMessageContext();
		//final Notification commit = notification;
		//final MAP inboundMap = AddressingHelper.inboundMap(messageContext);
		//final AriesContext context = AriesContext.getCurrentContext(messageContext);

		//TaskManager.getInstance().queueTask(new Task() {
		//	public void executeTask() {
		//		CompletionCoordinatorProcessor.getProcessor().commit(context, inboundMap, commit);
		//	}
		//});
	}

	@Oneway
	@WebMethod(operationName = "RollbackOperation", action = "http://docs.oasis-open.org/ws-tx/wsat/2006/06/Rollback")
	@Action(input="http://docs.oasis-open.org/ws-tx/wsat/2006/06/Rollback")
	public void rollbackOperation(@WebParam(name = "Rollback", targetNamespace = "http://docs.oasis-open.org/ws-tx/wsat/2006/06", partName = "parameters") Notification notification) {
		log.info("rollbackOperation");
		
		//MessageContext messageContext = webServiceCtx.getMessageContext();
		//final Notification rollback = notification;
		//final MAP inboundMap = AddressingHelper.inboundMap(messageContext);
		//final AriesContext context = AriesContext.getCurrentContext(messageContext);

		//TaskManager.getInstance().queueTask(new Task() {
		//	public void executeTask() {
		//		CompletionCoordinatorProcessor.getProcessor().rollback(context, inboundMap, rollback) ;
		//	}
		//});
	}

}
