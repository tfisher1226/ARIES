package common.tx.service.coordinator;

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


@WebService(name = "CoordinatorPortType", 
		portName = "CoordinatorPortType",
		serviceName = "CoordinatorService",
		//wsdlLocation = "/WEB-INF/wsdl/wsat-coordinator-binding.wsdl",
		targetNamespace = "http://docs.oasis-open.org/ws-tx/wsat/2006/06")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@HandlerChain(file="/jaxws-handlers-server-twoway.xml")
@Addressing(required=true)
public class CoordinatorPortTypeImpl implements CoordinatorPortType {

	private static Log log = LogFactory.getLog(CoordinatorPortTypeImpl.class);
	
	@Resource
	private WebServiceContext webServiceCtx;

	@Oneway
	@WebMethod(operationName = "PreparedOperation", action = "http://docs.oasis-open.org/ws-tx/wsat/2006/06/Prepared")
	@Action(input="http://docs.oasis-open.org/ws-tx/wsat/2006/06/Prepared")
	public void preparedOperation(@WebParam(name = "Prepared", targetNamespace = "http://docs.oasis-open.org/ws-tx/wsat/2006/06", partName = "parameters") Notification notification) {
		
		//MessageContext messageContext = webServiceCtx.getMessageContext();
		//final Notification prepared = notification;
		//final MAP inboundMap = AddressingHelper.inboundMap(messageContext);
		//final AriesContext context = AriesContext.getCurrentContext(messageContext);
		//log.info("preparedOperation: "+context.getInstanceIdentifier());

		//TaskManager.getInstance().queueTask(new Task() {
		//	public void executeTask() {
		//		CoordinatorProcessor.getInstance().prepared(context, inboundMap, prepared);
		//	}
		//});
	}

	@Oneway
	@WebMethod(operationName = "AbortedOperation", action = "http://docs.oasis-open.org/ws-tx/wsat/2006/06/Aborted")
	@Action(input="http://docs.oasis-open.org/ws-tx/wsat/2006/06/Aborted")
	public void abortedOperation(@WebParam(name = "Aborted", targetNamespace = "http://docs.oasis-open.org/ws-tx/wsat/2006/06", partName = "parameters") Notification parameters) {
		log.info("abortedOperation");
		
		//MessageContext messageContext = webServiceCtx.getMessageContext();
		//final Notification aborted = parameters;
		//final MAP inboundMap = AddressingHelper.inboundMap(messageContext);
		//final AriesContext context = AriesContext.getCurrentContext(messageContext);

		//TaskManager.getInstance().queueTask(new Task() {
		//	public void executeTask() {
		//		CoordinatorProcessor.getInstance().aborted(context, inboundMap, aborted);
		//	}
		//});
	}

	@Oneway
	@WebMethod(operationName = "ReadOnlyOperation", action = "http://docs.oasis-open.org/ws-tx/wsat/2006/06/ReadOnly")
	@Action(input="http://docs.oasis-open.org/ws-tx/wsat/2006/06/ReadOnly")
	public void readOnlyOperation(@WebParam(name = "ReadOnly", targetNamespace = "http://docs.oasis-open.org/ws-tx/wsat/2006/06", partName = "parameters") Notification notification) {
		log.info("readOnlyOperation");
		
		//MessageContext messageContext = webServiceCtx.getMessageContext();
		//final Notification readOnly = notification;
		//final MAP inboundMap = AddressingHelper.inboundMap(messageContext);
		//final AriesContext context = AriesContext.getCurrentContext(messageContext);

		//TaskManager.getInstance().queueTask(new Task() {
		//	public void executeTask() {
		//		CoordinatorProcessor.getInstance().readOnly(context, inboundMap, readOnly);
		//	}
		//});
	}

	@Oneway
	@WebMethod(operationName = "CommittedOperation", action = "http://docs.oasis-open.org/ws-tx/wsat/2006/06/Committed")
	@Action(input="http://docs.oasis-open.org/ws-tx/wsat/2006/06/Committed")
	public void committedOperation(@WebParam(name = "Committed", targetNamespace = "http://docs.oasis-open.org/ws-tx/wsat/2006/06", partName = "parameters") Notification notification) {
		log.info("committedOperation");
		
		//MessageContext messageContext = webServiceCtx.getMessageContext();
		//final Notification committed = notification;
		//final MAP inboundMap = AddressingHelper.inboundMap(messageContext);
		//final AriesContext context = AriesContext.getCurrentContext(messageContext);

		//TaskManager.getInstance().queueTask(new Task() {
		//	public void executeTask() {
		//		CoordinatorProcessor.getInstance().committed(context, inboundMap, committed);
		//	}
		//});
	}

	@Oneway
	@WebMethod(operationName = "Fault", action = "http://docs.oasis-open.org/ws-tx/wsat/2006/06/fault")
	@Action(input="http://docs.oasis-open.org/ws-tx/wsat/2006/06/fault")
	public void fault(@WebParam(name = "Fault", targetNamespace = "http://schemas.xmlsoap.org/soap/envelope/", partName = "parameters") Fault fault) {
		log.info("fault");
		
		//MessageContext messageContext = webServiceCtx.getMessageContext();
		//final MAP inboundMap = AddressingHelper.inboundMap(messageContext);
		//final AriesContext context = AriesContext.getCurrentContext(messageContext);
		//final Fault fault2 = FaultUtil.convertToFault(fault);

		//TaskManager.getInstance().queueTask(new Task() {
		//	public void executeTask() {
		//		CoordinatorProcessor.getInstance().fault(context, inboundMap, fault2);
		//	}
		//});
	}
}
